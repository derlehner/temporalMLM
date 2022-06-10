/**
 */
package multilevelmodel;

import edu.uoc.som.temf.core.TObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link multilevelmodel.Model#getClabject <em>Clabject</em>}</li>
 * </ul>
 *
 * @see multilevelmodel.MultilevelmodelPackage#getModel()
 * @model
 * @extends TObject
 * @generated
 */
public interface Model extends TObject {
	/**
	 * Returns the value of the '<em><b>Clabject</b></em>' containment reference list.
	 * The list contents are of type {@link multilevelmodel.Clabject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Clabject</em>' containment reference list.
	 * @see multilevelmodel.MultilevelmodelPackage#getModel_Clabject()
	 * @model containment="true"
	 * @generated
	 */
	EList<Clabject> getClabject();

} // Model
