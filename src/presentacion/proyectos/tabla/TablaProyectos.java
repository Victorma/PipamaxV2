package presentacion.proyectos.tabla;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import negocio.proyectos.Proyecto;

public class TablaProyectos extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private List<Proyecto> proyectos;
	private final String[] colNames = new String[] {"Id", "Nombre", "Descripción"};
	
	public TablaProyectos(){
		proyectos = new ArrayList<Proyecto>();
	}
	
	public void update(Collection<Proyecto> proyectos){
		this.proyectos.clear();
		this.proyectos.addAll(proyectos);
	 	this.fireTableDataChanged();
	}
	
	public Proyecto getProyecto(int pos){
		return proyectos.get(pos);
	}
	
	public int getColumnCount() { return colNames.length; }
	public int getRowCount() { return proyectos.size();}
	@Override
	public String getColumnName(int col) 
		{ 
			return colNames[col];
		}
	public Object getValueAt(int row, int col) {
		
		String out = "";
		
		switch(col){
			case 0 : out = ""+proyectos.get(row).getId(); break;
			case 1 : out = proyectos.get(row).getNombre(); break;
			case 2 : {
				if(proyectos.get(row).getDescripcion().length()>15)
					out = ""+proyectos.get(row).getDescripcion().substring(0, 15)+"...";
				else out = proyectos.get(row).getDescripcion();
			
			}break;
		}
		
		return out;
	}
	
}