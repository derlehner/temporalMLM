/**
 */
package multilevelmodel.impl;

import edu.uoc.som.temf.core.impl.TObjectImpl;

import multilevelmodel.Clabject;
import multilevelmodel.MultilevelmodelPackage;
import multilevelmodel.Slot;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Clabject</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link multilevelmodel.impl.ClabjectImpl#getSlot <em>Slot</em>}</li>
 *   <li>{@link multilevelmodel.impl.ClabjectImpl#getType <em>Type</em>}</li>
 *   <li>{@link multilevelmodel.impl.ClabjectImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ClabjectImpl extends TObjectImpl implements Clabject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClabjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MultilevelmodelPackage.Literals.CLABJECT;
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
	@SuppressWarnings("unchecked")
	public EList<Slot> getSlot() {
		return (EList<Slot>) eGet(MultilevelmodelPackage.Literals.CLABJECT__SLOT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Clabject getType() {
		return (Clabject) eGet(MultilevelmodelPackage.Literals.CLABJECT__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(Clabject newType) {
		eSet(MultilevelmodelPackage.Literals.CLABJECT__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String) eGet(MultilevelmodelPackage.Literals.CLABJECT__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(MultilevelmodelPackage.Literals.CLABJECT__NAME, newName);
	}

} //ClabjectImpl
