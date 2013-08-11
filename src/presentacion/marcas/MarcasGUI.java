package presentacion.marcas;

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
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javax.swing.JTable;
import presentacion.GUI;
import presentacion.controlador.*;

import negocio.Retorno;
import negocio.TError;
import negocio.marcas.TransferListaMarcas;
import negocio.marcas.TransferMarca;

public class MarcasGUI extends GUI {
	private static final long serialVersionUID = 1L;

	//frames and panels
	private JScrollPane pane;
	private JPanel left;
	private JPanel right;

	//buttons
	private JButton botonCrear;
	private JButton botonRecuperar;
	private JButton botonBorrar;
	private JButton botonEditar;
	private JButton botonConsultar;
	private JButton botonVolver;

	//table and model
	private static DefaultTableModel model;
	private JTable table;

	public MarcasGUI(GUI father) {
		super(father);

		this.setLayout(new BorderLayout());

		initializeButtonsPanel();

		initializeMarcasPanel();

		ControladorFrontal.getInstancia().accion(Acciones.marcasListado,
				new TransferListaMarcas());

		addWindowListener(new ListenerCerrar());

		//set the window title
		setTitle("PIPAMAX - MARCAS");

		//set the configurations of the window
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}

	public void initializeButtonsPanel() {
		//set the left part of the window as a panel
		left = new JPanel();
		left.setBorder(BorderFactory.createTitledBorder("Acciones"));

		//set de dimensions of the left part
		left.setLayout(new BorderLayout());

		//
		JPanel LeftTop = new JPanel(new FlowLayout());
		botonCrear = new JButton("Crear");
		botonCrear.setPreferredSize(new Dimension(130, 30));
		LeftTop.add(botonCrear);
		botonCrear.addActionListener(new CrearListener());//add CrearListener

		botonRecuperar = new JButton("Recuperar");
		botonRecuperar.setPreferredSize(new Dimension(130, 30));
		LeftTop.add(botonRecuperar);
		botonRecuperar.addActionListener(new RecuperarListener());//add CrearListener

		botonConsultar = new JButton("Consultar");
		botonConsultar.setPreferredSize(new Dimension(130, 30));
		LeftTop.add(botonConsultar);
		botonConsultar.addActionListener(new ConsultarListener());//add ConsultarListener

		botonEditar = new JButton("Editar");
		botonEditar.setPreferredSize(new Dimension(130, 30));
		LeftTop.add(botonEditar);
		botonEditar.addActionListener(new EditarListener());//add EditarListener

		botonBorrar = new JButton("Borrar");
		botonBorrar.setPreferredSize(new Dimension(130, 30));
		LeftTop.add(botonBorrar);
		botonBorrar.addActionListener(new BorrarListener());//add EditarListener

		botonVolver = new JButton("Volver");
		botonVolver.setPreferredSize(new Dimension(130, 30));
		left.add(LeftTop, BorderLayout.CENTER);
		left.add(botonVolver, BorderLayout.SOUTH);
		left.setPreferredSize(new Dimension(150, 0));
		botonVolver.addActionListener(new VolverListener());//add VolverListener

		MarcasGUI.this.add(left, BorderLayout.WEST);
	}

	public void initializeMarcasPanel() {
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
		model.addColumn("Nombre");

		//create the table with the model "model"
		table = new JTable(model);
		table.setEnabled(true);
		table.setBackground(Color.white);

		//add the table into a scrollPane
		pane = new JScrollPane(table);

		//add the scrollPanel to the right 
		right.add(pane);

		//add right to the window
		MarcasGUI.this.add(right, BorderLayout.CENTER);
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
				String dialogo = JOptionPane.showInputDialog(MarcasGUI.this,
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

	private String pideNombre() {
		String nombre = "";
		boolean cancelar = false;

		while (nombre == "" && !cancelar) {
			try {
				String dialogo = JOptionPane.showInputDialog(MarcasGUI.this,
						"Introduce Nombre");
				cancelar = (dialogo == null);
				if (!cancelar)
					nombre = dialogo;
			} catch (NumberFormatException ex) {
				nombre = "";
			}
		}
		return nombre;
	}

	/*
	 * ---------------Listeners---------------
	 */

	class CrearListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new CrearMarcaGUI(MarcasGUI.this);
		}
	}

	class ConsultarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int id = pideId();
			if (id != -1)
				new ConsultarMarcaGUI(id, MarcasGUI.this);
		}

	}

	class EditarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int id = pideId();
			if (id != -1)
				new EditarMarcaGUI(id, MarcasGUI.this);
		}

	}

	class BorrarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int id = pideId();
			if (id != -1)
				new BorrarMarcaGUI(id, MarcasGUI.this);
		}

	}

	class VolverListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MarcasGUI.this.dispose();
		}

	}

	class ListenerCerrar extends WindowAdapter {

		public void windowClosing(WindowEvent evt) {
			MarcasGUI.this.dispose();
		}
	}

	class RecuperarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String nombre = pideNombre();
			if (!nombre.equals("")) {
				TransferMarca transfer = new TransferMarca();
				transfer.setNombre(nombre);
				ControladorFrontal.getInstancia().accion(
						Acciones.marcasReactivar, transfer);
			}
		}

	}

	@Override
	public void actualiza(Acciones evento, Retorno datos) {

		if (!transmiteActualiza(evento, datos)) // si no es consultar, refrescamos la hija.
			// Comprobacion de errores y elecci�n del mensaje.
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
					case Errores.marcaNoEncontradoNombre:
						JOptionPane.showMessageDialog(this,
								"No se ha encontrado el nombre");
						break;
					case Errores.marcaNoRecuperada:
						JOptionPane
								.showMessageDialog(this,
										"No se ha podido recuperar la marca (¿La marca está ya activada?)");
						break;
					default: {
					}
					}
				}

			} else {
				switch (evento) {
				case marcasListado:
					TransferListaMarcas marcas = (TransferListaMarcas) datos
							.getDatos();
					model.getDataVector().removeAllElements();
					for (int i = 0; i < marcas.getLista().size(); i++) {
						model.addRow(new Object[] {
								marcas.getLista().get(i).getId(),
								marcas.getLista().get(i).getNombre() });
					}
					table.clearSelection();
					table.repaint();
					pane.repaint();
					break;
				case marcasReactivar:
					JOptionPane.showMessageDialog(this,
							"Marca Reactivada correctamente.");
					ControladorFrontal.getInstancia().accion(
							Acciones.marcasListado, new TransferListaMarcas());
					break;
				}
			}
	}

	@Override
	public void alVolver(GUI who) {
		ControladorFrontal.getInstancia().accion(Acciones.marcasListado,
				new TransferListaMarcas());
	};

}
