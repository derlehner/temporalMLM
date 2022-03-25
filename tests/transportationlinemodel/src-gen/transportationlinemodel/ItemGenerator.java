/**
 */
package transportationlinemodel;

import edu.uoc.som.temf.core.TObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Item Generator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link transportationlinemodel.ItemGenerator#getFrequency <em>Frequency</em>}</li>
 * </ul>
 *
 * @see transportationlinemodel.TransportationlinemodelPackage#getItemGenerator()
 * @model
 * @extends TObject
 * @generated
 */
public interface ItemGenerator extends TObject {
	/**
	 * Returns the value of the '<em><b>Frequency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Frequency</em>' attribute.
	 * @see #setFrequency(int)
	 * @see transportationlinemodel.TransportationlinemodelPackage#getItemGenerator_Frequency()
	 * @model
	 * @generated
	 */
	int getFrequency();

	/**
	 * Sets the value of the '{@link transportationlinemodel.ItemGenerator#getFrequency <em>Frequency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Frequency</em>' attribute.
	 * @see #getFrequency()
	 * @generated
	 */
	void setFrequency(int value);

} // ItemGenerator
