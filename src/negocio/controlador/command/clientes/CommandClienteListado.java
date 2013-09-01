package negocio.controlador.command.clientes;

import negocio.Retorno;
import negocio.clientes.SAClientes;
import negocio.clientes.factoria.FactoriaSAClientes;
import negocio.controlador.command.Command;

public class CommandClienteListado implements Command {

	private Retorno retorno;

	@Override
	public Retorno execute() {
		SAClientes SA = FactoriaSAClientes.getInstancia().getInstanciaSAClientes();
		retorno = SA.principalClientes();
		return retorno;
	}

	@Override
	public void setContext(Object datos) {}

}
