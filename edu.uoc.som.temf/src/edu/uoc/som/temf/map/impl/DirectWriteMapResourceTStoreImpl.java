/*******************************************************************************
 * Copyright (c) 2018 Abel G�mez.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Abel G�mez - initial API and implementation
 *******************************************************************************/
package edu.uoc.som.temf.map.impl;

import java.io.Serializable;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.h2.mvstore.Cursor;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import edu.uoc.som.temf.Logger;
import edu.uoc.som.temf.core.InternalTObject;
import edu.uoc.som.temf.core.TObject;
import edu.uoc.som.temf.core.TResource;
import edu.uoc.som.temf.core.exceptions.EClassNotFoundException;
import edu.uoc.som.temf.core.impl.TObjectAdapterFactoryImpl;
import edu.uoc.som.temf.estores.SearcheableResourceTStore;

public class DirectWriteMapResourceTStoreImpl implements SearcheableResourceTStore {

	protected static final String DATA = "data";
	protected static final String INSTANCE_OF = "instanceOf";
	protected static final String CONTAINER = "eContainer";

	protected LoadingCache<String, InternalTObject> loadedEObjects = CacheBuilder.newBuilder().softValues().build(new CacheLoader<String, InternalTObject>() {
		@Override
		public InternalTObject load(String key) throws Exception {
				EClass eClass = resolveInstanceOf(key);
				if (eClass == null) {
					throw new EClassNotFoundException(MessageFormat.format("Element {0} does not have an associated EClass", key));
				}
				EObject eObject = EcoreUtil.create(eClass);
				InternalTObject tObject = TObjectAdapterFactoryImpl.getAdapter(eObject, InternalTObject.class);
				tObject.tSetId(key);
				return tObject;
			}
	});
	
	protected MVStore mvStore;
	
	protected MVMap<DataKey, Object> dataMap; // Object[] must be an array of { String, String, Date }
	
	protected MVMap<String, EClassInfo> instanceOfMap;

	protected MVMap<ContainerKey, ContainerInfo> containersMap; // Object[] must be an array of { String, Date }
	
	protected TResource resource;

	DirectWriteMapResourceTStoreImpl(TResource resource, MVStore mvStore) {
		this.mvStore = mvStore;
		this.resource = (TResource) resource;
		this.dataMap = mvStore.openMap(DATA);
		this.instanceOfMap = mvStore.openMap(INSTANCE_OF);
		this.containersMap = mvStore.openMap(CONTAINER);
	}


	@Override
	public Resource.Internal getResource() {
		return resource;
	}

	@Override
	public Object get(InternalEObject object, EStructuralFeature feature, int index) {
		return getAt(now(), object, feature, index);
	}

	@Override
	public Object getAt(Instant instant, InternalEObject object, EStructuralFeature feature, int index) {
		TObject tObject = TObjectAdapterFactoryImpl.getAdapter(object, TObject.class);
		if (feature instanceof EAttribute) {
			return getAt(instant, tObject, (EAttribute) feature, index);
		} else if (feature instanceof EReference) {
			return getAt(instant, tObject, (EReference) feature, index);
		} else {
			throw new IllegalArgumentException(feature.toString());
		}
	}

	protected Object getAt(Instant instant, TObject object, EAttribute eAttribute, int index) {
		Object value = getFromDataMap(instant, object, eAttribute);
		if (!eAttribute.isMany()) {
			return parseMapValue(eAttribute, (String) value);
		} else {
			Object[] array = (Object[]) value;
			return parseMapValue(eAttribute, array[index]);
		}
	}

	protected Object getAt(Instant instant, TObject object, EReference eReference, int index) {
		Object value = getFromDataMap(instant, object, eReference);
		if (!eReference.isMany()) {
			return getEObject((String) value);
		} else {
			Object[] array = Arrays.stream((Object[]) value).toArray(Object[]::new);
			return getEObject((String) array[index]);
		}
	}

