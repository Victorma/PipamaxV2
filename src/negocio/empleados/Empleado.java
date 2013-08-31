/**
 * 
 */
package negocio.empleados;

import javax.persistence.Entity;
import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.Version;

import negocio.departamentos.Departamento;
import javax.persistence.ManyToOne;
import java.util.Set;
import negocio.proyectos.Proyecto;
import javax.persistence.ManyToMany;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Victorma
 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.empleados.Empleado.findByversion", query = "select obj from Empleado obj where obj.version = :version and obj.activo = 1"),
		@NamedQuery(name = "negocio.empleados.Empleado.findByid", query = "select obj from Empleado obj where obj.id = :id and obj.activo = 1"),
		@NamedQuery(name = "negocio.empleados.Empleado.findBynombre", query = "select obj from Empleado obj where obj.nombre = :nombre and obj.activo = 1"),
		@NamedQuery(name = "negocio.empleados.Empleado.findByapellido1", query = "select obj from Empleado obj where obj.apellido1 = :apellido1 and obj.activo = 1"),
		@NamedQuery(name = "negocio.empleados.Empleado.findByapellido2", query = "select obj from Empleado obj where obj.apellido2 = :apellido2 and obj.activo = 1"),
		@NamedQuery(name = "negocio.empleados.Empleado.findBydni", query = "select obj from Empleado obj where obj.dni = :dni and obj.activo = 1"),
		@NamedQuery(name = "negocio.empleados.Empleado.findBytelefono", query = "select obj from Empleado obj where obj.telefono = :telefono and obj.activo = 1"),
		@NamedQuery(name = "negocio.empleados.Empleado.findBydireccion", query = "select obj from Empleado obj where obj.direccion = :direccion and obj.activo = 1"),
		@NamedQuery(name = "negocio.empleados.Empleado.findByciudad", query = "select obj from Empleado obj where obj.ciudad = :ciudad and obj.activo = 1"),
		@NamedQuery(name = "negocio.empleados.Empleado.findBycp", query = "select obj from Empleado obj where obj.cp = :cp and obj.activo = 1"),
		@NamedQuery(name = "negocio.empleados.Empleado.findBydepartamento", query = "select obj from Empleado obj where obj.departamento = :departamento and obj.activo = 1"),
		@NamedQuery(name = "negocio.empleados.Empleado.findByproyecto", query = "select obj from Empleado obj where obj.proyecto = :proyecto and obj.activo = 1"),
		@NamedQuery(name = "negocio.empleados.Empleado.removeEmpleado", query = "update Empleado set activo = 0 where id = :id") })
public abstract class Empleado implements Serializable {
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private static final long serialVersionUID = 0;

	@Version
	private int version;

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Empleado() {
		// begin-user-code
		// TODO Apéndice de constructor generado automáticamente
		// end-user-code
	}

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
	private String nombre;
	private Integer activo;
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String apellido1;
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String apellido2;
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String dni;
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private Integer telefono;
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String direccion;
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String ciudad;
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private Integer cp;
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */

	@ManyToOne
	private Departamento departamento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public Integer getCp() {
		return cp;
	}

	public void setCp(Integer cp) {
		this.cp = cp;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Set<Proyecto> getProyecto() {
		return proyecto;
	}

	public void setProyecto(Set<Proyecto> proyecto) {
		this.proyecto = proyecto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNombreCompleto() {
		return nombre + " " + apellido1 + " " + apellido2;
	}

	@Override
	public String toString() {
		return dni + " - " + nombre + " " + apellido1 + " " + apellido2
				+ " (ID: " + id + ")";
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public abstract Double calcularSueldo();

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToMany
	private Set<Proyecto> proyecto;
}