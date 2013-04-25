package negocio.controlador.pedidos;

import negocio.Retorno;
import negocio.controlador.Command;
import negocio.pedidos.SAPedidos;
import negocio.pedidos.TransferPedido;
import negocio.pedidos.factoria.FactoriaSAPedidos;

public class CommandPedidoCrear implements Command {

	private TransferPedido datos;
	private Retorno retorno;

	public CommandPedidoCrear() {
		datos = null;
	}

	public CommandPedidoCrear(TransferPedido datos) {
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAPedidos SA = FactoriaSAPedidos.getInstancia().getInstanciaSAPedidos();

		retorno = SA.crearPedido(datos);
		
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferPedido) datos;
	}

}
