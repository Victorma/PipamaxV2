package presentacion.clientes;

import constantes.Acciones;
import constantes.Errores;

/*
 * ----------
 * External libraries
 * ----------
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javax.swing.JTable;
import presentacion.GUI;
import presentacion.controlador.*;

import negocio.Retorno;
import negocio.TError;
import negocio.clientes.TransferCliente;
import negocio.clientes.TransferListaClientes;

public class ClientesGUI extends GUI {

	private static final long serialVersionUID = 1L;
	//frames and panels
	private JPanel left, right;
	private JScrollPane pane;

	//buttons
	private JButton botonCrear, botonBorrar, botonEditar, botonConsultar,
			botonReactivar, botonVolver;

	//table and model
	private static DefaultTableModel model;
	private JTable table;

	public ClientesGUI(GUI padre) {
		super(padre);
		this.setLayout(new BorderLayout());

		//set the left part of the window as a panel
		left = new JPanel(new BorderLayout());
		left.setBorder(BorderFactory.createTitledBorder("Acciones"));
		left.setPreferredSize(new Dimension(120, 0));

		/*
		 * Add buttons
		 * ---
		 * Crear
		 * Consultar
		 * Editar
		 * Borrar
		 * Volver
		 * ---
		 * 
		 * the buttons are defined as an attribute of the class VistaMarcas
		 */

		//Botones del centro
		JPanel leftTop = new JPanel(new FlowLayout());

		botonCrear = new JButton("Crear");
		botonCrear.setPreferredSize(new Dimension(130, 30));
		leftTop.add(botonCrear);
		botonCrear.addActionListener(new CrearListener());//add CrearListener

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
		botonReactivar.addActionListener(new ReactivarListener());//add reactivarListener

		botonBorrar = new JButton("Borrar");
		botonBorrar.setPreferredSize(new Dimension(130, 30));
		leftTop.add(botonBorrar);
		botonBorrar.addActionListener(new BorrarListener());//add EditarListener
		//fin botones centro

		botonVolver = new JButton("Volver");
		botonVolver.setPreferredSize(new Dimension(130, 30));

		left.setPreferredSize(new Dimension(150, 0));
		left.add(leftTop, BorderLayout.CENTER);
		left.add(botonVolver, BorderLayout.SOUTH);
		botonVolver.addActionListener(new VolverListener());//add VolverListener

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
		model.addColumn("DNI");
		model.addColumn("Nombre");
		model.addColumn("Apellido");
		model.addColumn("Tipo");

		//create the table with the model "model"
		table = new JTable(model);
		table.setEnabled(true);
		table.setBackground(Color.white);

		//add the table into a scrollPane
		pane = new JScrollPane(table);

		//add the scrollPanel to the right 
		right.add(pane);

		this.add(left, BorderLayout.WEST);
		this.add(right, BorderLayout.CENTER);

		TransferListaClientes clientes = new TransferListaClientes();
		clientes.setLista(new ArrayList<TransferCliente>());
		ControladorFrontal.getInstancia().accion(Acciones.clientesListado,
				clientes);

		this.addWindowListener(new ListenerCerrar());

		//set the window title
		this.setTitle("PIPAMAX - CLIENTES");

		//set the configurations of the window
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
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
				String dialogo = JOptionPane.showInputDialog(ClientesGUI.this,
						"Introduce ID");
				cancelar = (dialogo == null);
				if (!cancelar)
					id = Integer.parseInt(dialogo);
			} catch (NumberFormatException ex) {
				id = -1;
			}
		}
		return id;
	}

	private int pideDNI() {
		int dni = -1;
		boolean cancelar = false;

		while (dni == -1 && !cancelar) {
			try {
				String dialogo = JOptionPane.showInputDialog(ClientesGUI.this,
						"Introduce DNI");
				cancelar = (dialogo == null);
				if (!cancelar)
					dni = Integer.parseInt(dialogo);
			} catch (NumberFormatException ex) {
				dni = -1;
			}
		}
		return dni;
	}

	@Override
	public void actualiza(Integer evento, Retorno datos) {

		if (!transmiteActualiza(evento, datos)) // si no es consultar, refrescamos la hija.
			// Comprobacion de errores y elecciï¿½n del mensaje.
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
					case Errores.clienteNoEncontradoDNI:
						JOptionPane.showMessageDialog(this,
								"No se ha encontrado el DNI");
						break;
					case Errores.clienteNoRecuperado:
						JOptionPane
								.showMessageDialog(this,
										"No se ha podido recuperar el Cliente (¿El cliente está ya activado?)");
						break;
					default: {
					}
					}
				}

			} else {
				switch (evento) {
				case Acciones.clientesListado:
					TransferListaClientes clientes = (TransferListaClientes) datos
							.getDatos();
					model.getDataVector().removeAllElements();
					for (int i = 0; i < clientes.getLista().size(); i++) {
						model.addRow(new Object[] {
								clientes.getLista().get(i).getId(),
								clientes.getLista().get(i).getDNI(),
								clientes.getLista().get(i).getName(),
								clientes.getLista().get(i).getLastName(),
								clientes.getLista().get(i).getTipo() });
					}
					table.clearSelection();
					table.repaint();
					pane.repaint();
					break;
				case Acciones.clientesReactivar:
					JOptionPane.showMessageDialog(this,
							"Cliente Reactivado correctamente.");
					ControladorFrontal.getInstancia().accion(
							Acciones.clientesListado,
							new TransferListaClientes());
					break;
				}
			}
	};

	/*
	 * ---------------Listeners---------------
	 */

	private class CrearListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new CrearClienteGUI(ClientesGUI.this);
		}
	}

	private class ConsultarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int id = pideId();

			if (id != -1)
				new ConsultarClienteGUI(id, ClientesGUI.this);
		}

	}

	private class EditarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int id = pideId();

			if (id != -1)
				new EditarClienteGUI(id, ClientesGUI.this);
		}

	}

	private class ReactivarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int dni = pideDNI();

			if (dni != -1) {
				TransferCliente transfer = new TransferCliente();
				transfer.setDNI(dni);
				ControladorFrontal.getInstancia().accion(
						Acciones.clientesReactivar, transfer);
			}
		}

	}

	private class BorrarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int id = pideId();

			if (id != -1) {
				TransferCliente transfer = new TransferCliente();
				transfer.setId(id);
				new BorrarClienteGUI(id, ClientesGUI.this);
			}
		}

	}

	private class VolverListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ClientesGUI.this.setVisible(false);
			ClientesGUI.this.dispose();
		}

	}

	private class ListenerCerrar extends WindowAdapter {

		public void windowClosing(WindowEvent evt) {
			ClientesGUI.this.dispose();

			if (child != null)
				child.dispose();
		}
	}

	@Override
	public void alVolver(GUI who) {
		ControladorFrontal.getInstancia().accion(Acciones.clientesListado,
				new TransferListaClientes());
	}

}
