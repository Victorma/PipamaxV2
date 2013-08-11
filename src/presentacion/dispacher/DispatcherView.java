package presentacion.dispacher;

import constantes.Acciones;
import presentacion.GUI;
import presentacion.dispacher.imp.DispatcherViewImp;
import negocio.Retorno;

public abstract class DispatcherView {

	protected static DispatcherView instancia = null;

	public static DispatcherView getInstancia() {
		if (instancia == null)
			instancia = new DispatcherViewImp();
		return instancia;
	}

	public abstract GUI getMain();

	public abstract void actualiza(Acciones evento, Retorno datos);

}
