package negocio.proveedores;

import negocio.productos.TransferListaProductos;

public class TComProveedorListaProductos {

	private TransferProveedor proveedor;
	private TransferListaProductos productos;

	public TransferProveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(TransferProveedor proveedor) {
		this.proveedor = proveedor;
	}

	public TransferListaProductos getProductos() {
		return productos;
	}

	public void setProductos(TransferListaProductos productos) {
		this.productos = productos;
	}

}
