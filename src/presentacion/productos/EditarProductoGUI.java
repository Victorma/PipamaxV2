package presentacion.productos;

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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.Document;

import javax.swing.JComboBox;
import presentacion.GUI;
import negocio.controlador.ControladorAplicacion;

import negocio.Retorno;
import negocio.marcas.TransferListaMarcas;
import negocio.marcas.TransferMarca;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import negocio.productos.TComProducto;
import negocio.productos.TransferProducto;
import negocio.proveedores.TransferListaProveedores;
import negocio.proveedores.TransferListaSuministros;
import negocio.proveedores.TransferProveedor;
import negocio.proveedores.TransferSuministro;
import javax.swing.JTextField;

public class EditarProductoGUI extends GUI {
	private static final long serialVersionUID = -4702361447797063140L;
	//frames and panels
	private JTextField nombre;
	private JTextField idMarca;
	private Document documentidmarca;
	private JTextField stock;
	private JTextField precio;
	private JComboBox<String> scrollmarca;
	private List<TransferMarca> listamarcas;
	private int id;

	private ModeloProveProdu model;
	private JTable table;
	private JScrollPane pane;

	private class ModeloProveProdu extends AbstractTableModel {
		private static final long serialVersionUID = -7799427390618551029L;
		private List<TransferSuministro> ProveProds = new ArrayList<TransferSuministro>();
		private final String[] colNames = new String[] { "Id Proveedor",
				"Precio" };

		public void update(List<TransferSuministro> ProveProds) {
			this.ProveProds = ProveProds;
			this.fireTableDataChanged();
		}

		public TransferSuministro getTransfer(int pos) {
			return ProveProds.get(pos);
		}

		public int getColumnCount() {
			return colNames.length;
		}

		public int getRowCount() {
			return ProveProds.size();
		}

		@Override
		public String getColumnName(int col) {
			return colNames[col];
		}

		public Object getValueAt(int row, int col) {

			String out = "";

			switch (col) {
			case 0:
				out = "" + ProveProds.get(row).getIdProveedor();
				break;
			case 1:
				out = "" + ProveProds.get(row).getPrecio();
				break;
			}

			return out;
		}
	}

	public int getId() {
		return id;
	}

	private int buscaIdMarca(int _idmarca) {
		int ret = 0;
		try {
			for (int i = 0; i < listamarcas.size() && ret == 0; i++) {
				if (listamarcas.get(i).getId() == _idmarca)
					ret = i + 1;
			}
		} catch (NullPointerException ex) {
		}
		;

		return ret;
	}

