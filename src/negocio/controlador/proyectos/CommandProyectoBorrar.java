package negocio.controlador.proyectos;

import negocio.Retorno;
import negocio.controlador.Command;
import negocio.proyectos.Proyecto;
import negocio.proyectos.factoria.FactoriaSAProyectos;

public class CommandProyectoBorrar implements Command {

	private Proyecto pry = null;
	
	@Override
	public Retorno execute() {
		return FactoriaSAProyectos.getInstancia().getInstanciaSAProyectos().bajaProyecto(pry);
	}

	@Override
	public void setContext(Object datos) {
		pry = (Proyecto)datos;
	}

}
