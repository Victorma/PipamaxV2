/**
 * 
 */
package negocio.productos.factoria.imp;

import negocio.productos.SAProductos;
import negocio.productos.factoria.FactoriaSAProductos;
import negocio.productos.imp.SAProductosImp;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Victorma
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class FactoriaSAProductosImp extends FactoriaSAProductos {

	@Override
	public SAProductos getInstanciaSAProductos() {
		// TODO Auto-generated method stub
		return new SAProductosImp();
	}
}