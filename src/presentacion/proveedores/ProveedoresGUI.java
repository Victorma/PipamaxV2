package presentacion.proveedores;

import constantes.Acciones;
import constantes.Errores;

/*
 * ----------
 * External libraries
 * ----------
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javax.swing.JTable;
import presentacion.GUI;
import presentacion.controlador.*;

import negocio.Retorno;
import negocio.TError;
import negocio.proveedores.TransferListaProveedores;
import negocio.proveedores.TransferProveedor;

public class ProveedoresGUI extends GUI {
	private static final long serialVersionUID = 1L;

	//frames and panels
	private JPanel left;
	private JPanel right;
	private JScrollPane pane;

	//buttons
	private JButton botonCrear;
	private JButton botonBorrar;
	private JButton botonEditar;
	private JButton botonConsultar;
	private JButton botonVolver;
	private JButton botonReactivar;

	//table and model
	private static DefaultTableModel model;
	private JTable table;

	public ProveedoresGUI(GUI father) {
		super(father);

		left = new JPanel(new BorderLayout());
		left.setBorder(BorderFactory.createTitledBorder("Acciones"));

		//Botones de arriba de la izquierda
		JPanel leftTop = new JPanel(new FlowLayout());

		botonCrear = new JButton("Crear");
		botonCrear.setPreferredSize(new Dimension(130, 30));
		leftTop.add(botonCrear);
		botonCrear.addActionListener(new CrearListener());

		botonConsultar = new JButton("Consultar");
		botonConsultar.setPreferredSize(new Dimension(130, 30));
		leftTop.add(botonConsultar);
		botonConsultar.addActionListener(new ConsultarListener());//add ConsultarListener

		botonEditar = new JButton("Editar");
		botonEditar.setPreferredSize(new Dimension(130, 30));
		leftTop.add(botonEditar);
		botonEditar.addActionListener(new EditarListener());//add EditarListener

		botonReactivar = new JButton("Reactivar");
		botonReactivar.setPreferredSize(new Dimension(130, 30));
		leftTop.add(botonReactivar);
		botonReactivar.addActionListener(new ReactivarListener());

		botonBorrar = new JButton("Borrar");
		botonBorrar.setPreferredSize(new Dimension(130, 30));
		leftTop.add(botonBorrar);
		botonBorrar.addActionListener(new BorrarListener());//add EditarListener

		left.add(leftTop, BorderLayout.CENTER);
		//fin de botones.

		botonVolver = new JButton("Volver");
		botonVolver.setPreferredSize(new Dimension(130, 30));
		left.add(botonVolver, BorderLayout.SOUTH);
		botonVolver.addActionListener(new VolverListener());//add VolverListener

		left.setPreferredSize(new Dimension(150, 0));

		//Set the RIGHT part of the container
		right = new JPanel(new BorderLayout());
		right.setBorder(BorderFactory.createTitledBorder("Lista"));

		//create the model for the table
		model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};

		//add columns to the model
		model.addColumn("Id");
		model.addColumn("NIF");
		model.addColumn("Nombre");

		//create the table with the model "model"
		table = new JTable(model);
		table.setEnabled(true);

		//add the table into a scrollPane
		pane = new JScrollPane(table);

		//add the scrollPanel to the right 
		right.add(pane);

		this.add(left, BorderLayout.WEST);
		this.add(right, BorderLayout.CENTER);

		this.addWindowListener(new ListenerCerrar());

		//set the window title
		this.setTitle("PIPAMAX - PROVEEDORES");

		//set the configurations of the window
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		ControladorFrontal.getInstancia().accion(Acciones.proveedoresListado,
				new TransferListaProveedores());
	}

	private int pideId() {
		int id = -1;
		boolean cancelar = false;

		try {
			id = (Integer) model.getValueAt(table.getSelectedRow(), 0);
		} catch (ArrayIndexOutOfBoundsException ex) {
			id = -1;
		}

		while (id == -1 && !cancelar) {
			try {
				String dialogo = JOptionPane.showInputDialog(
						ProveedoresGUI.this, "Introduce ID");
				cancelar = (dialogo == null);
				if (!cancelar)
					id = Integer.parseInt(dialogo);
			} catch (NumberFormatException ex) {
				id = -1;
			}
		}
		return id;
	}

	private int pideNIF() {
		int nif = -1;
		boolean cancelar = false;

		while (nif == -1 && !cancelar) {
			try {
				String dialogo = JOptionPane.showInputDialog(
						ProveedoresGUI.this, "Introduce NIF");
				cancelar = (dialogo == null);
				if (!cancelar)
					nif = Integer.parseInt(dialogo);
			} catch (NumberFormatException ex) {
				nif = -1;
			}
		}
		return nif;
	}

	/*
	 * ---------------Listeners---------------
	 */

	class CrearListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new CrearProveedorGUI(ProveedoresGUI.this);
		}
	}

	class ConsultarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int id = pideId();
			if (id != -1)
				new ConsultarProveedorGUI(id, ProveedoresGUI.this);
		}

	}

	class EditarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int id = pideId();
			if (id != -1)
				new EditarProveedorGUI(id, ProveedoresGUI.this);
		}

	}

	class BorrarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int id = pideId();
			if (id != -1)
				new BorrarProveedorGUI(id, ProveedoresGUI.this);
		}

	}

	class VolverListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ProveedoresGUI.this.dispose();
		}

	}

	class AsociarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ProveedoresGUI.this.dispose();
		}

	}

	class ReactivarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int nif = pideNIF();

			if (nif != -1) {
				TransferProveedor transfer = new TransferProveedor();
				transfer.setNif(nif);
				ControladorFrontal.getInstancia().accion(
						Acciones.proveedoresReactivar, transfer);
			}
		}

	}

	class ListenerCerrar extends WindowAdapter {

		public void windowClosing(WindowEvent evt) {
			ProveedoresGUI.this.dispose();
		}
	}

	@Override
	public void actualiza(Acciones evento, Retorno datos) {

		if (!transmiteActualiza(evento, datos))
			if (datos.tieneErrores()) {

				Iterator<TError> it = datos.getErrores().getLista().iterator();
				TError current = null;

				while (it.hasNext()) {

					current = it.next();
					switch (current.getErrorId()) {
					case Errores.errorDeAcceso:
						pane.add(new JLabel(
								"Error actualizando la base de datos."));
						break;
					case Errores.proveedorNoEncontradoNIF:
						JOptionPane.showMessageDialog(this,
								"No se ha encontrado el NIF");
						break;
					case Errores.proveedorNoRecuperado:
						JOptionPane
								.showMessageDialog(this,
										"No se ha podido recuperar el proveedor (¿El proveedor está ya activado?)");
						break;
					default: {
					}
					}
				}

			} else {
				switch (evento) {
				case proveedoresListado:
					TransferListaProveedores proveedores = (TransferListaProveedores) datos
							.getDatos();
					model.getDataVector().removeAllElements();
					for (int i = 0; i < proveedores.getLista().size(); i++) {
						model.addRow(new Object[] {
								proveedores.getLista().get(i).getId(),
								proveedores.getLista().get(i).getNif(),
								proveedores.getLista().get(i).getName() });//, proveedores.getLista().get(i)
					}
					table.clearSelection();
					table.repaint();
					pane.repaint();
					break;
				case proveedoresReactivar:
					JOptionPane.showMessageDialog(this,
							"Proveedor Reactivado correctamente.");
					ControladorFrontal.getInstancia().accion(
							Acciones.proveedoresListado,
							new TransferListaProveedores());
					break;
				}
			}
	}

	@Override
	public void alVolver(GUI who) {
		ControladorFrontal.getInstancia().accion(Acciones.proveedoresListado, new TransferListaProveedores());
	};
}
