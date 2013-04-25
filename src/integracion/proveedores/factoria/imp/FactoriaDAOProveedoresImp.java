package integracion.proveedores.factoria.imp;

import integracion.proveedores.DAOProveedores;
import integracion.proveedores.factoria.FactoriaDAOProveedores;
import integracion.proveedores.imp.DAOProveedoresImp;

public class FactoriaDAOProveedoresImp extends FactoriaDAOProveedores {

	@Override
	public DAOProveedores getInstanciaDAOProveedores() {
		return new DAOProveedoresImp();
	}
}
