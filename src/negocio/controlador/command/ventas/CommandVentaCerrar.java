package negocio.controlador.command.ventas;

import negocio.Retorno;
import negocio.controlador.command.Command;
import negocio.ventas.SAVentas;
import negocio.ventas.TransferVenta;
import negocio.ventas.factoria.FactoriaSAVentas;

public class CommandVentaCerrar implements Command {
	
	private TransferVenta datos;
	private Retorno retorno;

	public CommandVentaCerrar() {
		datos = null;
	}

	public CommandVentaCerrar(TransferVenta datos) {
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAVentas SA = FactoriaSAVentas.getInstancia().getInstanciaSAVentas();

		retorno = SA.cerrarVenta(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferVenta) datos;
	}

}
