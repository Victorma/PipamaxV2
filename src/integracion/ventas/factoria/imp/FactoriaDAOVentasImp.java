/**
 * 
 */
package integracion.ventas.factoria.imp;

import integracion.ventas.factoria.FactoriaDAOVentas;
import integracion.ventas.imp.DAOVentasImp;
import integracion.ventas.DAOVentas;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Victorma
 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
public class FactoriaDAOVentasImp extends FactoriaDAOVentas {
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public DAOVentas getInstanciaDAOVentas() {
		// begin-user-code
		// TODO Apéndice de método generado automáticamente
		return new DAOVentasImp();
		// end-user-code
	}
}