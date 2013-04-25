package integracion.pedidos.factoria;

import integracion.pedidos.DAOPedidos;
import integracion.pedidos.factoria.imp.FactoriaDAOPedidosImp;

public abstract class FactoriaDAOPedidos {
	static FactoriaDAOPedidos instancia;

	public static FactoriaDAOPedidos getInstancia() {
		if (instancia == null)
			instancia = new FactoriaDAOPedidosImp();
		return instancia;
	}

	public abstract DAOPedidos getInstanciaDAOPedidos();
}
