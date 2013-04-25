/**
 * 
 */
package negocio.productos.factoria;

import negocio.productos.SAProductos;
import negocio.productos.factoria.imp.FactoriaSAProductosImp;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Victorma
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public abstract class FactoriaSAProductos {
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private static FactoriaSAProductos instancia = null;

	/** 
	 * @return el instancia
	 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public static FactoriaSAProductos getInstancia(){
		// begin-user-code
		if(instancia == null)
			instancia = new FactoriaSAProductosImp();
		
		return instancia;
		// end-user-code
	}


	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract SAProductos getInstanciaSAProductos();
}