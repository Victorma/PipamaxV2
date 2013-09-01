package negocio.controlador.imp;


import presentacion.dispacher.Dispatcher;
import constantes.Acciones;
import negocio.Retorno;
import negocio.controlador.ControladorAplicacion;
import negocio.controlador.command.Command;
import negocio.controlador.command.factoria.FactoriaCommand;

public class ControladorAplicacionImp extends ControladorAplicacion {
	
	@Override
	public Retorno ejecuta(Command c) {
		return c.execute();
	}

	@Override
	public void accion(Acciones evento, Object datos) {
		Retorno retorno = new Retorno();
		Command comando = FactoriaCommand.getInstancia().createCommand(evento.toString());
		comando.setContext(datos);
		retorno = ejecuta(comando);
		Dispatcher.getInstancia().actualiza(evento, retorno);
	}

}
