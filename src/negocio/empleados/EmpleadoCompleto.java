/**
 * 
 */
package negocio.empleados;

import javax.persistence.Entity;

import constantes.Constantes.tTurno;

import java.io.Serializable;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Victorma
 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
@Entity
public class EmpleadoCompleto extends Empleado implements Serializable {
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private static final long serialVersionUID = 0;
	
	private boolean esFijo;
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public EmpleadoCompleto() {
	}
	
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public EmpleadoCompleto(boolean esFijo) {
		this.esFijo = esFijo;
	}
	
	
	public boolean getEsFijo() {
		return esFijo;
	}

	public void setEsFijo(boolean esFijo) {
		this.esFijo = esFijo;
	}
	
	
	
	/** 
	 * (sin Javadoc)
	 * @see Empleado#calcularSueldo()
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Double calcularSueldo() {
		return (this.getDepartamento() != null)?this.getDepartamento().getSueldo()*1.2:0;
	}
}