package presentacion.departamentos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import constantes.Acciones;
import constantes.Errores;
import negocio.Retorno;
import negocio.TError;
import negocio.departamentos.Departamento;
import negocio.empleados.Empleado;
import negocio.proyectos.Proyecto;
import presentacion.GUI;
import presentacion.controlador.ControladorFrontal;

public class DepartamentosGUI extends GUI {

	private static final long serialVersionUID = -1124401987989317611L;
	private TablaDepartamentos tablaDepartamentosModel;
	private JTable tablaDepartamentos;

	public DepartamentosGUI(GUI father) {
		super(father);
		this.setLayout(new BorderLayout());
		this.setTitle("PIPAMAX - DEPARTAMENTOS");

		JPanel top = new JPanel(new FlowLayout());
		top.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createDashedBorder(Color.ORANGE, 5, 4),
				"Estadisticas"));
		JLabel textoTop = new JLabel("Departamentos");
		textoTop.setSize(0, 50);
		top.add(textoTop);
		this.add(top, BorderLayout.NORTH);

		//tablaVentas.getRowCount();

		/* #############
		 * PANEL BOTONES
		 * #############
		 */
		JPanel left = new JPanel(new BorderLayout());
		left.setBorder(BorderFactory.createTitledBorder("Menu"));
		JPanel leftTop = new JPanel(new FlowLayout()), leftBot = new JPanel(
				new FlowLayout());
		JButton alta, consultar, editar, borrar, salir;

		alta = new JButton("Alta");
		alta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				new CrearDepartamentoGUI(DepartamentosGUI.this);
			}
		});
		alta.setPreferredSize(new Dimension(130, 30));
		leftTop.add(alta);

		consultar = new JButton("Consultar");
		consultar.setPreferredSize(new Dimension(130, 30));
		consultar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Departamento departamento = getSelectedDepartamento();
				new ConsultaDepartamentoGUI(DepartamentosGUI.this, departamento);

			}
		});
		leftTop.add(consultar);

		editar = new JButton("Editar");
		editar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Departamento departamento = getSelectedDepartamento();
				new EditarDepartamentoGUI(DepartamentosGUI.this, departamento);

			}
		});
		editar.setPreferredSize(new Dimension(130, 30));
		leftTop.add(editar);

		borrar = new JButton("Borrar");
		borrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Departamento departamento = getSelectedDepartamento();
				if (departamento != null) {
					GUI consultar = new ConsultaDepartamentoGUI(
							DepartamentosGUI.this, departamento);
					if (getChild() != null) {
						int respuesta = JOptionPane.showConfirmDialog(
								consultar, "¿Desea borrar este departamento?",
								"Borrar", JOptionPane.YES_NO_OPTION);
						consultar.dispose();
						if (respuesta == JOptionPane.YES_OPTION) {
							ControladorFrontal.getInstancia().accion(
									Acciones.departamentosBorrar, departamento);
							ControladorFrontal.getInstancia().accion(
									Acciones.departamentosListado, null);
						}
					}
				}
			}
		});
		borrar.setPreferredSize(new Dimension(130, 30));
		leftTop.add(borrar);

		salir = new JButton("Salir");
		salir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		salir.setPreferredSize(new Dimension(130, 30));
		leftBot.add(salir);

		left.add(leftBot, BorderLayout.SOUTH);
		left.add(leftTop, BorderLayout.CENTER);

		left.setPreferredSize(new Dimension(150, 0));
		this.add(left, BorderLayout.WEST);

		/* #############
		 * PANEL EMPLEADOS
		 * #############
		 */

		JPanel right = new JPanel(new GridLayout());
		right.setBorder(BorderFactory.createTitledBorder("Ventas"));

		tablaDepartamentosModel = new TablaDepartamentos();
		tablaDepartamentos = new JTable(tablaDepartamentosModel);

		right.add(new JScrollPane(tablaDepartamentos));

		this.add(right, BorderLayout.CENTER);

		/* #############
		 * CONFIGURACIONES
		 * #############
		 */

		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		ControladorFrontal.getInstancia().accion(Acciones.departamentosListado,
				null);
	}

	private class TablaDepartamentos extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private List<Departamento> departamentos;
		private final String[] colNames = new String[] { "Id", "Nombre",
				"Código" };

		TablaDepartamentos() {
			departamentos = new ArrayList<Departamento>();
		}

		public void update(Collection<Departamento> departamentos) {
			this.departamentos.clear();
			this.departamentos.addAll(departamentos);
			this.fireTableDataChanged();
		}

		public Departamento getEmpleado(int pos) {
			return departamentos.get(pos);
		}

		public int getColumnCount() {
			return colNames.length;
		}

		public int getRowCount() {
			return departamentos.size();
		}

		@Override
		public String getColumnName(int col) {
			return colNames[col];
		}

		public Object getValueAt(int row, int col) {

			String out = "";

			switch (col) {
			case 0:
				out = "" + departamentos.get(row).getId();
				break;
			case 1:
				out = departamentos.get(row).getNombre();
				break;
			case 2:
				out = "" + departamentos.get(row).getCodigo();
				break;
			}

			return out;
		}

	}

	private Departamento getSelectedDepartamento() {
		return ((tablaDepartamentos.getSelectedRow() != -1) ? tablaDepartamentosModel
				.getEmpleado(tablaDepartamentos.getSelectedRow()) : null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualiza(Acciones evento, Retorno datos) {
		if (!transmiteActualiza(evento, datos)) {
			if (evento == Acciones.departamentosListado) {
				if (!datos.tieneErrores()) {
					tablaDepartamentosModel
							.update((Collection<Departamento>) datos.getDatos());
				}
			}
		else if (evento == Acciones.departamentosBorrar) {
			if (datos.tieneErrores()) {
				StringBuilder errores = new StringBuilder("Se produjeron los siguientes errores: \n");
				for(TError error: datos.getErrores().getLista()){
					errores.append("  - ");
					switch(error.getErrorId()){
					case Errores.departamentoConEmpleados:{
						errores.append("Pertenece a los siguientes proyectos:\n");
						for(Empleado e:(Collection<Empleado>)error.getDatos())
							errores.append("     - Empleado: " + e.toString()+"\n");
						
					}break;
					default: errores.append(error.getDatos().toString()); break;				
					}
					errores.append("\n");
				}
				JOptionPane.showMessageDialog(this, errores.toString(), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "Departamento borrado correctamente.", "Correcto", JOptionPane.INFORMATION_MESSAGE);
				ControladorFrontal.getInstancia().accion(Acciones.empleadosListado, null);
			}
		}
		}

	}

	@Override
	public void alVolver(GUI who) {
		ControladorFrontal.getInstancia().accion(Acciones.departamentosListado,
				null);
	}

}
