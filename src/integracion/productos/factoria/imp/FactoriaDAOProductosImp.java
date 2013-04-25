/**
 * 
 */
package integracion.productos.factoria.imp;

import integracion.productos.DAOProductos;
import integracion.productos.factoria.FactoriaDAOProductos;
import integracion.productos.imp.DAOProductosImp;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Victorma
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class FactoriaDAOProductosImp extends FactoriaDAOProductos {

	@Override
	public DAOProductos getInstanciaDAOProductos() {
		// TODO Auto-generated method stub
		return new DAOProductosImp();
	}
}