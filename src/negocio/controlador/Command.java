package negocio.controlador;

import negocio.Retorno;

public interface Command {

	Retorno execute();

	void setContext(Object datos);
}
