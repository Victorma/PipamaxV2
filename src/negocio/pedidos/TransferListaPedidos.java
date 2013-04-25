package negocio.pedidos;

import java.util.ArrayList;

public class TransferListaPedidos {
	ArrayList<TransferPedido> lista = new ArrayList<TransferPedido>();

	public ArrayList<TransferPedido> getLista() {
		return lista;
	}

	public void setLista(ArrayList<TransferPedido> lista) {
		this.lista = lista;
	}
}
