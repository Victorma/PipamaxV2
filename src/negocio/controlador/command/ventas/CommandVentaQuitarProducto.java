package negocio.controlador.command.ventas;

import negocio.Retorno;
import negocio.controlador.command.Command;
import negocio.productos.TransferProducto;
import negocio.ventas.SAVentas;
import negocio.ventas.TransferVenta;
import negocio.ventas.factoria.FactoriaSAVentas;

public class CommandVentaQuitarProducto implements Command {

	private TransferVenta venta;
	private TransferProducto producto;
	private Retorno retorno;

	public CommandVentaQuitarProducto() {
		venta = null;
		producto = null;
	}

	public CommandVentaQuitarProducto(Object datos) {
		Object[] arrayDatos = (Object[]) datos;
		this.venta = (TransferVenta) arrayDatos[0];
		this.producto = (TransferProducto) arrayDatos[1];
	}

	@Override
	public Retorno execute() {
		SAVentas SA = FactoriaSAVentas.getInstancia().getInstanciaSAVentas();

		retorno = SA.quitarProducto(venta,producto);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		Object[] arrayDatos = (Object[]) datos;
		this.venta = (TransferVenta) arrayDatos[0];
		this.producto = (TransferProducto) arrayDatos[1];
	}

}
