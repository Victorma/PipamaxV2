package negocio.ventas;

import java.util.ArrayList;
import java.util.List;

public class TransferListaVentas {

	private List<TransferVenta> ventas = new ArrayList<TransferVenta>();

	public List<TransferVenta> getListaVentas() {
		return ventas;
	}

	public void setListaVentas(List<TransferVenta> ventas) {
		this.ventas = ventas;
	}

}
