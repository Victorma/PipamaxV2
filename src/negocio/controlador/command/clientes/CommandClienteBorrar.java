package negocio.controlador.command.clientes;

import negocio.Retorno;
import negocio.clientes.SAClientes;
import negocio.clientes.TransferCliente;
import negocio.clientes.factoria.FactoriaSAClientes;
import negocio.controlador.command.Command;

public class CommandClienteBorrar implements Command {

	private TransferCliente datos;
	private Retorno retorno;

	public CommandClienteBorrar() {
		datos = null;
	}

	public CommandClienteBorrar(TransferCliente datos) {
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAClientes SA = FactoriaSAClientes.getInstancia()
				.getInstanciaSAClientes();

		retorno = SA.borrarCliente(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferCliente) datos;
	}

}
