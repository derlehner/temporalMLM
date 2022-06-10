/**
 */
package multilevelmodel.impl;

import edu.uoc.som.temf.core.impl.TObjectImpl;

import multilevelmodel.MultilevelmodelPackage;
import multilevelmodel.Slot;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Slot</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link multilevelmodel.impl.SlotImpl#getType <em>Type</em>}</li>
 *   <li>{@link multilevelmodel.impl.SlotImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SlotImpl extends TObjectImpl implements Slot {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SlotImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MultilevelmodelPackage.Literals.SLOT;
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
	public Slot getType() {
		return (Slot) eGet(MultilevelmodelPackage.Literals.SLOT__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(Slot newType) {
		eSet(MultilevelmodelPackage.Literals.SLOT__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String) eGet(MultilevelmodelPackage.Literals.SLOT__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(MultilevelmodelPackage.Literals.SLOT__NAME, newName);
	}

} //SlotImpl
