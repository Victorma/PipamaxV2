package negocio.controlador.command.factoria;

import negocio.controlador.command.Command;
import negocio.controlador.command.factoria.imp.FactoriaCommandImp;

public abstract class FactoriaCommand {
	
	private static FactoriaCommand instancia;

	public static FactoriaCommand getInstancia() {
		if (instancia == null)
			instancia = new FactoriaCommandImp();

		return instancia;
	}

	public abstract Command createCommand(String evento);

}