	@Override
	public SortedMap<Instant, Object> getAllBetween(Instant startInstant, Instant endInstant, InternalEObject object, EStructuralFeature feature, int index) {
		TObject tObject = TObjectAdapterFactoryImpl.getAdapter(object, TObject.class);
		if (feature instanceof EAttribute) {
			return getAllBetween(startInstant, endInstant, tObject, (EAttribute) feature, index);
		} else if (feature instanceof EReference) {
			return getAllBetween(startInstant, endInstant, tObject, (EReference) feature, index);
		} else {
			throw new IllegalArgumentException(feature.toString());
		}
	}
	
	protected SortedMap<Instant, Object> getAllBetween(Instant startInstant, Instant endInstant, TObject object, EAttribute eAttribute, int index) {
		SortedMap<Instant, Object> result = new TreeMap<>();
		TreeMap<DataKey, Object> all = getAllFromDataMap(startInstant, endInstant, object, eAttribute);
		if (!eAttribute.isMany()) {
			all.forEach((key, obj) ->  result.put(key.instant, parseMapValue(eAttribute, (String) obj)));
		} else {
			all.forEach((key, obj) ->  result.put(key.instant, parseMapValue(eAttribute, parseMapValue(eAttribute, ((Object[]) obj)[index]))));
		}
		return result;
	}
	
	protected SortedMap<Instant, Object> getAllBetween(Instant startInstant, Instant endInstant, TObject object, EReference eReference, int index) {
		SortedMap<Instant, Object> result = new TreeMap<>();
		TreeMap<DataKey, Object> all = getAllFromDataMap(startInstant, endInstant, object, eReference);
		if (!eReference.isMany()) {
			all.forEach((key, obj) ->  result.put(key.instant, getEObject((String) obj)));
		} else {
			all.forEach((key, obj) ->  result.put(key.instant, getEObject((String) ((Object[]) obj)[index])));
		}
		return result;
	}
	@Override
	public Object set(InternalEObject object, EStructuralFeature feature, int index, Object value) {
		TObject tObject = TObjectAdapterFactoryImpl.getAdapter(object, TObject.class);
		if (feature instanceof EAttribute) {
			return set(tObject, (EAttribute) feature, index, value);
		} else if (feature instanceof EReference) {
			TObject referencedEObject = TObjectAdapterFactoryImpl.getAdapter(value, TObject.class);
			return set(tObject, (EReference) feature, index, referencedEObject);
		} else {
			throw new IllegalArgumentException(feature.toString());
		}
	}

	protected Object set(TObject object, EAttribute eAttribute, int index, Object value) {
		if (!eAttribute.isMany()) {
			Object oldValue = dataMap.put(DataKey.from(object.tId(), eAttribute.getName(), now()), serializeToMapValue(eAttribute, value));
			return parseMapValue(eAttribute, oldValue);
		} else {
			Object[] array = (Object[]) getFromDataMap(object, eAttribute);
			Object oldValue = array[index]; 
			array[index] = serializeToMapValue(eAttribute, value);
			dataMap.put(DataKey.from(object.tId(), eAttribute.getName(), now()), array);
			return parseMapValue(eAttribute, oldValue);
		}
	}

	protected Object set(TObject object, EReference eReference, int index, TObject referencedObject) {
		updateContainment(object, eReference, referencedObject);
		updateInstanceOf(referencedObject);
		if (!eReference.isMany()) {
			Object oldId = dataMap.put(DataKey.from(object.tId(), eReference.getName(), now()), referencedObject.tId());
			return oldId != null ? getEObject((String) oldId) : null;
		} else {
			Object[] array = (Object[]) getFromDataMap(object, eReference);
			Object oldId = array[index];
			array[index] = referencedObject.tId();
			dataMap.put(DataKey.from(object.tId(), eReference.getName(), now()), array);
			return oldId != null ? getEObject((String) oldId) : null;
		}
	}


	@Override
	public boolean isSet(InternalEObject object, EStructuralFeature feature) {
		return isSetAt(now(), object, feature);
	}

	
	@Override
	public boolean isSetAt(Instant instant, InternalEObject object, EStructuralFeature feature) {
		TObject tObject = TObjectAdapterFactoryImpl.getAdapter(object, TObject.class);
		return getFromDataMap(instant, tObject, feature) != null;
	}


