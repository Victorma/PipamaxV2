package negocio.controlador.ventas;

import negocio.Retorno;
import negocio.controlador.Command;
import negocio.ventas.SAVentas;
import negocio.ventas.factoria.FactoriaSAVentas;

public class CommandVentaListado implements Command {

	private Retorno retorno;

	public CommandVentaListado() {
		retorno = null;
	}

	@Override
	public Retorno execute() {
		SAVentas SA = FactoriaSAVentas.getInstancia().getInstanciaSAVentas();

		retorno = SA.consultaListaVentas();
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		//--
	}

}
