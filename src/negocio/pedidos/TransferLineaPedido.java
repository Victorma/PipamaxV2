package negocio.pedidos;

public class TransferLineaPedido {

	private int idProducto;
	private int cantidad;
	private double precio;
	private int idPedido;

	public TransferLineaPedido(int idProducto, int cantidad) {
		this.idProducto = idProducto;
		this.cantidad = cantidad;
		this.idPedido = -1;
	}

	public TransferLineaPedido(int idProducto, int cantidad, double precio,
			int idPedido) {
		this.idProducto = idProducto;
		this.cantidad = cantidad;
		this.precio = precio;
		this.idPedido = idPedido;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

}