	@Override
	public void add(InternalEObject object, EStructuralFeature feature, int index, Object value) {
		TObject tObject = TObjectAdapterFactoryImpl.getAdapter(object, TObject.class);
		if (feature instanceof EAttribute) {
			add(tObject, (EAttribute) feature, index, value);
		} else if (feature instanceof EReference) {
			TObject referencedEObject = TObjectAdapterFactoryImpl.getAdapter(value, TObject.class);
			add(tObject, (EReference) feature, index, referencedEObject);
		} else {
			throw new IllegalArgumentException(feature.toString());
		}
	}

	protected void add(TObject object, EAttribute eAttribute, int index, Object value) {
		Object[] array = (Object[]) getFromDataMap(object, eAttribute);
		if (array == null) {
			array = new Object[] {};
		}
		array = ArrayUtils.add(array, index, serializeToMapValue(eAttribute, value));
		dataMap.put(DataKey.from(object.tId(), eAttribute.getName(), now()), array);
	}

	protected void add(TObject object, EReference eReference, int index, TObject referencedObject) {
		updateContainment(object, eReference, referencedObject);
		updateInstanceOf(referencedObject);
		Object[] array = (Object[]) getFromDataMap(object, eReference);
		if (array == null) {
			array = new Object[] {};
		}
		array = ArrayUtils.add(array, index, referencedObject.tId());
		dataMap.put(DataKey.from(object.tId(), eReference.getName(), now()), array);
	}

	@Override
	public Object remove(InternalEObject object, EStructuralFeature feature, int index) {
		TObject tObject = TObjectAdapterFactoryImpl.getAdapter(object, TObject.class);
		if (feature instanceof EAttribute) {
			return remove(tObject, (EAttribute) feature, index);
		} else if (feature instanceof EReference) {
			return remove(tObject, (EReference) feature, index);
		} else {
			throw new IllegalArgumentException(feature.toString());
		}
	}

	protected Object remove(TObject object, EAttribute eAttribute, int index) {
		Object[] array = (Object[]) getFromDataMap(object, eAttribute);
		Object oldValue = array[index];
		array = ArrayUtils.remove(array, index);
		dataMap.put(DataKey.from(object.tId(), eAttribute.getName(), now()), array);
		return parseMapValue(eAttribute, oldValue);
	}

	protected Object remove(TObject object, EReference eReference, int index) {
		Object[] array = (Object[]) getFromDataMap(object, eReference);
		Object oldId = array[index];
		array = ArrayUtils.remove(array, index);
		dataMap.put(DataKey.from(object.tId(), eReference.getName(), now()), array);
		return getEObject((String) oldId);

	}

	@Override
	public Object move(InternalEObject object, EStructuralFeature feature, int targetIndex, int sourceIndex) {
		Object movedElement = remove(object, feature, sourceIndex);
		add(object, feature, targetIndex, movedElement);
		return movedElement;
	}


	@Override
	public void unset(InternalEObject object, EStructuralFeature feature) {
		TObject tObject = TObjectAdapterFactoryImpl.getAdapter(object, TObject.class);
		dataMap.remove(DataKey.from(tObject.tId(), feature.getName(), now()));
	}

	@Override
	public boolean isEmpty(InternalEObject object, EStructuralFeature feature) {
		return isEmptyAt(now(), object, feature);
	}
	
	@Override
	public boolean isEmptyAt(Instant instant, InternalEObject object, EStructuralFeature feature) {
		return sizeAt(instant, object, feature) == 0;
	}

	@Override
	public int size(InternalEObject object, EStructuralFeature feature) {
		return sizeAt(now(), object, feature);
	}
	
	@Override
	public int sizeAt(Instant instant, InternalEObject object, EStructuralFeature feature) {
		TObject tObject = TObjectAdapterFactoryImpl.getAdapter(object, TObject.class);
		Object[] array = (Object[]) getFromDataMap(instant, tObject, feature);
		return array != null ? array.length : 0;
	}

	@Override
	public boolean contains(InternalEObject object, EStructuralFeature feature, Object value) {
		return containsAt(now(), object, feature, value);
	}

