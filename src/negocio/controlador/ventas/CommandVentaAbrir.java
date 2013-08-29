package negocio.controlador.ventas;

import negocio.Retorno;
import negocio.clientes.TransferCliente;
import negocio.controlador.Command;
import negocio.ventas.SAVentas;
import negocio.ventas.factoria.FactoriaSAVentas;

public class CommandVentaAbrir implements Command {

	private TransferCliente datos;
	private Retorno retorno;

	public CommandVentaAbrir() {
		datos = null;
	}

	public CommandVentaAbrir(TransferCliente datos) {
		this.datos = datos;
	}

	@Override
	public Retorno execute() {
		SAVentas SA = FactoriaSAVentas.getInstancia().getInstanciaSAVentas();

		retorno = SA.abrirVenta(datos);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		this.datos = (TransferCliente) datos;
	}

}