	public EditarProductoGUI(GUI father, int id) {
		super(father);

		this.id = id;

		this.setLayout(new BorderLayout());

		//######### NORTE ######### 
		JPanel norte = new JPanel(new FlowLayout());

		JLabel textotitulo = new JLabel();
		textotitulo.setFont(new Font("Dialog", 1, 20));
		norte.add(textotitulo);

		//######### SUPERCENTRO #########
		JPanel center = new JPanel(new BorderLayout());
		//######### CENTRO 1 #########
		JPanel center1 = new JPanel(new GridLayout(4, 4));
		center1.setBorder(BorderFactory.createTitledBorder("Datos"));

		//SCROLL DE MARCA
		JPanel jMarca = new JPanel(new BorderLayout());
		idMarca = new JTextField();
		idMarca.setPreferredSize(new Dimension(50, 20));
		scrollmarca = new JComboBox<String>();
		scrollmarca.addActionListener(new CambiaScroll());
		scrollmarca.addItem("-----------");

		documentidmarca = idMarca.getDocument();
		documentidmarca.addDocumentListener(new JComboBoxStateController());
		jMarca.add(idMarca, BorderLayout.WEST);
		jMarca.add(scrollmarca, BorderLayout.CENTER);
		//FIN DE SCROLL DE MARCA

		JLabel lNombre = new JLabel("Nombre: ");
		nombre = new JTextField();
		JLabel lIdMarca = new JLabel("ID Marca: ");
		JLabel lStock = new JLabel("Stock: ");
		stock = new JTextField();
		JLabel lPrecio = new JLabel("Precio de Venta: ");
		precio = new JTextField();

		center1.add(lNombre);
		center1.add(nombre);
		center1.add(lIdMarca);
		center1.add(jMarca);
		center1.add(lStock);
		center1.add(stock);
		center1.add(lPrecio);
		center1.add(precio);

		//######### CENTRO 2 #########
		JPanel center2 = new JPanel(new BorderLayout());
		center2.setBorder(BorderFactory
				.createTitledBorder("Proveedores del producto"));

		model = new ModeloProveProdu();
		table = new JTable(model);
		table.setEnabled(true);
		table.setBackground(Color.white);
		pane = new JScrollPane(table);

		//BOTONES PROVEEDORES
		JPanel botProveedores = new JPanel(new GridLayout(1, 2));
		JButton agregar = new JButton("Añadir Proveedor");
		JButton borrar = new JButton("Borrar Proveedor");
		agregar.addActionListener(new AgregarProveedorListener());
		borrar.addActionListener(new BorrarProveedorListener());

		botProveedores.add(agregar);
		botProveedores.add(borrar);
		//FIN DE BOTONES PROVEEDORES

		center2.add(pane, BorderLayout.CENTER);
		center2.add(botProveedores, BorderLayout.SOUTH);
		//############################
		center.add(center1, BorderLayout.NORTH);
		center.add(center2, BorderLayout.CENTER);

		//######### SUR #########
		JPanel sur = new JPanel(new GridLayout(1, 2, 10, 10));
		JButton aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new AceptarListener());
		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new CancelarListener());
		sur.add(aceptar);
		sur.add(cancelar);

		//-------------------------------------
		this.add(norte, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(sur, BorderLayout.SOUTH);
		//set the configurations of the window
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		TransferProducto transfer = new TransferProducto();
		transfer.setId(id);

		ControladorAplicacion.getInstancia().accion(Acciones.productosConsultar,
				transfer);

	}

	private class CambiaScroll implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if (scrollmarca.getSelectedIndex() != 0
						&& (scrollmarca.getSelectedIndex()) != buscaIdMarca(Integer
								.parseInt(idMarca.getText())))
					idMarca.setText(""
							+ ((listamarcas.get(scrollmarca.getSelectedIndex() - 1))
									.getId()));
			} catch (NumberFormatException ex) {
				idMarca.setText(""
						+ ((listamarcas.get(scrollmarca.getSelectedIndex() - 1))
								.getId()));
			}

		}

	}

	private class JComboBoxStateController implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent arg0) {
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			try {
				scrollmarca.setSelectedIndex(buscaIdMarca(Integer
						.parseInt(idMarca.getText())));
			} catch (NumberFormatException ex) {
				scrollmarca.setSelectedIndex(0);
			}
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			try {
				scrollmarca.setSelectedIndex(buscaIdMarca(Integer
						.parseInt(idMarca.getText())));
			} catch (NumberFormatException ex) {
				scrollmarca.setSelectedIndex(0);
			}
		}

	}

	private class AgregarProveedorListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new CrearProveProduGUI(EditarProductoGUI.this);
		}

	}

	private class BorrarProveedorListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (table.getSelectedRow() != -1) {
				ControladorAplicacion.getInstancia().accion(
						Acciones.productosBorrarSuministro,
						model.getTransfer(table.getSelectedRow()));
				alVolver(null);
			}
		}

	}

	private class AceptarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if (nombre.getText().equals("") || idMarca.getText().equals("")
						|| stock.getText().equals("")
						|| precio.getText().equals("")) {
					JOptionPane.showMessageDialog(EditarProductoGUI.this,
							"Faltan Datos por Rellenar.");
				} else if (scrollmarca.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(EditarProductoGUI.this,
							"La marca introducida no es válida.");
				} else {
					TransferProducto _aEnviar = new TransferProducto();
					_aEnviar.setId(id);
					_aEnviar.setIdMarca(Integer.parseInt(idMarca.getText()));
					_aEnviar.setNombre(nombre.getText());
					_aEnviar.setPrecio(Double.parseDouble(precio.getText()));
					if (_aEnviar.getPrecio() <= 0)
						throw new NumberFormatException();
					_aEnviar.setStock(Integer.parseInt(stock.getText()));
					if (_aEnviar.getStock() < 0)
						throw new NumberFormatException();

					ControladorAplicacion.getInstancia().accion(
							Acciones.productosEditar, _aEnviar);

					dispose();
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(EditarProductoGUI.this,
						"Debes introducir números.");
			}

		}

	}

	private class CancelarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}

	}

	@Override
	public void actualiza(Acciones evento, Retorno datos) {
		switch (evento) {
		case productosConsultar:
			TransferProducto producto = ((TComProducto) datos.getDatos())
					.getProducto();
			id = producto.getId();
			nombre.setText(producto.getNombre());
			idMarca.setText("" + producto.getIdMarca());
			stock.setText("" + producto.getStock());
			precio.setText("" + producto.getPrecio());

			TransferListaMarcas tlm = new TransferListaMarcas();
			tlm.setLista(new ArrayList<TransferMarca>());
			ControladorAplicacion.getInstancia().accion(Acciones.marcasListado,
					tlm);
			ControladorAplicacion.getInstancia().accion(
					Acciones.productosListaSuministros, producto);
			break;
		case productosListaSuministros:
			model.update(((TransferListaSuministros) datos.getDatos())
					.getList());
			break;
		case productosCrearSuministro:
			if (datos.tieneErrores())
				JOptionPane.showMessageDialog(this,
						"Error al crear el suministro.");
			break;
		case productosBorrarSuministro:
			if (datos.tieneErrores())
				JOptionPane.showMessageDialog(this,
						"Error al borrar el suministro.");
			break;
		case marcasListado:
			listamarcas = ((TransferListaMarcas) datos.getDatos()).getLista();
			for (int i = 0; i < listamarcas.size(); i++) {
				scrollmarca.addItem(listamarcas.get(i).getNombre());
			}
			scrollmarca.setSelectedIndex(buscaIdMarca(Integer.parseInt(idMarca
					.getText())));
			break;
		case productosEditar:
			if (datos.tieneErrores())
				JOptionPane.showMessageDialog(this, "Error al editar.");
			break;
		case proveedoresListado:
			ArrayList<TransferProveedor> oldlist = ((TransferListaProveedores) datos
					.getDatos()).getLista();
			ArrayList<TransferProveedor> newList = ((TransferListaProveedores) datos
					.getDatos()).getLista();

			for (int i = 0; i < oldlist.size(); i++) {
				int idtmp = oldlist.get(i).getId();
				for (int j = 0; j < model.getRowCount(); j++) {
					if (idtmp == Integer.parseInt("" + model.getValueAt(j, 0))) {
						newList.remove(i);
						i--;
					}
				}
			}
			((TransferListaProveedores) datos.getDatos()).setLista(newList);
			if (this.getChild() != null)
				this.getChild().actualiza(evento, datos);

		default:
		}
	}

	@Override
	public void alVolver(GUI who) {

		TransferProducto producto = new TransferProducto();
		producto.setId(id);
		ControladorAplicacion.getInstancia().accion(
				Acciones.productosListaSuministros, producto);
	}

}