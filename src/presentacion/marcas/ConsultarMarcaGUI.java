package presentacion.marcas;

/*
 * ----------
 * internal imports
 * ----------
 */

import constantes.Acciones;

/*
 * ----------
 * External libraries
 * ----------
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javax.swing.JTable;
import presentacion.GUI;
import negocio.controlador.ControladorAplicacion;

import negocio.Retorno;
import negocio.marcas.TComMarcaListaProductos;
import negocio.marcas.TransferMarca;
import negocio.productos.TransferListaProductos;

public class ConsultarMarcaGUI extends GUI {

	private static final long serialVersionUID = 1L;

	// Paneles de la ventana
	JPanel norte;
	JPanel centro;
	JPanel sur = new JPanel();

	// Componentes de la ventana
	JButton volver;
	JButton cancelar;
	JLabel labelNombre;

	//table and model
	private static DefaultTableModel model;
	private JTable table;

	public ConsultarMarcaGUI(Integer id, MarcasGUI padre) {
		super(padre);

		//create the frame
		this.setLayout(new BorderLayout());
		this.setResizable(false);

		norte = new JPanel(new BorderLayout());
		norte.setBorder(BorderFactory.createTitledBorder("Nombre de la marca"));
		labelNombre = new JLabel();
		norte.add(labelNombre);

		this.add(norte, BorderLayout.NORTH);

		initializeListaProductos();

		volver = new JButton("Volver");
		volver.addActionListener(new ListenerCancelar());
		sur.add(volver);

		//a�adimos las zonas
		this.add(centro, BorderLayout.CENTER);
		this.add(sur, BorderLayout.SOUTH);
		//set the configurations of the window
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		setResizable(false);

		TComMarcaListaProductos marca = new TComMarcaListaProductos();
		marca.setMarca(new TransferMarca());
		marca.setProductos(new TransferListaProductos());
		marca.getMarca().setId(id);
		ControladorAplicacion.getInstancia().accion(
				Acciones.marcasListadoProductos, marca);

	}

	private void initializeListaProductos() {
		//Set the RIGHT part of the container
		centro = new JPanel(new BorderLayout());
		centro.setBorder(BorderFactory
				.createTitledBorder("Lista de productos de la marca"));

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
		model.addColumn("Precio");
		model.addColumn("Stock");

		//create the table with the model "model"
		table = new JTable(model);
		table.setEnabled(true);
		table.setBackground(Color.white);

		//add the table into a scrollPane
		JScrollPane pane = new JScrollPane(table);

		//add the scrollPanel to the right 
		centro.add(pane);

		//add right to the window
		this.add(centro, BorderLayout.CENTER);
	}

	//Metodo para insertar los productos en la tabla
	private void insertarProductos(TComMarcaListaProductos marca) {
		model.getDataVector().removeAllElements();
		for (int i = 0; i < marca.getProductos().getList().size(); i++) {
			model.addRow(new Object[] {
					marca.getProductos().getList().get(i).getId(),
					marca.getProductos().getList().get(i).getNombre(),
					marca.getProductos().getList().get(i).getPrecio(),
					marca.getProductos().getList().get(i).getStock() });
		}

	}

	class ListenerCancelar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ConsultarMarcaGUI.this.setVisible(false);
			ConsultarMarcaGUI.this.dispose();
		}
	}

	@Override
	public void actualiza(Acciones evento, Retorno datos) {

		if (!datos.tieneErrores()) {
			TComMarcaListaProductos transfer = ((TComMarcaListaProductos) datos
					.getDatos());
			labelNombre.setText(transfer.getMarca().getNombre());
			insertarProductos(transfer);
		} else {
			JOptionPane.showMessageDialog(this, "Error consultando la marca");
			this.dispose();
		}
	}

	@Override
	public void alVolver(GUI who) {
	}
}
