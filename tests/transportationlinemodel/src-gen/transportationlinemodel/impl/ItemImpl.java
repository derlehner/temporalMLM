/**
 */
package transportationlinemodel.impl;

import org.eclipse.emf.ecore.EClass;

import transportationlinemodel.Component;
import transportationlinemodel.Item;
import transportationlinemodel.TransportationlinemodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link transportationlinemodel.impl.ItemImpl#isIsProcessed <em>Is Processed</em>}</li>
 *   <li>{@link transportationlinemodel.impl.ItemImpl#getLocation <em>Location</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ItemImpl extends NamedElementImpl implements Item {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransportationlinemodelPackage.Literals.ITEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsProcessed() {
		return (Boolean) eGet(TransportationlinemodelPackage.Literals.ITEM__IS_PROCESSED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsProcessed(boolean newIsProcessed) {
		eSet(TransportationlinemodelPackage.Literals.ITEM__IS_PROCESSED, newIsProcessed);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Component getLocation() {
		return (Component) eGet(TransportationlinemodelPackage.Literals.ITEM__LOCATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocation(Component newLocation) {
		eSet(TransportationlinemodelPackage.Literals.ITEM__LOCATION, newLocation);
	}

} //ItemImpl
