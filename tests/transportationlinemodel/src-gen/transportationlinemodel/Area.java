/**
 */
package transportationlinemodel;

import edu.uoc.som.temf.core.TObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Area</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link transportationlinemodel.Area#getItemgenerator <em>Itemgenerator</em>}</li>
 *   <li>{@link transportationlinemodel.Area#getItem <em>Item</em>}</li>
 *   <li>{@link transportationlinemodel.Area#getComponent <em>Component</em>}</li>
 * </ul>
 *
 * @see transportationlinemodel.TransportationlinemodelPackage#getArea()
 * @model
 * @extends TObject
 * @generated
 */
public interface Area extends TObject {
	/**
	 * Returns the value of the '<em><b>Itemgenerator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Itemgenerator</em>' containment reference.
	 * @see #setItemgenerator(ItemGenerator)
	 * @see transportationlinemodel.TransportationlinemodelPackage#getArea_Itemgenerator()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ItemGenerator getItemgenerator();

	/**
	 * Sets the value of the '{@link transportationlinemodel.Area#getItemgenerator <em>Itemgenerator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Itemgenerator</em>' containment reference.
	 * @see #getItemgenerator()
	 * @generated
	 */
	void setItemgenerator(ItemGenerator value);

	/**
	 * Returns the value of the '<em><b>Item</b></em>' containment reference list.
	 * The list contents are of type {@link transportationlinemodel.Item}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Item</em>' containment reference list.
	 * @see transportationlinemodel.TransportationlinemodelPackage#getArea_Item()
	 * @model containment="true"
	 * @generated
	 */
	EList<Item> getItem();

	/**
	 * Returns the value of the '<em><b>Component</b></em>' containment reference list.
	 * The list contents are of type {@link transportationlinemodel.Component}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component</em>' containment reference list.
	 * @see transportationlinemodel.TransportationlinemodelPackage#getArea_Component()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Component> getComponent();

} // Area
