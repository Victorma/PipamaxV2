/**
 * 
 */
package negocio.empleados.factoria.imp;

import negocio.empleados.factoria.FactoriaSAEmpleados;
import negocio.empleados.imp.SAEmpleadosImp;
import negocio.empleados.SAEmpleados;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Victorma
 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
public class FactoriaSAEmpleadosImp extends FactoriaSAEmpleados {
	/** 
	 * (sin Javadoc)
	 * @see FactoriaSAEmpleados#getInstanciaSAEmpleados()
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public SAEmpleados getInstanciaSAEmpleados() {
		// begin-user-code
		// TODO Apéndice de método generado automáticamente
		return new SAEmpleadosImp();
		// end-user-code
	}
}