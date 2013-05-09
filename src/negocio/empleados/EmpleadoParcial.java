/**
 * 
 */
package negocio.empleados;

import javax.persistence.Entity;
import java.io.Serializable;
import constantes.Constantes.tTurno;
/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Victorma
 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
@Entity
public class EmpleadoParcial extends Empleado implements Serializable {
	
	
	private tTurno turno;
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private static final long serialVersionUID = 0;

	
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public EmpleadoParcial() {

	}
	
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public EmpleadoParcial(tTurno turno) {
		this.turno = turno;
	}

	public tTurno getTurno() {
		return turno;
	}

	public void setTurno(tTurno turno) {
		this.turno = turno;
	}

	/** 
	 * (sin Javadoc)
	 * @see Empleado#calcularSueldo()
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Double calcularSueldo() {
		return (this.getDepartamento() != null)?this.getDepartamento().getSueldo()*0.5:0;
	}
}