package negocio.productos;

public class TransferProducto {
	private int id;
	private String nombre;
	private int idMarca;
	private int stock;
	private double precio;
	boolean borrado;

	public TransferProducto() {
		id = 0;
		nombre = "";
		idMarca = 0;
		stock = 0;
		precio = 0;
		borrado = false;
	}

	public void setId(int _id) {
		id = _id;
	}

	public void setNombre(String _nombre) {
		nombre = _nombre;
	}

	public void setIdMarca(int _idMarca) {
		idMarca = _idMarca;
	}

	public void setStock(int _stock) {
		stock = _stock;
	}

	public void setPrecio(double _precio) {
		precio = _precio;
	}

	public void setBorrado(boolean _borrado) {
		borrado = _borrado;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public int getIdMarca() {
		return idMarca;
	}

	public int getStock() {
		return stock;
	}

	public double getPrecio() {
		return precio;
	}

	public boolean getBorrado() {
		return borrado;
	}
	
	public String toString(){
		return "("+id+") " + nombre;
	}

}
