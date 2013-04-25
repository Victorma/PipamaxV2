/**
 * 
 */
package negocio.departamentos.factoria.imp;

import negocio.departamentos.SADepartamentos;
import negocio.departamentos.factoria.FactoriaSADepartamentos;
import negocio.departamentos.imp.SADepartamentosImp;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Victorma
 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
public class FactoriaSADepartamentosImp extends FactoriaSADepartamentos {

	@Override
	public SADepartamentos getInstanciaSADepartamentos() {
		// TODO Apéndice de método generado automáticamente
		return new SADepartamentosImp();
	}
}