package negocio.pedidos.factoria.imp;

import negocio.pedidos.SAPedidos;
import negocio.pedidos.factoria.FactoriaSAPedidos;
import negocio.pedidos.imp.SAPedidosImp;

public class FactoriaSAPedidosImp extends FactoriaSAPedidos {

	public SAPedidos getInstanciaSAPedidos() {
		return new SAPedidosImp();
	}
}
