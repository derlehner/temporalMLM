/**
 */
package transportationlinemodel.impl;

import edu.uoc.som.temf.core.impl.TObjectImpl;

import org.eclipse.emf.ecore.EClass;

import transportationlinemodel.ItemGenerator;
import transportationlinemodel.TransportationlinemodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Item Generator</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link transportationlinemodel.impl.ItemGeneratorImpl#getFrequency <em>Frequency</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ItemGeneratorImpl extends TObjectImpl implements ItemGenerator {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ItemGeneratorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransportationlinemodelPackage.Literals.ITEM_GENERATOR;
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
	public int getFrequency() {
		return (Integer) eGet(TransportationlinemodelPackage.Literals.ITEM_GENERATOR__FREQUENCY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFrequency(int newFrequency) {
		eSet(TransportationlinemodelPackage.Literals.ITEM_GENERATOR__FREQUENCY, newFrequency);
	}

} //ItemGeneratorImpl
