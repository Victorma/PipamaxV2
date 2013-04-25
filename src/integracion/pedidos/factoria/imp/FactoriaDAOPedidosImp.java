package integracion.pedidos.factoria.imp;

import integracion.pedidos.DAOPedidos;
import integracion.pedidos.factoria.FactoriaDAOPedidos;
import integracion.pedidos.imp.DAOPedidosImp;

public class FactoriaDAOPedidosImp extends FactoriaDAOPedidos {

	@Override
	public DAOPedidos getInstanciaDAOPedidos() {
		return new DAOPedidosImp();
	}

}
