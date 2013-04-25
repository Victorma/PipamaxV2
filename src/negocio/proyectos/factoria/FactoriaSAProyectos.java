/**
 * 
 */
package negocio.proyectos.factoria;

import negocio.proyectos.SAProyectos;
import negocio.proyectos.factoria.imp.FactoriaSAProyectosImp;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Victorma
 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
public abstract class FactoriaSAProyectos {
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private static FactoriaSAProyectos instancia;

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public static FactoriaSAProyectos getInstancia() {
		// begin-user-code
		// TODO Apéndice de método generado automáticamente
		if(instancia == null)
			instancia = new FactoriaSAProyectosImp();
		return instancia;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public abstract SAProyectos getInstanciaSAProyectos();
}