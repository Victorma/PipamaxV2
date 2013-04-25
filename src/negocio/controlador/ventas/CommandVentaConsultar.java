package negocio.controlador.ventas;

import negocio.Retorno;
import negocio.controlador.Command;
import negocio.ventas.SAVentas;
import negocio.ventas.TransferVenta;
import negocio.ventas.factoria.FactoriaSAVentas;

public class CommandVentaConsultar implements Command {

	private TransferVenta datos;
	private Retorno retorno;

	public CommandVentaConsultar() {
		datos = null;
	}

	public CommandVentaConsultar(TransferVenta datos) {
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAVentas SA = FactoriaSAVentas.getInstancia().getInstanciaSAVentas();

		retorno = SA.consultaVenta(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferVenta) datos;
	}

}
