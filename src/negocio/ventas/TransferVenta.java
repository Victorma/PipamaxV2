/**
 * 
 */
package negocio.ventas;

import java.util.ArrayList;
import java.util.List;

public class TransferVenta {

	private List<TransferLineaVenta> transferLineaVenta = new ArrayList<TransferLineaVenta>();

	int estado;
	private int id;
	private String fecha;
	private int idCliente;
	private float descuento;
	private boolean cerrada;

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getNumLineasVenta() {
		return transferLineaVenta.size();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public boolean removeLineaVenta(int pos) {
		if (pos > transferLineaVenta.size())
			return false;
		else {
			transferLineaVenta.remove(pos);
			return true;
		}
	}

	public TransferLineaVenta getLineaVenta(int pos) {
		return transferLineaVenta.get(pos);
	}

	public void addLineaVenta(int idProducto, int cantidad) {
		transferLineaVenta.add(new TransferLineaVenta(idProducto, cantidad));
	}

	public void addLineaVenta(int idProducto, int cantidad, double precio,
			int idVenta) {
		transferLineaVenta.add(new TransferLineaVenta(idProducto, cantidad,
				precio, idVenta));
	}

	public float getDescuento() {
		return descuento;
	}

	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}
	
	public boolean isCerrada(){
		return this.cerrada;
	}
	
	public void setCerrada(boolean cerrada){
		this.cerrada = cerrada;
	}
}