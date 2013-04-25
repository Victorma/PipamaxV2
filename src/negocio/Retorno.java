package negocio;

public class Retorno {

	private ListaTError errores;
	private Object datos;

	public Retorno() {
		errores = new ListaTError();
	}

	public ListaTError getErrores() {
		return errores;
	}

	public void addError(int codigo, Object datos) {
		errores.addError(codigo, datos);
	}

	public void setErrores(ListaTError errores) {
		this.errores = errores;
	}

	public boolean tieneErrores() {
		boolean retorno = false;
		if (errores == null)
			retorno = false;
		else
			retorno = errores.getLista().size() != 0;

		return retorno;
	}

	public Object getDatos() {
		return datos;
	}

	public void setDatos(Object datos) {
		this.datos = datos;
	}

}
