package negocio.controlador.ventas;

import negocio.Retorno;
import negocio.controlador.Command;
import negocio.ventas.SAVentas;
import negocio.ventas.TransferVenta;
import negocio.ventas.factoria.FactoriaSAVentas;

public class CommandVentaCrear implements Command {

	private TransferVenta datos;
	private Retorno retorno;

	public CommandVentaCrear() {
		datos = null;
	}

	public CommandVentaCrear(TransferVenta datos) {
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAVentas SA = FactoriaSAVentas.getInstancia().getInstanciaSAVentas();

		retorno = SA.creaVenta(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferVenta) datos;
	}

}
