package negocio.pedidos;

import java.util.List;

import negocio.productos.TComProducto;
import negocio.proveedores.TransferProveedor;

public class TComPedido {

	private TransferPedido pedido;
	private TransferProveedor proveedor;
	private List<TComProducto> productos;

	public TransferProveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(TransferProveedor proveedor) {
		this.proveedor = proveedor;
	}

	public TransferPedido getPedido() {
		return pedido;
	}

	public void setPedido(TransferPedido pedido) {
		this.pedido = pedido;
	}

	public List<TComProducto> getProductos() {
		return productos;
	}

	public void setProductos(List<TComProducto> productos) {
		this.productos = productos;
	}

}
