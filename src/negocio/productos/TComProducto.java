package negocio.productos;

import negocio.marcas.TransferMarca;

public class TComProducto {

	private TransferProducto producto;
	private TransferMarca marca;

	public TransferProducto getProducto() {
		return producto;
	}

	public void setProducto(TransferProducto producto) {
		this.producto = producto;
	}

	public TransferMarca getMarca() {
		return marca;
	}

	public void setMarca(TransferMarca marca) {
		this.marca = marca;
	}

}
