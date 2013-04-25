package presentacion.dispacher;

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

	public abstract void actualiza(Integer evento, Retorno datos);

}
