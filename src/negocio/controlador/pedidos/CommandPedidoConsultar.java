package negocio.controlador.pedidos;

import negocio.Retorno;
import negocio.controlador.Command;
import negocio.pedidos.SAPedidos;
import negocio.pedidos.TComPedido;
import negocio.pedidos.factoria.FactoriaSAPedidos;

public class CommandPedidoConsultar implements Command {

	private TComPedido datos;
	private Retorno retorno;

	public CommandPedidoConsultar() {
		datos = null;
	}

	public CommandPedidoConsultar(TComPedido datos) {
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAPedidos SA = FactoriaSAPedidos.getInstancia().getInstanciaSAPedidos();

		retorno = SA.consultarPedido(datos.getPedido());

		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TComPedido) datos;
	}

}
