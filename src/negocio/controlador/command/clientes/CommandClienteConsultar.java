package negocio.controlador.command.clientes;

import negocio.Retorno;
import negocio.clientes.SAClientes;
import negocio.clientes.TransferCliente;
import negocio.clientes.factoria.FactoriaSAClientes;
import negocio.controlador.command.Command;

public class CommandClienteConsultar implements Command {

	private TransferCliente datos;
	private Retorno retorno;

	public CommandClienteConsultar() {
		datos = null;
	}

	public CommandClienteConsultar(TransferCliente datos) {
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAClientes SA = FactoriaSAClientes.getInstancia()
				.getInstanciaSAClientes();

		retorno = SA.consultarCliente(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferCliente) datos;
	}

}
