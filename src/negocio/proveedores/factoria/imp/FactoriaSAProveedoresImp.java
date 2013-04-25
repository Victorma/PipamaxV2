package negocio.proveedores.factoria.imp;

import negocio.proveedores.SAProveedores;
import negocio.proveedores.factoria.FactoriaSAProveedores;
import negocio.proveedores.imp.SAProveedoresImp;


public class FactoriaSAProveedoresImp extends FactoriaSAProveedores{
	
	public SAProveedores getInstanciaSAProveedores() {
		return new SAProveedoresImp();
	}
	
}
