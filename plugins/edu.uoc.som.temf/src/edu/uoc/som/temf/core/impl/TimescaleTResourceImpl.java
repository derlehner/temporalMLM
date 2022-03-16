/*******************************************************************************
 * Copyright (c) 2018 Abel Gómez.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Abel Gómez - initial API and implementation
 *******************************************************************************/
package edu.uoc.som.temf.core.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.InternalEObject.EStore;
import org.eclipse.emf.ecore.impl.EClassifierImpl;
import org.eclipse.emf.ecore.impl.EReferenceImpl;
import org.eclipse.emf.ecore.impl.EStoreEObjectImpl;
import org.eclipse.emf.ecore.impl.EStoreEObjectImpl.EStoreEList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.h2.mvstore.MVStore;

import edu.uoc.som.temf.TURI;
import edu.uoc.som.temf.core.InternalTObject;
import edu.uoc.som.temf.core.TGlobalClock;
import edu.uoc.som.temf.core.TObject;
import edu.uoc.som.temf.core.TResource;
import edu.uoc.som.temf.tstores.SearcheableTStore;
import edu.uoc.som.temf.tstores.TStore;
import edu.uoc.som.temf.tstores.impl.IsSetCachingDelegatedTStoreImpl;
import edu.uoc.som.temf.tstores.impl.SizeCachingDelegatedTStoreImpl;
import edu.uoc.som.temf.tstores.impl.TimescaleTStoreImpl;

public class TimescaleTResourceImpl extends ResourceImpl implements TResource {

	protected static final ResourceContentsEStructuralFeature ROOT_CONTENTS_ESTRUCTURALFEATURE = new ResourceContentsEStructuralFeature();

	protected final DummyRootEObject DUMMY_ROOT_EOBJECT = new DummyRootEObject(this);

	protected SearcheableTStore tStore;

	protected Connection con;

	protected Clock clock = TGlobalClock.INSTANCE;

