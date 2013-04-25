package negocio.proveedores.factoria;

import negocio.proveedores.SAProveedores;
import negocio.proveedores.factoria.imp.FactoriaSAProveedoresImp;

public abstract class FactoriaSAProveedores {
	private static FactoriaSAProveedores instancia;
	
	public static FactoriaSAProveedores getInstancia() {
		if (instancia == null)
			instancia = new FactoriaSAProveedoresImp();
		return instancia;
	}
	
	public abstract SAProveedores getInstanciaSAProveedores();
	
}