	@Override
	public boolean containsAt(Instant instant, InternalEObject object, EStructuralFeature feature, Object value) {
		return indexOfAt(instant, object, feature, value) != -1;
	}
	
	@Override
	public int indexOf(InternalEObject object, EStructuralFeature feature, Object value) {
		return indexOfAt(now(), object, feature, value);
	}
	
	@Override
	public int indexOfAt(Instant instant, InternalEObject object, EStructuralFeature feature, Object value) {
		TObject tObject = TObjectAdapterFactoryImpl.getAdapter(object, TObject.class);
		Object[] array = (Object[]) getFromDataMap(instant, tObject, feature);
		if (array == null) {
			return -1;
		}
		if (feature instanceof EAttribute) {
			return ArrayUtils.indexOf(array, serializeToMapValue((EAttribute) feature, value));
		} else {
			TObject childEObject = TObjectAdapterFactoryImpl.getAdapter(value, TObject.class);
			return ArrayUtils.indexOf(array, childEObject.tId());
		}
	}

	@Override
	public int lastIndexOf(InternalEObject object, EStructuralFeature feature, Object value) {
		return lastIndexOfAt(now(), object, feature, value);
	}
	
	@Override
	public int lastIndexOfAt(Instant instant, InternalEObject object, EStructuralFeature feature, Object value) {
		TObject tObject = TObjectAdapterFactoryImpl.getAdapter(object, TObject.class);
		Object[] array = (Object[]) getFromDataMap(instant, tObject, feature);
		if (array == null) {
			return -1;
		}
		if (feature instanceof EAttribute) {
			return ArrayUtils.lastIndexOf(array, serializeToMapValue((EAttribute) feature, value));
		} else {
			TObject childEObject = TObjectAdapterFactoryImpl.getAdapter(value, TObject.class);
			return ArrayUtils.lastIndexOf(array, childEObject.tId());
		}
	}

	@Override
	public void clear(InternalEObject object, EStructuralFeature feature) {
		TObject tObject = TObjectAdapterFactoryImpl.getAdapter(object, TObject.class);
		dataMap.put(DataKey.from(tObject.tId(), feature.getName(), now()), new Object[] {});
	}

	@Override
	public Object[] toArray(InternalEObject object, EStructuralFeature feature) {
		return toArrayAt(now(), object, feature);
	}
	
	@Override
	public Object[] toArrayAt(Instant instant, InternalEObject object, EStructuralFeature feature) {
		int size = sizeAt(instant, object, feature);
		Object[] result = new Object[size];
		for (int index = 0; index < size; index++) {
			result[index] = getAt(instant, object, feature, index);
		}
		return result;
	}

