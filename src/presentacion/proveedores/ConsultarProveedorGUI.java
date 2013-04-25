package presentacion.proveedores;

import constantes.Acciones;

/*
 * ----------
 * External libraries
 * ----------
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javax.swing.JTable;
import presentacion.GUI;
import presentacion.controlador.*;

import negocio.Retorno;
import negocio.productos.TransferListaProductos;
import negocio.proveedores.TComProveedorListaProductos;
import negocio.proveedores.TransferProveedor;

public class ConsultarProveedorGUI extends GUI {

	private static final long serialVersionUID = 1L;
	// Paneles de la ventana
	private JPanel norte;
	private JPanel centro;
	private JPanel sur;

	// Componentes de la ventana
	private JButton aceptar;
	private JLabel labelId;
	private JLabel labelNif;
	private JLabel labelNombre;
	private JLabel labelTelefono;
	private JLabel labelEmail;

	//table and model
	private static DefaultTableModel model;
	private JTable table;

	public ConsultarProveedorGUI(Integer id, GUI father) {
		super(father);

		//create the frame
		this.setLayout(new BorderLayout());
		this.setResizable(false);

		norte = new JPanel(new FlowLayout());
		norte.add(new JLabel("Datos del proveedor."));

		this.add(norte, BorderLayout.NORTH);

		centro = new JPanel(new BorderLayout());

		JPanel centroTop = new JPanel(new GridLayout(5, 2));
		centroTop.setBorder(BorderFactory.createTitledBorder("Datos"));
		centroTop.setPreferredSize(new Dimension(100, 120));

		centroTop.add(new JLabel("Identificador: "));
		labelId = new JLabel();
		centroTop.add(labelId);

		centroTop.add(new JLabel("NIF: "));
		labelNif = new JLabel();
		centroTop.add(labelNif);

		centroTop.add(new JLabel("Nombre: "));
		labelNombre = new JLabel();
		centroTop.add(labelNombre);

		centroTop.add(new JLabel("Nº telefono: "));
		labelTelefono = new JLabel();
		centroTop.add(labelTelefono);

		centroTop.add(new JLabel("Email: "));
		labelEmail = new JLabel();
		centroTop.add(labelEmail);

		centro.add(centroTop, BorderLayout.NORTH);

		initializeListaProductos();

		this.add(centro, BorderLayout.CENTER);

		sur = new JPanel(new GridLayout(1, 2, 10, 10));

		aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new ListenerCancelar());
		sur.add(aceptar);

		this.add(sur, BorderLayout.SOUTH);

		//set the configurations of the window
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		TComProveedorListaProductos proveedor = new TComProveedorListaProductos();
		TransferProveedor tprov = new TransferProveedor();
		tprov.setId(id);
		proveedor.setProveedor(tprov);
		proveedor.setProductos(new TransferListaProductos());
		ControladorFrontal.getInstancia().accion(Acciones.pedidosConsProdProv,
				proveedor);
	}

	private void initializeListaProductos() {
		//Set the RIGHT part of the container
		JPanel centroBot = new JPanel(new BorderLayout());
		centroBot.setBorder(BorderFactory
				.createTitledBorder("Lista de productos asociados"));

		//create the model for the table
		model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};

		//add columns to the model
		model.addColumn("Id Producto");
		model.addColumn("Nombre");
		model.addColumn("Precio");

		//create the table with the model "model"
		table = new JTable(model);
		table.setEnabled(true);
		table.setBackground(Color.white);

		//add the table into a scrollPane
		JScrollPane pane = new JScrollPane(table);

		//add the scrollPanel to the right 
		centroBot.add(pane);

		//add right to the window
		centro.add(centroBot, BorderLayout.CENTER);
	}

	private void insertarProductos(TComProveedorListaProductos proveedor) {
		model.getDataVector().removeAllElements();
		for (int i = 0; i < proveedor.getProductos().getList().size(); i++) {
			model.addRow(new Object[] {
					proveedor.getProductos().getList().get(i).getId(),
					proveedor.getProductos().getList().get(i).getNombre(),
					proveedor.getProductos().getList().get(i).getPrecio() });
		}

	}

	private class ListenerCancelar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ConsultarProveedorGUI.this.dispose();
		}
	}

	@Override
	public void actualiza(Integer evento, Retorno datos) {
		if (!datos.tieneErrores()) {
			TComProveedorListaProductos transfer = ((TComProveedorListaProductos) datos
					.getDatos());
			labelId.setText("" + transfer.getProveedor().getId());
			labelNif.setText("" + transfer.getProveedor().getNif());
			labelNombre.setText(transfer.getProveedor().getName());
			labelTelefono.setText(""
					+ transfer.getProveedor().getTelephoneNumber());
			labelEmail.setText(transfer.getProveedor().getEmail());
			insertarProductos(transfer);
		} else {
			JOptionPane.showMessageDialog(this,
					"Error consultando el proveedor");
			this.dispose();
		}
	}

	@Override
	public void alVolver(GUI who) {
	}
}
