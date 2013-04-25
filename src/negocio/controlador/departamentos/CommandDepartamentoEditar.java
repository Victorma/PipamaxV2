package negocio.controlador.departamentos;

import negocio.Retorno;
import negocio.controlador.Command;
import negocio.departamentos.Departamento;
import negocio.departamentos.factoria.FactoriaSADepartamentos;

public class CommandDepartamentoEditar implements Command {

	private Departamento dep = null;

	@Override
	public Retorno execute() {
		return FactoriaSADepartamentos.getInstancia()
				.getInstanciaSADepartamentos().editarDepartamento(dep);
	}

	@Override
	public void setContext(Object datos) {
		dep = (Departamento) datos;
	}

}
