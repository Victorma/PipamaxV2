package negocio.ventas;

import java.util.ArrayList;
import java.util.List;

import negocio.clientes.TransferCliente;
import negocio.productos.TComProducto;

public class TComVenta {

	private TransferVenta venta;
	private TransferCliente cliente;
	private List<TComProducto> productos = new ArrayList<TComProducto>();

	public TransferCliente getCliente() {
		return cliente;
	}

	public void setCliente(TransferCliente cliente) {
		this.cliente = cliente;
	}

	public TransferVenta getVenta() {
		return venta;
	}

	public void setVenta(TransferVenta venta) {
		this.venta = venta;
	}

	public List<TComProducto> getProductos() {
		return productos;
	}

	public void setProductos(List<TComProducto> productos) {
		this.productos = productos;
	}

}
