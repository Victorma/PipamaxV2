package negocio.controlador.command.ventas;

import negocio.Retorno;
import negocio.controlador.command.Command;
import negocio.productos.TransferProducto;
import negocio.ventas.SAVentas;
import negocio.ventas.TransferVenta;
import negocio.ventas.factoria.FactoriaSAVentas;

public class CommandVentaDevolucion implements Command {

	private TransferVenta venta;
	private TransferProducto producto;
	private Integer cantidad;
	private Retorno retorno;

	public CommandVentaDevolucion() {
		venta = null;
		producto = null;
		cantidad = null;
	}

	public CommandVentaDevolucion(Object datos) {
		Object[] arrayDatos = (Object[]) datos;
		this.venta = (TransferVenta) arrayDatos[0];
		this.producto = (TransferProducto) arrayDatos[1];
		this.cantidad = (Integer) arrayDatos[2];
	}

	@Override
	public Retorno execute() {
		SAVentas SA = FactoriaSAVentas.getInstancia().getInstanciaSAVentas();

		retorno = SA.devolucion(venta,producto,cantidad);
		return retorno;
	}

	@Override
	public void setContext(Object datos) {
		Object[] arrayDatos = (Object[]) datos;
		this.venta = (TransferVenta) arrayDatos[0];
		this.producto = (TransferProducto) arrayDatos[1];
		this.cantidad = (Integer) arrayDatos[2];
	}

}
