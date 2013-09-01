package negocio.controlador.command;

import negocio.Retorno;

public interface Command {

	Retorno execute();

	void setContext(Object datos);
}
