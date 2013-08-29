/**
 * 
 */
package negocio.proyectos;

import javax.persistence.Entity;
import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

import java.util.Set;
import negocio.empleados.Empleado;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Victorma
 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.proyectos.Proyecto.findBynombre", query = "select obj from Proyecto obj where obj.nombre = :nombre"),
		@NamedQuery(name = "negocio.proyectos.Proyecto.findByid", query = "select obj from Proyecto obj where obj.id = :id"),
		@NamedQuery(name = "negocio.proyectos.Proyecto.findByempleado", query = "select obj from Proyecto obj where obj.empleado = :empleado") })
public class Proyecto implements Serializable {
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private static final long serialVersionUID = 0;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Set<Empleado> getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Set<Empleado> empleado) {
		this.empleado = empleado;
	}
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Proyecto() {
	}
	
	@Version
	private Integer version;
	
	private String nombre;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString(){
		return "(" + id + ") "+  nombre;
	}
	private String descripcion;
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToMany(mappedBy = "proyecto")
	private Set<Empleado> empleado;
}