package negocio.clientes;

import java.util.ArrayList;

public class TransferListaClientes {
	ArrayList<TransferCliente> lista = new ArrayList<TransferCliente>();

	public ArrayList<TransferCliente> getLista() {
		return lista;
	}

	public void setLista(ArrayList<TransferCliente> lista) {
		this.lista = lista;
	}
}
