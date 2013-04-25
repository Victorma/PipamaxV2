package negocio.pedidos.factoria;

import negocio.pedidos.SAPedidos;
import negocio.pedidos.factoria.imp.FactoriaSAPedidosImp;

public abstract class FactoriaSAPedidos {
	private static FactoriaSAPedidos instancia;

	public static FactoriaSAPedidos getInstancia() {
		if (instancia == null)
			instancia = new FactoriaSAPedidosImp();
		return instancia;
	}

	public abstract SAPedidos getInstanciaSAPedidos();
}
