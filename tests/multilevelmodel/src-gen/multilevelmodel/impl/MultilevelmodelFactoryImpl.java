/**
 */
package multilevelmodel.impl;

import multilevelmodel.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MultilevelmodelFactoryImpl extends EFactoryImpl implements MultilevelmodelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static MultilevelmodelFactory init() {
		try {
			MultilevelmodelFactory theMultilevelmodelFactory = (MultilevelmodelFactory) EPackage.Registry.INSTANCE
					.getEFactory(MultilevelmodelPackage.eNS_URI);
			if (theMultilevelmodelFactory != null) {
				return theMultilevelmodelFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MultilevelmodelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultilevelmodelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case MultilevelmodelPackage.CLABJECT:
			return (EObject) createClabject();
		case MultilevelmodelPackage.SLOT:
			return (EObject) createSlot();
		case MultilevelmodelPackage.MODEL:
			return (EObject) createModel();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Clabject createClabject() {
		ClabjectImpl clabject = new ClabjectImpl();
		return clabject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Slot createSlot() {
		SlotImpl slot = new SlotImpl();
		return slot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Model createModel() {
		ModelImpl model = new ModelImpl();
		return model;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultilevelmodelPackage getMultilevelmodelPackage() {
		return (MultilevelmodelPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static MultilevelmodelPackage getPackage() {
		return MultilevelmodelPackage.eINSTANCE;
	}

} //MultilevelmodelFactoryImpl
