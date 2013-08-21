package presentacion.dispacher.imp;

import constantes.Acciones;
import presentacion.GUI;
import presentacion.PrincipalGUI;
import presentacion.dispacher.Dispatcher;
import negocio.Retorno;

public class DispatcherImp extends Dispatcher {

	private GUI main;

	public GUI getMain() {

		main = creaPrincipalGUI();
		return main;
	}

	public GUI creaPrincipalGUI() {
		main = new PrincipalGUI(null);
		return main;
	}

	public void actualiza(Acciones evento, Retorno datos) {
		main.actualiza(evento, datos);
	}

}
