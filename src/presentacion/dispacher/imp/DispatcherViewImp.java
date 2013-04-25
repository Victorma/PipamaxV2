package presentacion.dispacher.imp;

import presentacion.GUI;
import presentacion.PrincipalGUI;
import presentacion.dispacher.DispatcherView;
import negocio.Retorno;

public class DispatcherViewImp extends DispatcherView {

	private GUI main;

	public GUI getMain() {

		main = creaPrincipalGUI();
		return main;
	}

	public GUI creaPrincipalGUI() {
		main = new PrincipalGUI(null);
		return main;
	}

	public void actualiza(Integer evento, Retorno datos) {
		main.actualiza(evento, datos);
	}

}
