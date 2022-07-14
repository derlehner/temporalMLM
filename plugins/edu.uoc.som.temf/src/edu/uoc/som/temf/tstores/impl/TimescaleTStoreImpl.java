package edu.uoc.som.temf.tstores.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.SortedMap;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.uoc.som.temf.core.InternalTObject;
import edu.uoc.som.temf.core.TGlobalClock;
import edu.uoc.som.temf.core.TObject;
import edu.uoc.som.temf.core.TResource;
import edu.uoc.som.temf.core.impl.TObjectAdapterFactoryImpl;
import edu.uoc.som.temf.tstores.SearcheableTStore;

public class TimescaleTStoreImpl implements SearcheableTStore {
	private Connection con;
	private TResource resource;
	
	public TimescaleTStoreImpl(Connection con) {
		this(con, null);
	}

	public TimescaleTStoreImpl(Connection con, TResource resource) {
		this.con = con;
		this.resource = (TResource) resource;
	}

	@Override
	public Object get(InternalEObject object, EStructuralFeature feature, int index) {
		DriverManager.drivers().forEach(System.out::println);
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
		System.out.println("GetAt Method for eAttribute " + eAttribute.getName() + " called");
		try {
		Statement stmt = this.con.createStatement();
        String sql = "Select * from EAttribute where t <= '" + instant.toString() + "' and t >= (select max(t) from EAttribute where t <= '" + instant.toString() + "'"
        		+ "and type = '" + object.tId() + "' and id = '" + eAttribute.getName() + "')"
        		+ "and type = '" + object.tId() + "' and id = '" + eAttribute.getName() + "';";
        
        ResultSet rs = stmt.executeQuery( sql );
		rs.next();
		String value = rs.getString("value");
		// todo: also add the remaining resultset
        rs.close();
        stmt.close();
        return parseMapValue(eAttribute, value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object; 
	}
	

	
	protected Object getAt(Instant instant, TObject object, EReference eReference, int index) {
		System.out.println("GetAt Method for eReference" + eReference.getName() + " called");
		// todo: this should consider DummyEObject as main element, and return a reference to the top level elements here
		try {
			Statement stmt = this.con.createStatement();
	        String sql = "Select * from ereference where id = '" + eReference.getName() + "' and source = '" + object.tId() + "';";//where t <= '" + instant.toString() + "' and t >= (select max(t) from EReference where t <= '" + instant.toString() + "');";
	        
	        ResultSet rs = stmt.executeQuery(sql);
			while(index >= 0) {
				if(!rs.next()) {
					return null;//return getEObject(null);//throw new IllegalArgumentException("No value at index " + index);
				}
				index--;
			}
			String value = rs.getString("target");
			//System.out.print(" reference target: " + value);
	        rs.close();
	        stmt.close();
	        
	        return getEObject(value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
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
		System.out.println("Set Method for eAttribute " + eAttribute.getName() + " called");
		try {
		Statement stmt = this.con.createStatement();
        String sql = "Insert Into EAttribute(id, type, t, value) \r\n"
        		+ "VALUES \r\n"
        		+ "('" + eAttribute.getName() + "', '" + object.tId() + "', '" + now() + "', '" + value.toString() + "');";
        
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object; // TODO: what should be returned here??
        
        
		/*if (!eAttribute.isMany()) {
			Object oldValue = dataMap.put(DataKey.from(object.tId(), eAttribute.getName(), now()), serializeToMapValue(eAttribute, value));
			return parseMapValue(eAttribute, oldValue);
		} else {
			Object[] array = ArrayUtils.clone((Object[]) getFromDataMap(object, eAttribute).getValue());
			Object oldValue = array[index];
			array[index] = serializeToMapValue(eAttribute, value);
			dataMap.put(DataKey.from(object.tId(), eAttribute.getName(), now()), array);
			return parseMapValue(eAttribute, oldValue);
		}*/
	}
	
	protected static Object parseMapValue(EAttribute eAttribute, Object property) {
		return property != null ? EcoreUtil.createFromString(eAttribute.getEAttributeType(), property.toString()) : null;
	}

	protected Object set(TObject object, EReference eReference, int index, TObject referencedObject) {
		//updateContainment(object, eReference, referencedObject);
		//updateInstanceOf(referencedObject);
		throw new UnsupportedOperationException(); // TODO: this must be implemented once relations are supported
	}

	@Override
	public boolean isSet(InternalEObject object, EStructuralFeature feature) {
		return isSetAt(now(), object, feature);
	}
	
	@Override
	public boolean isSetAt(Instant instant, InternalEObject object, EStructuralFeature feature) {
		//TObject tObject = TObjectAdapterFactoryImpl.getAdapter(object, TObject.class);
		Object value = getAt(instant,  object,  feature, 0);
		return value != null;
	}

	@Override
	public void unset(InternalEObject object, EStructuralFeature feature) {
		try {
			Statement stmt = this.con.createStatement();
	        String sql = "Delete from EAttribute where id = '" + feature.getName() + "' and type = '" + object.getClass().getName() + "');";
	        stmt.execute(sql);
	
		} catch (SQLException e) {
				e.printStackTrace();
		}
		
	}

	@Override
	public boolean isEmpty(InternalEObject object, EStructuralFeature feature) {
		
		return get(object, feature, 0) != null;
	}

	@Override
	public int size(InternalEObject object, EStructuralFeature feature) {
		return sizeAt(now(), object, feature);
	}
	
	@Override
	public int sizeAt(Instant instant, InternalEObject object, EStructuralFeature feature) {
		int count = 0;
		try {
			Statement stmt = this.con.createStatement();
	        //String sql = "Select * from EAttribute where t <= '" + instant.toString() + "' and t >= (select max(t) from EAttribute where t <= '" + instant.toString() + "');";
			String sql = "Select * from EReference;";
	        ResultSet rs = stmt.executeQuery( sql );
	        
	        while(rs.next()){
	        	count++;
	        }
			return count;
		} catch (SQLException e) {
				e.printStackTrace();
		}
		return count;
	}

	@Override
	public boolean contains(InternalEObject object, EStructuralFeature feature, Object value) {
		try {
			Statement stmt = this.con.createStatement();
			String sql = "Select * from EAttribute where type = '" + object.getClass().getName() + "' and value = '" + value.toString() + "' and id = '" + feature.getName() + "';";
	        ResultSet rs = stmt.executeQuery( sql );
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	@Override
	public EObject getEObject(String id) {
		// TODO how to implement this???
		if (id == null) {
			return null;
		}
		try {
			Statement stmt = this.con.createStatement();
	        String sql = "Select * from EObject where id = '" + id + "';";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			String classId = rs.getString("type");
			stmt.close();
			//classId = "Item";
			EClass eClass = (EClass) Registry.INSTANCE.getEPackage("http://www.example.org/transportationlinemodel").getEClassifier(classId);
			//EClass eClass = (EClass) Registry.INSTANCE.getEPackage("transportationlinemodel.impl").getEClassifier(id);
			// code from h2 database: TObject tObject = loadedEObjects.getUnchecked(id);
			EObject eObject =  EcoreUtil.create(eClass);
			InternalTObject tObject = TObjectAdapterFactoryImpl.getAdapter(eObject, InternalTObject.class);
			tObject.tSetId(id);
			tObject.tSetResource(resource);
			return tObject;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public boolean isEmptyAt(Instant instant, InternalEObject object, EStructuralFeature feature) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public SortedMap<Instant, Object> getAllBetween(Instant startInstant, Instant endInstant, InternalEObject object,
			EStructuralFeature feature, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public boolean containsAt(Instant instant, InternalEObject object, EStructuralFeature feature, Object value) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	// Unsupported Operations

	@Override
	public int indexOf(InternalEObject object, EStructuralFeature feature, Object value) {
		// TODO: this must be implemented for multi-value attributes
		throw new UnsupportedOperationException();
	}

	@Override
	public int lastIndexOf(InternalEObject object, EStructuralFeature feature, Object value) {
		/// TODO: this must be implemented for multi-value attributes
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(InternalEObject object, EStructuralFeature feature, int index, Object value) {
		TObject tObject = TObjectAdapterFactoryImpl.getAdapter(object, TObject.class);
		if (feature instanceof EAttribute) {
			//add(tObject, (EAttribute) feature, index, value);
		} else if (feature instanceof EReference) {
			TObject referencedEObject = TObjectAdapterFactoryImpl.getAdapter(value, TObject.class);
			add(tObject, (EReference) feature, index, referencedEObject);
		} else {
			throw new IllegalArgumentException(feature.toString());
		}
		// throw new UnsupportedOperationException();
	}
	
	private void add(TObject source, EReference feature, int index, TObject target) {
		System.out.println("Add Method for eReference " + feature.getName() + " called");
		try {
		Statement stmt = this.con.createStatement();
        String sql = "Insert Into EReference(id, source, target, t) \r\n"
        		+ "VALUES \r\n"
        		+ "('" + feature.getName() + "', '" + source.tId() + "', '" + target.tId() + "', '" + now() + "');";
        
			stmt.executeUpdate(sql);
			stmt.close();
			if(!isObjectAlreadyPersisted(target)) {
				addTObject(target);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isObjectAlreadyPersisted(TObject obj) {
		try {
			Statement stmt = this.con.createStatement();
	        String sql = "Select * from EObject where id = '" + obj.tId() + "'";
	        ResultSet rs = stmt.executeQuery( sql );
	        if(rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void addTObject(TObject obj) {
		try {
			Statement stmt = this.con.createStatement();
	        String sql = "Insert Into EObject(id, type) \r\n"
        		+ "VALUES \r\n"
        		+ "('" + obj.tId() + "', '" + obj.getClass().getInterfaces()[0].getSimpleName() + "');";
        
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public Object remove(InternalEObject object, EStructuralFeature feature, int index) {
		// TODO: this must be implemented for multi-value attributes
		throw new UnsupportedOperationException();
	}

	@Override
	public Object move(InternalEObject object, EStructuralFeature feature, int targetIndex, int sourceIndex) {
		// TODO: this must be implemented for multi-value attributes
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear(InternalEObject object, EStructuralFeature feature) {
		// TODO this must be implemented when deleting values is allowed
		throw new UnsupportedOperationException();
		
	}

	@Override
	public Object[] toArray(InternalEObject object, EStructuralFeature feature) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(InternalEObject object, EStructuralFeature feature, T[] array) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int hashCode(InternalEObject object, EStructuralFeature feature) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public InternalEObject getContainer(InternalEObject object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public EStructuralFeature getContainingFeature(InternalEObject object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public EObject create(EClass eClass) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}



	

	

	@Override
	public Instant whenSet(InternalEObject object, EStructuralFeature feature) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public int indexOfAt(Instant instant, InternalEObject object, EStructuralFeature feature, Object value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOfAt(Instant instant, InternalEObject object, EStructuralFeature feature, Object value) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArrayAt(Instant instant, InternalEObject object, EStructuralFeature feature) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArrayAt(Instant instant, InternalEObject object, EStructuralFeature feature, T[] array) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public SortedMap<Instant, Object[]> toArrayAllBetween(Instant startInstant, Instant endInstant,
			InternalEObject object, EStructuralFeature feature) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int hashCodeAt(Instant instant, InternalEObject object, EStructuralFeature feature) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public InternalEObject getContainerAt(Instant instant, InternalEObject object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public EStructuralFeature getContainingFeatureAt(Instant instant, InternalEObject object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	
	
	private Instant now() {
		if (resource != null) {
			return resource.getClock().instant();
		} else {
			return TGlobalClock.INSTANCE.instant();
		}
	}
	
	public void cleanup() {
		try {
			Statement stmt = this.con.createStatement();
	        String sql = "Delete from EAttribute;";
			stmt.execute(sql);
			sql = "Delete from EReference where target != 'dummy';";
			stmt.execute(sql);
			sql = "Delete from EObject where id != 'dummy' and id != 'ROOT';";
			stmt.execute(sql);
			stmt.close();
			
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
