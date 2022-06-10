/**
 */
package multilevelmodel;

import edu.uoc.som.temf.core.TObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Clabject</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link multilevelmodel.Clabject#getSlot <em>Slot</em>}</li>
 *   <li>{@link multilevelmodel.Clabject#getType <em>Type</em>}</li>
 *   <li>{@link multilevelmodel.Clabject#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see multilevelmodel.MultilevelmodelPackage#getClabject()
 * @model
 * @extends TObject
 * @generated
 */
public interface Clabject extends TObject {
	/**
	 * Returns the value of the '<em><b>Slot</b></em>' containment reference list.
	 * The list contents are of type {@link multilevelmodel.Slot}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Slot</em>' containment reference list.
	 * @see multilevelmodel.MultilevelmodelPackage#getClabject_Slot()
	 * @model containment="true"
	 * @generated
	 */
	EList<Slot> getSlot();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(Clabject)
	 * @see multilevelmodel.MultilevelmodelPackage#getClabject_Type()
	 * @model
	 * @generated
	 */
	Clabject getType();

	/**
	 * Sets the value of the '{@link multilevelmodel.Clabject#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(Clabject value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see multilevelmodel.MultilevelmodelPackage#getClabject_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link multilevelmodel.Clabject#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Clabject
