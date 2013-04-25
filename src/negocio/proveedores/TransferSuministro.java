package negocio.proveedores;

public class TransferSuministro {

	private int id;
	private int idProd;
	private int idProve;
	private double precio;

	public TransferSuministro() {
		id = 0;
		idProd = 0;
		idProve = 0;
		precio = 0;
	}

	public void setIdProducto(int _idProd) {
		idProd = _idProd;
	}

	public void setIdProveedor(int _idProve) {
		idProve = _idProve;
	}

	public void setPrecio(double _precio) {
		precio = _precio;
	}

	public int getIdProducto() {
		return idProd;
	}

	public int getIdProveedor() {
		return idProve;
	}

	public double getPrecio() {
		return precio;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
