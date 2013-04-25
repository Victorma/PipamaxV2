/**
 * 
 */
package negocio.ventas;

public class TransferLineaVenta {

	private int idVenta;
	private int idProducto;
	private int cantidad;
	private double precio;

	public TransferLineaVenta(int idProducto, int cantidad) {
		this.idProducto = idProducto;
		this.cantidad = cantidad;
		this.setIdVenta(-1);
	}

	public TransferLineaVenta(int idProducto, int cantidad, double precio,
			int idVenta) {
		this.idProducto = idProducto;
		this.cantidad = cantidad;
		this.precio = precio;
		this.setIdVenta(idVenta);
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

	public int getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}
}