	@Override
	public <T> T[] toArray(InternalEObject object, EStructuralFeature feature, T[] array) {
		return toArrayAt(now(), object, feature, array);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArrayAt(Instant instant, InternalEObject object, EStructuralFeature feature, T[] array) {
		int size = sizeAt(instant, object, feature);
		T[] result = null;
		if (array.length < size) {
			result = Arrays.copyOf(array, size);
		} else {
			result = array;
		}
		for (int index = 0; index < size; index++) {
			result[index] = (T) getAt(instant, object, feature, index);
		}
		return result;
	}
	
	@Override
	public SortedMap<Instant, Object[]> toArrayAllBetween(Instant startInstant, Instant endInstant, InternalEObject object, EStructuralFeature feature) {
		TObject tObject = TObjectAdapterFactoryImpl.getAdapter(object, TObject.class);

		SortedMap<Instant, Object[]> result = new TreeMap<>();
		TreeMap<DataKey, Object> all = getAllFromDataMap(startInstant, endInstant, tObject, feature);
		
		if (feature instanceof EAttribute) {
			all.forEach((key, obj) ->  result.put(key.instant, Arrays.asList((Object[]) obj).stream().map(v -> parseMapValue((EAttribute) feature, (String) v)).toArray()));
		} else if (feature instanceof EReference) {
			all.forEach((key, obj) ->  result.put(key.instant, Arrays.asList((Object[]) obj).stream().map(v -> getEObject((String) v)).toArray()));
		} else {
			throw new IllegalArgumentException(feature.toString());
		}
		
		return result;
	}
	@Override
	public int hashCode(InternalEObject object, EStructuralFeature feature) {
		return hashCodeAt(now(), object, feature);
	}
	
	@Override
	public int hashCodeAt(Instant instant, InternalEObject object, EStructuralFeature feature) {
		return toArrayAt(instant, object, feature).hashCode();
	}

	@Override
	public InternalEObject getContainer(InternalEObject object) {
		return getContainerAt(now(), object); 
	}


	@Override
	public InternalEObject getContainerAt(Instant instant, InternalEObject object) {
		TObject tObject = TObjectAdapterFactoryImpl.getAdapter(object, TObject.class);
		ContainerInfo lower = containersMap.get(containersMap.ceilingKey(ContainerKey.from(tObject.tId(), instant)));
		if (lower != null) {
			return (InternalEObject) getEObject(lower.containerId);
		}
		return null;
	}


	@Override
	public EStructuralFeature getContainingFeature(InternalEObject object) {
		return getContainingFeatureAt(now(), object);
	}

	@Override
	public EStructuralFeature getContainingFeatureAt(Instant instant, InternalEObject object) {
		TObject tObject = TObjectAdapterFactoryImpl.getAdapter(object, TObject.class);
		ContainerInfo lower = containersMap.get(containersMap.ceilingKey(ContainerKey.from(tObject.tId(), instant)));
		if (lower != null) {
			EObject container = getEObject(lower.containerId);
			container.eClass().getEStructuralFeature(lower.containingFeatureName);
		}
		return null;
	}

	@Override
	public EObject create(EClass eClass) {
		// This should not be called
		throw new UnsupportedOperationException();
	}


	@Override
	public EObject getEObject(String id) {
		if (id == null) {
			return null;
		}
		InternalTObject tObject = loadedEObjects.getUnchecked(id);
		if (tObject == null) {
			EClass eClass = resolveInstanceOf(id);
			if (eClass != null) {
				EObject eObject = EcoreUtil.create(eClass);
				if (eObject instanceof InternalTObject) {
					tObject = (InternalTObject) eObject;
				} else {
					tObject = TObjectAdapterFactoryImpl.getAdapter(eObject, InternalTObject.class);
				}
				tObject.tSetId(id.toString());
			} else {
				Logger.log(Logger.SEVERITY_ERROR, 
						MessageFormat.format("Element {0} does not have an associated EClass", id));
			}
			loadedEObjects.put(id, tObject);
		}
		if (tObject.tResource() != getResource()) {
			tObject.tSetResource(getResource());
		}
		return tObject;
	}
	

	protected EClass resolveInstanceOf(String id) {
		EClassInfo eClassInfo = instanceOfMap.get(id);
		if (eClassInfo != null) {
			EClass eClass = (EClass) Registry.INSTANCE.getEPackage(eClassInfo.nsURI).getEClassifier(eClassInfo.className);
			return eClass;
		}
		return null;
	}
	
	protected void updateContainment(TObject object, EReference eReference, TObject referencedObject) {
		if (eReference.isContainment()) {
			ContainerInfo lower = containersMap.get(containersMap.ceilingKey(ContainerKey.from(referencedObject.tId(), now())));
			if (lower == null || !StringUtils.equals(lower.containerId, object.tId())) {
				containersMap.put(ContainerKey.from(referencedObject.tId(), now()), ContainerInfo.from(object.tId(), eReference.getName()));
			}
		}
	}
	
	protected void updateInstanceOf(TObject object) {
		EClassInfo info = instanceOfMap.get(object.tId());
		if (info == null) {
			instanceOfMap.put(object.tId(), EClassInfo.from(object.eClass().getEPackage().getNsURI(), object.eClass().getName()));
		}
	}


	protected static Object parseMapValue(EAttribute eAttribute, Object property) {
		return property != null ? EcoreUtil.createFromString(eAttribute.getEAttributeType(), property.toString()) : null;
	}

	protected static String serializeToMapValue(EAttribute eAttribute, Object value) {
		return value != null ? EcoreUtil.convertToString(eAttribute.getEAttributeType(), value) : null;
	}
	
	protected Object getFromDataMap(TObject object, EStructuralFeature feature) {
		return getFromDataMap(Instant.MAX, object, feature);
	}

	/**
	 * Gets the latest value for {@link EStructuralFeature} {@code feature} from the
	 * data map for the {@link TObject} {@code object} before <code>endInstant</code>. 
	 * 
	 * @param endInstant
	 *            the end instant or <code>null</null> to indicate the latest possible time.
	 * @param object
	 * @param feature
	 * @return The value of the {@code feature}. It can be a {@link String} for
	 *         single-valued {@link EStructuralFeature}s or a {@link String}[] for
	 *         many-valued {@link EStructuralFeature}s
	 */
	protected Object getFromDataMap(Instant endInstant, TObject object, EStructuralFeature feature) {
		DataKey floorKey = dataMap.floorKey(DataKey.from(object.tId(), feature.getName(), endInstant));
		if (floorKey != null && object.tId().equals(floorKey.id) && feature.getName().equals(floorKey.feature)) {
			return dataMap.get(floorKey);
		} else {
			return null;
		}
	}
	
	protected TreeMap<DataKey, Object> getAllFromDataMap(Instant startInstant, Instant endInstant, TObject object, EStructuralFeature feature) {
		TreeMap<DataKey, Object> result = new TreeMap<>();
		DataKey firstKey = dataMap.ceilingKey(DataKey.from(object.tId(), feature.getName(), startInstant));
		DataKey lastKey = dataMap.floorKey(DataKey.from(object.tId(), feature.getName(), startInstant));
		Cursor<DataKey, Object> c = dataMap.cursor(firstKey);
		while (!c.getKey().equals(lastKey)) {
			result.put(c.getKey(), c.getValue());
			c.next();
		};
		return result; 
	}

	private Instant now() {
		return resource.getClock().instant();
	}
	
	private static class DataKey implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		public final String id;
		public final String feature;
		public final Instant instant;
		
		private DataKey(String id, String feature, Instant instant) {
			this.id = id;
			this.feature = feature;
			this.instant = instant;
		}
		
		public static DataKey from(String id, String feature, Instant instant) {
			return new DataKey(id, feature, instant);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((feature == null) ? 0 : feature.hashCode());
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result + ((instant == null) ? 0 : instant.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DataKey other = (DataKey) obj;
			if (feature == null) {
				if (other.feature != null)
					return false;
			} else if (!feature.equals(other.feature))
				return false;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (instant == null) {
				if (other.instant != null)
					return false;
			} else if (!instant.equals(other.instant))
				return false;
			return true;
		}


	}
	
	private static class ContainerKey implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		public final String id;
		public final Instant instant;
		
		private ContainerKey(String id, Instant instant) {
			this.id = id;
			this.instant = instant;
		}
		
		public static ContainerKey from(String id, Instant instant) {
			return new ContainerKey(id, instant);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result + ((instant == null) ? 0 : instant.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ContainerKey other = (ContainerKey) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (instant == null) {
				if (other.instant != null)
					return false;
			} else if (!instant.equals(other.instant))
				return false;
			return true;
		}

	}
	
	private static class ContainerInfo implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		public final String containerId;
		
		public final String containingFeatureName;
		
		private ContainerInfo(String containerId, String containingFeatureName) {
			this.containerId = containerId;
			this.containingFeatureName = containingFeatureName;
		}
		
		public static ContainerInfo from(String containerId, String containingFeatureName) {
			return new ContainerInfo(containerId, containingFeatureName);
		}
	}
	
	private static class EClassInfo implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		public final String nsURI;
		
		public final String className;
		
		private EClassInfo(String nsURI, String className) {
			this.nsURI = nsURI;
			this.className = className;
		}
		
		public static EClassInfo from(String nsURI, String className) {
			return new EClassInfo(nsURI, className);
		}
	}
}
