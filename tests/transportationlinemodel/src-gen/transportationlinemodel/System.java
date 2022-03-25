/**
 */
package transportationlinemodel;

import edu.uoc.som.temf.core.TObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>System</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link transportationlinemodel.System#getArea <em>Area</em>}</li>
 * </ul>
 *
 * @see transportationlinemodel.TransportationlinemodelPackage#getSystem()
 * @model
 * @extends TObject
 * @generated
 */
public interface System extends TObject {
	/**
	 * Returns the value of the '<em><b>Area</b></em>' containment reference list.
	 * The list contents are of type {@link transportationlinemodel.Area}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Area</em>' containment reference list.
	 * @see transportationlinemodel.TransportationlinemodelPackage#getSystem_Area()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Area> getArea();

} // System
