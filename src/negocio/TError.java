package negocio;

public class TError {

	int errorId;
	Object datos;

	public TError(int errorId, Object datos) {
		this.errorId = errorId;
		this.datos = datos;
	}

	public int getErrorId() {
		return errorId;
	}

	public void setErrorId(int errorId) {
		this.errorId = errorId;
	}

	public Object getDatos() {
		return datos;
	}

	public void setDatos(Object datos) {
		this.datos = datos;
	}
}
