package integracion.proveedores.factoria;

import integracion.proveedores.DAOProveedores;
import integracion.proveedores.factoria.imp.FactoriaDAOProveedoresImp;

public abstract class FactoriaDAOProveedores{
	static FactoriaDAOProveedores instancia;
	
	public static FactoriaDAOProveedores getInstancia() {
		if (instancia == null)
			instancia = new FactoriaDAOProveedoresImp();
		return instancia;
	}
	
	
	public abstract DAOProveedores getInstanciaDAOProveedores();
}



