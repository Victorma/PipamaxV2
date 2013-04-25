package negocio;

import java.util.ArrayList;
import java.util.List;

public class ListaTError {

	private List<TError> lista = new ArrayList<TError>();

	public List<TError> getLista() {
		return lista;
	}

	public void setLista(List<TError> lista) {
		this.lista = lista;
	}

	public void addError(int codigo, Object datos) {
		lista.add(new TError(codigo, datos));
	}

}
