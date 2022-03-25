/**
 */
package transportationlinemodel;

import edu.uoc.som.temf.core.TObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link transportationlinemodel.Item#isIsProcessed <em>Is Processed</em>}</li>
 *   <li>{@link transportationlinemodel.Item#getLocation <em>Location</em>}</li>
 * </ul>
 *
 * @see transportationlinemodel.TransportationlinemodelPackage#getItem()
 * @model
 * @extends TObject
 * @generated
 */
public interface Item extends TObject {
	/**
	 * Returns the value of the '<em><b>Is Processed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Processed</em>' attribute.
	 * @see #setIsProcessed(boolean)
	 * @see transportationlinemodel.TransportationlinemodelPackage#getItem_IsProcessed()
	 * @model
	 * @generated
	 */
	boolean isIsProcessed();

	/**
	 * Sets the value of the '{@link transportationlinemodel.Item#isIsProcessed <em>Is Processed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Processed</em>' attribute.
	 * @see #isIsProcessed()
	 * @generated
	 */
	void setIsProcessed(boolean value);

	/**
	 * Returns the value of the '<em><b>Location</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link transportationlinemodel.Component#getHosts <em>Hosts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' reference.
	 * @see #setLocation(Component)
	 * @see transportationlinemodel.TransportationlinemodelPackage#getItem_Location()
	 * @see transportationlinemodel.Component#getHosts
	 * @model opposite="hosts"
	 * @generated
	 */
	Component getLocation();

	/**
	 * Sets the value of the '{@link transportationlinemodel.Item#getLocation <em>Location</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' reference.
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(Component value);

} // Item
