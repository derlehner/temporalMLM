/**
 */
package multilevelmodel.impl;

import edu.uoc.som.temf.core.impl.TObjectImpl;

import multilevelmodel.Clabject;
import multilevelmodel.Model;
import multilevelmodel.MultilevelmodelPackage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link multilevelmodel.impl.ModelImpl#getClabject <em>Clabject</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModelImpl extends TObjectImpl implements Model {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MultilevelmodelPackage.Literals.MODEL;
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
	public EList<Clabject> getClabject() {
		return (EList<Clabject>) eGet(MultilevelmodelPackage.Literals.MODEL__CLABJECT, true);
	}

} //ModelImpl
