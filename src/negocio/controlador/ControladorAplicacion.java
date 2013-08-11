package negocio.controlador;

import constantes.Acciones;
import negocio.Retorno;
import negocio.controlador.imp.ControladorAplicacionImp;

public abstract class ControladorAplicacion {

	private static ControladorAplicacion instancia;

	public static ControladorAplicacion getInstancia() {
		if (instancia == null)
			instancia = new ControladorAplicacionImp();

		return instancia;
	}

	public abstract void accion(Acciones evento, Object datos);
	protected abstract Retorno ejecuta(Command c);
}