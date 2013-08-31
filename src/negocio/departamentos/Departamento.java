/**
 * 
 */
package negocio.departamentos;

import javax.persistence.Entity;
import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.Version;

import java.util.Set;
import negocio.empleados.Empleado;
import javax.persistence.OneToMany;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Victorma
 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.departamentos.Departamento.findByid", query = "select obj from Departamento obj where obj.id = :id and obj.activo = 1"),
		@NamedQuery(name = "negocio.departamentos.Departamento.findBynombre", query = "select obj from Departamento obj where obj.nombre = :nombre and obj.activo = 1"),
		@NamedQuery(name = "negocio.departamentos.Departamento.findBycodigo", query = "select obj from Departamento obj where obj.codigo = :codigo and obj.activo = 1"),
		@NamedQuery(name = "negocio.departamentos.Departamento.findByempleado", query = "select obj from Departamento obj where obj.empleado = :empleado and obj.activo = 1"),
		@NamedQuery(name = "negocio.departamentos.Departamento.removeDepartamento", query = "update Departamento set activo = 1 where id = :id"), })
public class Departamento implements Serializable {
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private static final long serialVersionUID = 0;

	@Version
	private Integer version;
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@Id	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String nombre;
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private Integer codigo;
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "departamento")
	private Set<Empleado> empleado;
	private Double sueldo;
	
	private Integer activo;
	
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Departamento() {
	}
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	//devuelve el número de empleados en el departamento
	public Integer getNumEpleados(){
		return this.getEmpleado().size();
	}
	
	@Override
	public String toString() {
		return "("+codigo+") "+nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public Set<Empleado> getEmpleado() {
		return empleado;
	}	
	public void setEmpleado(Set<Empleado> empleado) {
		this.empleado = empleado;
	}
	
	public Double getSueldo() {
		return sueldo;
	}
	public void setSueldo(Double sueldo) {
		this.sueldo = sueldo;
	}



	public Integer getActivo() {
		return activo;
	}



	public void setActivo(Integer activo) {
		this.activo = activo;
	}



}