	public TimescaleTResourceImpl(URI connectionString) {
		String connString = "jdbc:postgresql://127.0.0.1:5432/example?user=postgres&password=password";
		try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found " + e);
        }
		try {
			Connection con = DriverManager.getConnection(connString);
			System.out.println("Connection successfully established");
			this.con = con;
			this.tStore = new TimescaleTStoreImpl(con, this);
		}catch(SQLException ex){
			System.out.println(ex.getMessage());
		}
		this.uri = uri;
	}

	private TimescaleTResourceImpl(URI uri, Connection con) {
		this.uri = uri;
		this.con = con;
		this.tStore = new TimescaleTStoreImpl(con, this); 
	}

	@Override
	public TStore tStore() {
		return tStore;
	}

	@Override
	public Clock getClock() {
		return clock;
	}

	@Override
	public EList<EObject> getContents() {
		return new ResourceContentsEStoreEList(DUMMY_ROOT_EOBJECT, ROOT_CONTENTS_ESTRUCTURALFEATURE, tStore);
	}

	@SuppressWarnings("unchecked")
	@Override
	public EList<EObject> getContentsAt(Instant instant) {
		return ECollections.unmodifiableEList(
				(EList<EObject>) (Object) ECollections.asEList(tStore.toArrayAt(instant, DUMMY_ROOT_EOBJECT, ROOT_CONTENTS_ESTRUCTURALFEATURE)));
	}

	public TreeIterator<EObject> getAllContentsAt(final Instant instant) {
		return new AbstractTreeIterator<EObject>(this, false) {
			private static final long serialVersionUID = 1L;

			@Override
			public Iterator<EObject> getChildren(Object object) {
				return object == TimescaleTResourceImpl.this ? TimescaleTResourceImpl.this.getContentsAt(instant).iterator() : ((TObject) object).eContentsAt(instant).iterator();
			}
		};
	}

	@Override
	public EObject getEObject(String uriFragment) {
		EObject eObject = tStore.getEObject(uriFragment);
		if (eObject != null) {
			return eObject;
		} else {
			return super.getEObject(uriFragment);
		}
	}

	@Override
	public String getURIFragment(EObject eObject) {
		if (eObject.eResource() != this) {
			return "/-1";
		} else {
			// Try to adapt as a TObject and return the ID
			TObject tObject = TObjectAdapterFactoryImpl.getAdapter(eObject, TObject.class);
			if (tObject != null) {
				return (tObject.tId());
			}
		}
		return super.getURIFragment(eObject);
	}

	@Override
	public TResource at(Instant instant) {
		return new TResourceAtImpl(this.uri.appendFragment("@" + instant.toString()), this.con, instant);
	}

	@Override
	public void load(Map<?, ?> options) throws IOException {
		try {
			isLoading = true;
			if (isLoaded) {
				return;
			} else if (!getFile().exists()) {
				throw new FileNotFoundException(uri.toFileString());
			} else {
				// this.mvStore = MVStore.open(getFile().getAbsolutePath());
				this.tStore = createResourceEStore(this.con);
			}
			isLoaded = true;
		} finally {
			isLoading = false;
		}
	}

	@Override
	public void save(Map<?, ?> options) throws IOException {
		if(isLoaded()) {	// save only makes sense if resource is loaded
			/*try {	// not needed, because autocommit is enabled
				con.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
		}
		//TODO: what happens here if con is null? Are there other error cases to be considered??

	}

	public static void shutdownWithoutUnload(TimescaleTResourceImpl resource) {
		resource.shutdown();
	}

	protected void shutdown() {
		try {
		this.con.close();
		}catch(SQLException ex){
			System.out.println("Timescale connection was not closed!");
		}
		//this.mvStore = new MVStore.Builder().fileStore(new OffHeapStore()).open();
		//this.tStore = new MVStoreTStoreImpl(mvStore, this);
	}

	@Override
	protected void doUnload() {
		Iterator<EObject> allContents = getAllProperContents(unloadingContents);
		getErrors().clear();
		getWarnings().clear();
		while (allContents.hasNext()) {
			unloaded((InternalEObject) allContents.next());
		}
		shutdown();
	}

	@Override
	protected void finalize() throws Throwable {
		unload();
	}

	protected SearcheableTStore createResourceEStore(Connection con) throws IOException {
		return new IsSetCachingDelegatedTStoreImpl(new SizeCachingDelegatedTStoreImpl(new TimescaleTStoreImpl(con, this)));
	}

	protected File getFile() {
		return FileUtils.getFile(TURI.createTURI(getURI().appendSegment("mvstore")).toFileString());
	}

	/**
	 * Specific {@link TResource} implementation that presents a read-only view of
	 * an existing {@link TResource} (backed by an existing {@link MVStore})
	 * 
	 * @author agomez
	 *
	 */
	static private class TResourceAtImpl extends TimescaleTResourceImpl {

		private TResourceAtImpl(URI uri, Connection con, Instant instant) {
			super(uri, con);
			this.clock = Clock.fixed(instant, ZoneOffset.UTC);
			this.tStore = new TimescaleTStoreImpl(con, TResourceAtImpl.this);
			isLoaded = true;
		}

		@Override
		public void load(Map<?, ?> options) throws IOException {
			throw new UnsupportedOperationException("Past TResources cannot be explicitly loaded");
		}

		@Override
		public final void save(Map<?, ?> options) throws IOException {
			throw new UnsupportedOperationException("Past TResources cannot vbe saved");
		}
	}

	/**
	 * Fake {@link EStructuralFeature} that represents the
	 * {@link Resource#getContents()} feature.
	 * 
	 * @author agomez
	 * 
	 */
	protected static class ResourceContentsEStructuralFeature extends EReferenceImpl {
		protected static final String RESOURCE__CONTENTS__FEATURE_NAME = "eContents";

		public ResourceContentsEStructuralFeature() {
			// @formatter:off
			this.setUpperBound(ETypedElement.UNBOUNDED_MULTIPLICITY);
			this.setLowerBound(0);
			this.setName(RESOURCE__CONTENTS__FEATURE_NAME);
			this.setEType(new EClassifierImpl() {});
			this.setFeatureID(RESOURCE__CONTENTS);
			// @formatter:on
		}
	}

	/**
	 * Dummy {@link EObject} that represents the root entry point for this
	 * {@link Resource}
	 * 
	 * @author agomez
	 * 
	 */
	protected final class DummyRootEObject extends TObjectImpl {
		protected static final String ROOT_EOBJECT_ID = "ROOT";

		protected DummyRootEObject(Resource.Internal resource) {
			super();
			this.id = ROOT_EOBJECT_ID;
			eSetDirectResource(resource);
		}
	}

	/**
	 * A notifying {@link EStoreEList} list implementation for supporting
	 * {@link Resource#getContents}.
	 * 
	 * @author agomez
	 * 
	 */
	protected class ResourceContentsEStoreEList extends EStoreEObjectImpl.EStoreEList<EObject> {
		protected static final long serialVersionUID = 1L;

		protected ResourceContentsEStoreEList(InternalEObject owner, EStructuralFeature eStructuralFeature, EStore store) {
			super(owner, eStructuralFeature, store);
		}

		@Override
		protected EObject validate(int index, EObject object) {
			if (!canContainNull() && object == null) {
				throw new IllegalArgumentException("The 'no null' constraint is violated");
			}
			return object;
		}

		@Override
		public Object getNotifier() {
			return TimescaleTResourceImpl.this;
		}

		@Override
		public int getFeatureID() {
			return RESOURCE__CONTENTS;
		}

		@Override
		protected boolean isNotificationRequired() {
			return TimescaleTResourceImpl.this.eNotificationRequired();
		}

		@Override
		protected boolean useEquals() {
			return false;
		}

		@Override
		protected boolean hasInverse() {
			return true;
		}

		@Override
		protected boolean isUnique() {
			return true;
		}

		@Override
		public NotificationChain inverseAdd(EObject object, NotificationChain notifications) {
			InternalEObject eObject = (InternalEObject) object;
			notifications = eObject.eSetResource(TimescaleTResourceImpl.this, notifications);
			TimescaleTResourceImpl.this.attached(eObject);
			return notifications;
		}

		@Override
		public NotificationChain inverseRemove(EObject object, NotificationChain notifications) {
			InternalEObject eObject = (InternalEObject) object;
			if (TimescaleTResourceImpl.this.isLoaded || unloadingContents != null) {
				TimescaleTResourceImpl.this.detached(eObject);
			}
			return eObject.eSetResource(null, notifications);
		}

		@Override
		protected void delegateAdd(int index, EObject object) {
			// Maintain a list of hard links to the elements while moving
			// them to the new resource. If a garbage collection happens while
			// traversing the children elements, some unsaved objects that are
			// referenced from a saved object may be garbage collected before
			// they have been completely stored in the DB
			List<EObject> hardLinksList = new ArrayList<>();
			InternalTObject eObject = TObjectAdapterFactoryImpl.getAdapter(object, InternalTObject.class);
			// Collect all contents
			hardLinksList.add(object);
			eObject.eAllContents().forEachRemaining(e -> hardLinksList.add(e));
			// Iterate using the hard links list instead eAllContents
			hardLinksList.forEach(e -> TObjectAdapterFactoryImpl.getAdapter(e, InternalTObject.class).tSetResource(TimescaleTResourceImpl.this));
			super.delegateAdd(index, object);
		}

		@Override
		protected EObject delegateRemove(int index) {
			// See comment on #delegateAdd about hardLinkList
			EObject object = super.delegateRemove(index);
			List<EObject> hardLinksList = new ArrayList<>();
			InternalTObject eObject = TObjectAdapterFactoryImpl.getAdapter(object, InternalTObject.class);
			hardLinksList.add(object);
			eObject.eAllContents().forEachRemaining(e -> hardLinksList.add(e));
			hardLinksList.forEach(e -> TObjectAdapterFactoryImpl.getAdapter(e, InternalTObject.class).tSetResource(null));
			return object;
		}

		@Override
		protected void didAdd(int index, EObject object) {
			super.didAdd(index, object);
			if (index == size() - 1) {
				loaded();
			}
			modified();
		}

		@Override
		protected void didRemove(int index, EObject object) {
			super.didRemove(index, object);
			modified();
		}

		@Override
		protected void didSet(int index, EObject newObject, EObject oldObject) {
			super.didSet(index, newObject, oldObject);
			modified();
		}

		@Override
		protected void didClear(int oldSize, Object[] oldData) {
			if (oldSize == 0) {
				loaded();
			} else {
				super.didClear(oldSize, oldData);
			}
		}

		protected void loaded() {
			if (!TimescaleTResourceImpl.this.isLoaded()) {
				Notification notification = TimescaleTResourceImpl.this.setLoaded(true);
				if (notification != null) {
					TimescaleTResourceImpl.this.eNotify(notification);
				}
			}
		}

		protected void modified() {
			if (isTrackingModification()) {
				setModified(true);
			}
		}
	}

}
