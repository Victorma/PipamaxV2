/**
 * 
 */
package negocio.proyectos;

import negocio.Retorno;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Victorma
 * @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
public interface SAProyectos {
	/** 
	 * Crea un proyecto comprobando que los empleados añadidos son correctos. Se asigna una id nueva, y se muestra
	 */
	public Retorno altaProyecto(Proyecto pry);

	/** 
	 * Da de baja un proyecto desviculando los empleados que trabajaban en éste. Requiere la id
	 */
	public Retorno bajaProyecto(Proyecto pry);

	/** 
	 * Muestra los datos del proyecto. Requiere la id
	 */
	public Retorno consultaProyecto(Proyecto pry);

	/** 
	 * Modifica nombre, empleados asociados o descripción. Requiere la id
	 */
	public Retorno editaProyecto(Proyecto pry);
	
	/** 
	 * Muestra todos los proyectos
	 */
	public Retorno listaProyectos();
}