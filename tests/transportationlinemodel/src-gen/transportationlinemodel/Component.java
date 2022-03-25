/**
 */
package transportationlinemodel;

import edu.uoc.som.temf.core.TObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link transportationlinemodel.Component#getHosts <em>Hosts</em>}</li>
 *   <li>{@link transportationlinemodel.Component#getNeighbours <em>Neighbours</em>}</li>
 * </ul>
 *
 * @see transportationlinemodel.TransportationlinemodelPackage#getComponent()
 * @model
 * @extends TObject
 * @generated
 */
public interface Component extends TObject {
	/**
	 * Returns the value of the '<em><b>Hosts</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link transportationlinemodel.Item#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hosts</em>' reference.
	 * @see #setHosts(Item)
	 * @see transportationlinemodel.TransportationlinemodelPackage#getComponent_Hosts()
	 * @see transportationlinemodel.Item#getLocation
	 * @model opposite="location"
	 * @generated
	 */
	Item getHosts();

	/**
	 * Sets the value of the '{@link transportationlinemodel.Component#getHosts <em>Hosts</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hosts</em>' reference.
	 * @see #getHosts()
	 * @generated
	 */
	void setHosts(Item value);

	/**
	 * Returns the value of the '<em><b>Neighbours</b></em>' reference list.
	 * The list contents are of type {@link transportationlinemodel.Component}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Neighbours</em>' reference list.
	 * @see transportationlinemodel.TransportationlinemodelPackage#getComponent_Neighbours()
	 * @model
	 * @generated
	 */
	EList<Component> getNeighbours();

} // Component
