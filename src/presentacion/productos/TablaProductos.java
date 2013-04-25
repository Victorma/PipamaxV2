package presentacion.productos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import negocio.productos.TransferListaProductos;
import negocio.productos.TransferProducto;

public class TablaProductos {

	private JTable table;
	private JScrollPane pane;
	private ModeloProductos model;

	private class ModeloProductos extends AbstractTableModel {
		private static final long serialVersionUID = 5051406499985006913L;
		private List<TransferProducto> productos = new ArrayList<TransferProducto>();
		private final String[] colNames = new String[] { "Id", "Nombre",
				"Id Marca", "Stock", "Precio Venta" };

		public void update(List<TransferProducto> productos) {
			this.productos = productos;
			this.fireTableDataChanged();
		}

		@SuppressWarnings("unused")
		public TransferProducto getVenta(int pos) {
			return productos.get(pos);
		}

		public int getId(int _row) {
			if (_row != -1)
				return productos.get(_row).getId();
			else
				return -1;
		}

		public int getColumnCount() {
			return colNames.length;
		}

		public int getRowCount() {
			return productos.size();
		}

		@Override
		public String getColumnName(int col) {
			return colNames[col];
		}

		public Object getValueAt(int row, int col) {

			String out = "";

			switch (col) {
			case 0:
				out = "" + productos.get(row).getId();
				break;
			case 1:
				out = productos.get(row).getNombre();
				break;
			case 2:
				out = "" + productos.get(row).getIdMarca();
				break;
			case 3:
				out = "" + productos.get(row).getStock();
				break;
			case 4:
				out = "" + productos.get(row).getPrecio();
				break;
			}

			return out;
		}
	}

	public TablaProductos() {

		model = new ModeloProductos();

		//create the table with the model "model"
		table = new JTable(model);
		table.setEnabled(true);
		table.setBackground(Color.white);
		//ad the table into a scrollPane
		pane = new JScrollPane(table);
	}

	public JScrollPane getPane() {
		return pane;
	}

	public int getSelectedId() {
		int retorno = -1;
		if (table.getSelectedRow() != -1)
			retorno = model.getId(table.getSelectedRow());
		return retorno;
	}

	public int getLastId() {
		return model.getRowCount();
	}

	public void updateTable(TransferListaProductos tr) {
		List<TransferProducto> newproductos = new ArrayList<TransferProducto>();
		List<TransferProducto> oldproductos = tr.getList();
		TransferProducto temp;
		int i = 0;
		while (i < oldproductos.size()) {
			temp = oldproductos.get(i);
			if (temp.getBorrado() == false) {
				newproductos.add(temp);
			}
			i++;
		}

		model.update(newproductos);
	}

}
