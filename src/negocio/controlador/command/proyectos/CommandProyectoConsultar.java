package negocio.controlador.command.proyectos;

import negocio.Retorno;
import negocio.controlador.command.Command;
import negocio.proyectos.Proyecto;
import negocio.proyectos.factoria.FactoriaSAProyectos;

public class CommandProyectoConsultar implements Command {

	private Proyecto pry = null;
	
	@Override
	public Retorno execute() {
		return FactoriaSAProyectos.getInstancia().getInstanciaSAProyectos().consultaProyecto(pry);
	}

	@Override
	public void setContext(Object datos) {
		pry = (Proyecto)datos;
	}

}
