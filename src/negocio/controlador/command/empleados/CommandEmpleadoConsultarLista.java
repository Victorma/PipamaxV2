package negocio.controlador.command.empleados;

import negocio.Retorno;
import negocio.controlador.command.Command;
import negocio.empleados.factoria.FactoriaSAEmpleados;

public class CommandEmpleadoConsultarLista implements Command {

	@Override
	public Retorno execute() {
		return FactoriaSAEmpleados.getInstancia().getInstanciaSAEmpleados()
				.consultaListaEmpleados();
	}

	@Override
	public void setContext(Object datos) {
	}

}
