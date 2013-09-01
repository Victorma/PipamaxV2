package negocio.controlador.command.clientes;

import negocio.Retorno;
import negocio.clientes.SAClientes;
import negocio.clientes.TransferCliente;
import negocio.clientes.factoria.FactoriaSAClientes;
import negocio.controlador.command.Command;

public class CommandClienteCrear implements Command {

	private TransferCliente datos;
	private Retorno retorno;

	public CommandClienteCrear() {
		datos = null;
		retorno = null;
	}

	public CommandClienteCrear(TransferCliente datos) {
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAClientes SA = FactoriaSAClientes.getInstancia()
				.getInstanciaSAClientes();

		retorno = SA.crearCliente(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferCliente) datos;
	}

}
