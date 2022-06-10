/**
 */
package transportationlinemodel.impl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import transportationlinemodel.Area;
import transportationlinemodel.TransportationlinemodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>System</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link transportationlinemodel.impl.SystemImpl#getArea <em>Area</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SystemImpl extends NamedElementImpl implements transportationlinemodel.System {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SystemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransportationlinemodelPackage.Literals.SYSTEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Area> getArea() {
		return (EList<Area>) eGet(TransportationlinemodelPackage.Literals.SYSTEM__AREA, true);
	}

} //SystemImpl
