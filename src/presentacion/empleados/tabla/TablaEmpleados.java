package presentacion.empleados.tabla;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import negocio.empleados.Empleado;

public class TablaEmpleados extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<Empleado> empleados;
	private final String[] colNames = new String[] { "Id", "Nombre", "DNI" };

	public TablaEmpleados() {
		empleados = new ArrayList<Empleado>();
	}

	public void update(Collection<Empleado> empleados) {
		this.empleados.clear();
		this.empleados.addAll(empleados);
		this.fireTableDataChanged();
	}

	public Empleado getEmpleado(int pos) {
		return empleados.get(pos);
	}

	public int getColumnCount() {
		return colNames.length;
	}

	public int getRowCount() {
		return empleados.size();
	}

	@Override
	public String getColumnName(int col) {
		return colNames[col];
	}

	public Object getValueAt(int row, int col) {

		String out = "";

		switch (col) {
		case 0:
			out = "" + empleados.get(row).getId();
			break;
		case 1:
			out = empleados.get(row).getNombreCompleto();
			break;
		case 2:
			out = "" + empleados.get(row).getDni();
			break;
		}

		return out;
	}

}
