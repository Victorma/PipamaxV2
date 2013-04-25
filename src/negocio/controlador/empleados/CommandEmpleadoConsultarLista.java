package negocio.controlador.empleados;

import negocio.Retorno;
import negocio.controlador.Command;
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
