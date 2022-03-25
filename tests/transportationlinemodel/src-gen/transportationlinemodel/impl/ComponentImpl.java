/**
 */
package transportationlinemodel.impl;

import edu.uoc.som.temf.core.impl.TObjectImpl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import transportationlinemodel.Component;
import transportationlinemodel.Item;
import transportationlinemodel.TransportationlinemodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link transportationlinemodel.impl.ComponentImpl#getHosts <em>Hosts</em>}</li>
 *   <li>{@link transportationlinemodel.impl.ComponentImpl#getNeighbours <em>Neighbours</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ComponentImpl extends TObjectImpl implements Component {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransportationlinemodelPackage.Literals.COMPONENT;
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
	public Item getHosts() {
		return (Item) eGet(TransportationlinemodelPackage.Literals.COMPONENT__HOSTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHosts(Item newHosts) {
		eSet(TransportationlinemodelPackage.Literals.COMPONENT__HOSTS, newHosts);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Component> getNeighbours() {
		return (EList<Component>) eGet(TransportationlinemodelPackage.Literals.COMPONENT__NEIGHBOURS, true);
	}

} //ComponentImpl
