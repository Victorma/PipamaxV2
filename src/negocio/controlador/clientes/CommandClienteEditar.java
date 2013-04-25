package negocio.controlador.clientes;

import negocio.Retorno;
import negocio.clientes.SAClientes;
import negocio.clientes.TransferCliente;
import negocio.clientes.factoria.FactoriaSAClientes;
import negocio.controlador.Command;

public class CommandClienteEditar implements Command {

	private TransferCliente datos;
	private Retorno retorno;

	public CommandClienteEditar() {
		datos = null;
	}

	public CommandClienteEditar(TransferCliente datos) {
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAClientes SA = FactoriaSAClientes.getInstancia()
				.getInstanciaSAClientes();

		retorno = SA.editarCliente(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferCliente) datos;
	}

}
