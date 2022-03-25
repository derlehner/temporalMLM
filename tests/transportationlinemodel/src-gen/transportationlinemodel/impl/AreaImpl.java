/**
 */
package transportationlinemodel.impl;

import edu.uoc.som.temf.core.impl.TObjectImpl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import transportationlinemodel.Area;
import transportationlinemodel.Component;
import transportationlinemodel.Item;
import transportationlinemodel.ItemGenerator;
import transportationlinemodel.TransportationlinemodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Area</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link transportationlinemodel.impl.AreaImpl#getItemgenerator <em>Itemgenerator</em>}</li>
 *   <li>{@link transportationlinemodel.impl.AreaImpl#getItem <em>Item</em>}</li>
 *   <li>{@link transportationlinemodel.impl.AreaImpl#getComponent <em>Component</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AreaImpl extends TObjectImpl implements Area {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AreaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransportationlinemodelPackage.Literals.AREA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ItemGenerator getItemgenerator() {
		return (ItemGenerator) eGet(TransportationlinemodelPackage.Literals.AREA__ITEMGENERATOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setItemgenerator(ItemGenerator newItemgenerator) {
		eSet(TransportationlinemodelPackage.Literals.AREA__ITEMGENERATOR, newItemgenerator);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Item> getItem() {
		return (EList<Item>) eGet(TransportationlinemodelPackage.Literals.AREA__ITEM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Component> getComponent() {
		return (EList<Component>) eGet(TransportationlinemodelPackage.Literals.AREA__COMPONENT, true);
	}

} //AreaImpl
