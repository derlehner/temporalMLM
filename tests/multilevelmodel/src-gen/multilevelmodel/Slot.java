/**
 */
package multilevelmodel;

import edu.uoc.som.temf.core.TObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Slot</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link multilevelmodel.Slot#getType <em>Type</em>}</li>
 *   <li>{@link multilevelmodel.Slot#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see multilevelmodel.MultilevelmodelPackage#getSlot()
 * @model
 * @extends TObject
 * @generated
 */
public interface Slot extends TObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(Slot)
	 * @see multilevelmodel.MultilevelmodelPackage#getSlot_Type()
	 * @model
	 * @generated
	 */
	Slot getType();

	/**
	 * Sets the value of the '{@link multilevelmodel.Slot#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(Slot value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see multilevelmodel.MultilevelmodelPackage#getSlot_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link multilevelmodel.Slot#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Slot
