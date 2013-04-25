/**
 * 
 */
package negocio.empleados;

import negocio.Retorno;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Victorma
 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
public interface SAEmpleados {
	/** 
	 * Alta Empleado da de alta un empleado comprobando que el DNI no exista en la base de datos.
	 * Si se produce algun error lo devuelve dentro de Retorno, si no se produce ningun error,
	 * devuelve la ID del nuevo empleado
	 * @param Empleado emp con los datos del nuevo empleado
	 * @return Retorno con los errores (si los hay) y los datos (la id del nuevo empleado)
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno altaEmpleado(Empleado emp);

	/** 
	 * Busca en la base de datos que exista el empleado a borrar, y si está y no tiene ningún
	 * proyecto asignado, lo borra. Si se produce algun error lo devuelve dentro del retorno.
	 * @param Empleado emp con la id del empleado a borrar
	 * @return Retorno con los errores (si los hay) y los datos (nada)
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno bajaEmpleado(Empleado emp);

	/** 
	 * Busca en la base de datos que exista el empleado a consultar, y si está recopila los datos
	 * y los devuelve por retorno. Si se produce algun error los devuelve por retorno.
	 * @param Empleado emp con la id del empleado a consultar
	 * @return Retorno con los errores (si los hay) y los datos (el empleado consultado)
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno consultaEmpleado(Empleado emp);

	/** 
	 * Busca en la base de datos que exista el empleado a editar, y si está intenta editarlo.
	 * Si se produce algun error los devuelve por retorno.
	 * @param Empleado emp con los datos del empleado modificados
	 * @return Retorno con los errores (si los hay) y los datos (la id del empleado)
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno editaEmpleado(Empleado emp);
	
	/** 
	 * Recorre la base de datos guardando en un set todos los empleados que hay en la base de
	 * datos. Si se produce algún error lo devuelve por retorno.
	 * @return Retorno con los errores (si los hay) y los datos (el set de empleados)
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno consultaListaEmpleados();

	/** 
	 * Busca en la base de datos el empleado, si este tiene departamento, calcula el sueldo en funcion
	 * de el sueldo que se pague en dicho departamento y el tipo de jornada que tiene el empleado.
	 * Si se produce algún error lo almacena en el retorno.
	 * @return Retorno con los errores (si los hay) y los datos (el sueldo del empleado)
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno calculaSueldoEmpleado(Empleado emp);
}