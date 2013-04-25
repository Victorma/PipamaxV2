/**
 * 
 */
package negocio.empleados.factoria;

import negocio.empleados.SAEmpleados;
import negocio.empleados.factoria.imp.FactoriaSAEmpleadosImp;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Victorma
 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
public abstract class FactoriaSAEmpleados {
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private static FactoriaSAEmpleados instancia = null;

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public static FactoriaSAEmpleados getInstancia() {
		// begin-user-code
		// TODO Apéndice de método generado automáticamente
		if(instancia == null)
			instancia = new FactoriaSAEmpleadosImp();
		return instancia;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public abstract SAEmpleados getInstanciaSAEmpleados();
}