package negocio.controlador.command.pedidos;

import negocio.Retorno;
import negocio.controlador.command.Command;
import negocio.pedidos.SAPedidos;
import negocio.pedidos.TransferPedido;
import negocio.pedidos.factoria.FactoriaSAPedidos;

public class CommandPedidoCompletar implements Command {

	private TransferPedido datos;
	private Retorno retorno;

	public CommandPedidoCompletar() {
		datos = null;
	}

	public CommandPedidoCompletar(TransferPedido datos) {
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAPedidos SA = FactoriaSAPedidos.getInstancia().getInstanciaSAPedidos();

		retorno = SA.completarPedido(datos);

		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferPedido) datos;
	}

}
