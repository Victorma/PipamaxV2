package presentacion.productos;

/*
 * ----------
 * internal imports
 * ----------
 */

import constantes.Acciones;
import constantes.Errores;

/*
 * ----------
 * External libraries
 * ----------
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import presentacion.GUI;
import presentacion.controlador.*;

import negocio.Retorno;
import negocio.TError;
import negocio.marcas.TransferMarca;
import negocio.productos.TComProducto;
import negocio.productos.TransferProducto;
import negocio.proveedores.TransferListaSuministros;
import negocio.proveedores.TransferSuministro;

public class ConsultarProductosGUI extends GUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1342579292970896043L;
	private int id;
	private JTable table;
	private JLabel nombre, stock, precio, idMarca, nombreMarca;
	private ModeloProveProdu model;

	private class ModeloProveProdu extends AbstractTableModel {
		private static final long serialVersionUID = -2575721073377894935L;
		private List<TransferSuministro> ProveProds = new ArrayList<TransferSuministro>();
		private final String[] colNames = new String[] { "Id Proveedor",
				"Precio" };

		public void update(List<TransferSuministro> ProveProds) {
			this.ProveProds = ProveProds;
			this.fireTableDataChanged();
		}

		@SuppressWarnings("unused")
		public TransferSuministro getVenta(int pos) {
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

	public ConsultarProductosGUI(GUI father, int _id, boolean borrar) {
		super(father);
		id = _id;

		this.setLayout(new BorderLayout());

		//######### SUPERCENTRO #########
		JPanel center = new JPanel(new BorderLayout());
		//######### CENTRO 1 #########
		JPanel center1 = new JPanel(new GridLayout(4, 4));
		center1.setBorder(BorderFactory.createTitledBorder("Datos Producto"));

		JLabel lNombre = new JLabel("Nombre: ");
		nombre = new JLabel();
		JLabel lStock = new JLabel("Stock: ");
		stock = new JLabel();
		JLabel lPrecio = new JLabel("Precio de Venta: ");
		precio = new JLabel();

		center1.add(lNombre);
		center1.add(nombre);
		center1.add(lStock);
		center1.add(stock);
		center1.add(lPrecio);
		center1.add(precio);

		//######### CENTRO 0 #########
		JPanel center0 = new JPanel(new GridLayout(2, 2));
		center0.setBorder(BorderFactory.createTitledBorder("Marca"));

		JLabel lIdMarca = new JLabel("ID: ");
		idMarca = new JLabel();
		JLabel lNombreMarca = new JLabel("Nombre: ");
		nombreMarca = new JLabel();

		center0.add(lIdMarca);
		center0.add(idMarca);
		center0.add(lNombreMarca);
		center0.add(nombreMarca);

		//######### CENTRO 2 #########
		JPanel center2 = new JPanel(new BorderLayout());
		center2.setBorder(BorderFactory
				.createTitledBorder("Proveedores del producto"));

		model = new ModeloProveProdu();
		table = new JTable(model);
		table.setEnabled(true);
		table.setBackground(Color.white);
		JScrollPane pane = new JScrollPane(table);

		center2.add(pane, BorderLayout.CENTER);
		//############################
		center.add(center1, BorderLayout.NORTH);
		center.add(center0, BorderLayout.SOUTH);
		center.add(center2, BorderLayout.CENTER);

		//######### SUR #########
		JPanel sur = new JPanel(new GridLayout(1, 1, 10, 10));
		JButton aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new AceptarListener());
		sur.add(aceptar);

		//-------------------------------------
		this.add(center, BorderLayout.CENTER);
		this.add(sur, BorderLayout.SOUTH);
		//set the configurations of the window
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		TransferProducto tp = new TransferProducto();
		tp.setId(_id);
		ControladorFrontal.getInstancia().accion(Acciones.productosConsultar,
				tp);

		if (borrar) {
			int respuesta = JOptionPane.showConfirmDialog(this,
					"¿Desea borrar este producto?", "Borrar",
					JOptionPane.YES_NO_OPTION);
			if (respuesta == JOptionPane.YES_OPTION) {
				TransferProducto aBorrar = new TransferProducto();
				aBorrar.setId(id);
				ControladorFrontal.getInstancia().accion(
						Acciones.productosBorrar, aBorrar);
			}
			this.dispose();
		}

	}

	private class AceptarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}

	}

	@Override
	public void actualiza(Acciones evento, Retorno datos) {
		if(datos.tieneErrores()){
			JOptionPane.showMessageDialog(this, "Producto no encontrado");
			this.dispose();
		}else switch (evento) {
		case productosConsultar:
			TransferProducto producto = ((TComProducto) datos.getDatos())
					.getProducto();
			id = producto.getId();
			nombre.setText("" + producto.getNombre());
			stock.setText("" + producto.getStock());
			precio.setText("" + producto.getPrecio());
			idMarca.setText("" + producto.getIdMarca());

			TransferMarca marca = ((TComProducto) datos.getDatos()).getMarca();
			idMarca.setText("" + marca.getId());
			nombreMarca.setText("" + marca.getNombre());
			
			TransferMarca trm = new TransferMarca();
			trm.setId(producto.getIdMarca());
			ControladorFrontal.getInstancia().accion(Acciones.marcasConsultar,
					trm);
			ControladorFrontal.getInstancia().accion(
					Acciones.productosListaSuministros, producto);
			break;
		case productosListaSuministros:
			model.update(((TransferListaSuministros) datos.getDatos())
					.getList());
			break;
		case productosBorrar:
			if (datos.tieneErrores()) {

				Iterator<TError> it = datos.getErrores().getLista().iterator();
				TError current = null;

				while (it.hasNext()) {

					current = it.next();
					switch (current.getErrorId()) {
					case Errores.productoNoBorrado:
						JOptionPane.showMessageDialog(this, "¡¡NO SE BORRO!!.");
						break;
					case Errores.productoEnProceso:
						JOptionPane
								.showMessageDialog(this,
										"El producto que intenta borrar se encuentra en proceso de recepción.");
						break;
					default: {
					}
					}
				}
			} else {
				JOptionPane.showMessageDialog(this,
						"Producto borrado correctamente.");
			}

			break;
		default: {
		}
		}
	}

	@Override
	public void alVolver(GUI who) {
	}

}
