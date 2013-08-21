package presentacion.dispacher;

import constantes.Acciones;
import presentacion.GUI;
import presentacion.dispacher.imp.DispatcherImp;
import negocio.Retorno;

public abstract class Dispatcher {

	protected static Dispatcher instancia = null;

	public static Dispatcher getInstancia() {
		if (instancia == null)
			instancia = new DispatcherImp();
		return instancia;
	}

	public abstract GUI getMain();

	public abstract void actualiza(Acciones evento, Retorno datos);

}
