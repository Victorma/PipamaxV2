package negocio.marcas;

import negocio.productos.TransferListaProductos;

public class TComMarcaListaProductos {

	private TransferMarca marca;
	private TransferListaProductos productos;

	public TransferMarca getMarca() {
		return marca;
	}

	public void setMarca(TransferMarca marca) {
		this.marca = marca;
	}

	public TransferListaProductos getProductos() {
		return productos;
	}

	public void setProductos(TransferListaProductos productos) {
		this.productos = productos;
	}

}
