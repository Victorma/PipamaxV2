/**
 * 
 */
package negocio.departamentos;

import negocio.Retorno;

/** 
 * @author Victorma
 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
public interface SADepartamentos {
	/** 
	 * Recibe los datos del departamento a crear. Si cumple con los requisitos y no da ningún error
	 * lo crea devolviendo la id del nuevo departamento. Los errores se almacenan en el retorno.
	 * @param Departamento dep con los datos del departamento a crear.
	 * @return Retorno con los errores (si los hay) y los datos (la id del nuevo departamento)
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno crearDepartamento(Departamento dep);

	/** 
	 * Recibe la id del departamento a borrar, y, si no tiene ningun empleado asignado, lo borra.
	 * Los errores se almacenan en el retorno.
	 * @param Departamento dep con la id del departamento a borrar.
	 * @return Retorno con los errores (si los hay) y los datos
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno bajaDepartamento(Departamento dep);

	/** 
	 * Recibe la id del departamento a consultar, y si está en la base de datos lo devuelve por
	 * retorno. Si se produce algún error lo almacena en el retorno.
	 * @param Departamento dep con la id del departamento a consultar.
	 * @return Retorno con los errores (si los hay) y los datos (el departamento consultado)
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno consultaDepartamento(Departamento dep);

	/** 
	 * Recibe los datos del departamento a modificar, y si cumple con los requisitos se modifica.
	 * Si se produce algún error, lo almacena en retorno.
	 * @param Departamento dep con los datos del departamento modificado.
	 * @return Retorno con los errores (si los hay) y los datos.
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno editarDepartamento(Departamento dep);
	
	/** 
	 * Consulta el la base de datos todos los departamentos que hay y los almacena en el retorno.
	 * Si se produce algún error lo almacena en el retorno.
	 * @return Retorno con los errores (si los hay) y los datos (la lista de departamentos).
	 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno consultarListaDepartamentos();
}