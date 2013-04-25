package negocio.productos;

import java.util.ArrayList;
import java.util.List;

public class TransferListaProductos {
	private List<TransferProducto> productos;

	public TransferListaProductos() {
		productos = new ArrayList<TransferProducto>();
	}

	public void add(TransferProducto _tr) {
		productos.add(_tr);
	}

	public List<TransferProducto> getList() {
		return productos;
	}

	public void setList(List<TransferProducto> productos) {
		this.productos = productos;
	}

}
