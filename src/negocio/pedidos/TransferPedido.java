package negocio.pedidos;

import java.util.ArrayList;
import java.util.List;

public class TransferPedido {

	private int id;
	private List<TransferLineaPedido> lineasPedido = new ArrayList<TransferLineaPedido>();
	private char estado;
	private int idProveedor;
	private String fecha;

	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getId() {
		return id;
	}

	public void setId(int i) {
		id = i;
	}

	public char getEstado() {
		return estado;
	}

	public void setEstado(char es) {
		estado = es;
	}

	public int getNumLineasPedido() {
		return lineasPedido.size();
	}

	public boolean removeLineaPedido(int pos) {
		if (pos > lineasPedido.size())
			return false;
		else {
			lineasPedido.remove(pos);
			return true;
		}
	}

	public TransferLineaPedido getLineaPedido(int pos) {
		return lineasPedido.get(pos);
	}

	public void addLineaPedido(int idProducto, int cantidad) {
		lineasPedido.add(new TransferLineaPedido(idProducto, cantidad));
	}

	public void addLineaPedido(int idProducto, int cantidad, double precio,
			int idVenta) {
		lineasPedido.add(new TransferLineaPedido(idProducto, cantidad, precio,
				idVenta));
	}

}