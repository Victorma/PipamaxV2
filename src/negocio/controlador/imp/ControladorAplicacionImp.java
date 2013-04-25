package negocio.controlador.imp;

import negocio.Retorno;
import negocio.controlador.Command;
import negocio.controlador.ControladorAplicacion;

public class ControladorAplicacionImp extends ControladorAplicacion {

	@Override
	public Retorno ejecuta(Command c) {
		return c.execute();
	}

}
