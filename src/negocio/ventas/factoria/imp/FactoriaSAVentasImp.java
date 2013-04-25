/**
 * 
 */
package negocio.ventas.factoria.imp;

import negocio.ventas.factoria.FactoriaSAVentas;
import negocio.ventas.imp.SAVentasImp;
import negocio.ventas.SAVentas;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Victorma
 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
public class FactoriaSAVentasImp extends FactoriaSAVentas {
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public SAVentas getInstanciaSAVentas() {
		// begin-user-code
		// TODO Apéndice de método generado automáticamente
		return new SAVentasImp();
		// end-user-code
	}
}