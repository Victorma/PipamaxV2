package presentacion.pedidos;

/*
 * ----------
 * internal imports
 * ----------
 */

/*
 * ----------
 * External libraries
 * ----------
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import presentacion.GUI;
import presentacion.controlador.ControladorFrontal;

import negocio.Retorno;
import negocio.TError;
import negocio.pedidos.TComPedido;
import javax.swing.JLabel;
import javax.swing.JTable;
import negocio.pedidos.TransferPedido;

import constantes.Acciones;
import constantes.Errores;

public class ConsultarPedidoGUI extends GUI {

	private static final long serialVersionUID = 1L;

	JLabel id, fecha, total, idProveedor, nombre, telefono, email, nif, estado;

	TComPedido pedido;
	JTable tablaPedido;
	TablaPedido modeloTablaPedido;

	public ConsultarPedidoGUI(GUI padre, TransferPedido pedido) {
		super(padre);
		this.setLayout(new BorderLayout());

		this.pedido = new TComPedido();
		this.pedido.setPedido(pedido);

		// PANEL PROVEEDOR
		JPanel topTop = new JPanel(new FlowLayout());
		topTop.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createDashedBorder(Color.black, 5, 4),
				"Proveedor"));

		JLabel textoId = new JLabel("Id Proveedor: "), textoNombre = new JLabel(
				"Nombre: "), textoNif = new JLabel("NIF: "), textoTelefono = new JLabel(
				"Telefono: "), textoEmail = new JLabel("Email: ");

		idProveedor = new JLabel();
		nombre = new JLabel();
		telefono = new JLabel();
		email = new JLabel();
		nif = new JLabel();

		topTop.add(textoId);
		topTop.add(idProveedor);
		topTop.add(textoNombre);
		topTop.add(nombre);
		topTop.add(textoTelefono);
		topTop.add(telefono);
		topTop.add(textoEmail);
		topTop.add(email);
		topTop.add(textoNif);
		topTop.add(nif);

		// PANEL PEDIDO
		JPanel topBot = new JPanel(new FlowLayout());
		topBot.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createDashedBorder(Color.blue, 5, 4), "Pedido"));

		JLabel textoIdPedido = new JLabel("Id Pedido: "), textoFecha = new JLabel(
				"Fecha: "), textoTotal = new JLabel("Total: "), textoEstado = new JLabel(
				"Estado: ");

		id = new JLabel();
		fecha = new JLabel();
		total = new JLabel();
		estado = new JLabel();

		topBot.add(textoIdPedido);
		topBot.add(id);
		topBot.add(textoFecha);
		topBot.add(fecha);
		topBot.add(textoTotal);
		topBot.add(total);
		topBot.add(textoEstado);
		topBot.add(estado);

		JPanel top = new JPanel(new GridLayout(2, 1));

		top.add(topTop);
		top.add(topBot);

		this.add(top, BorderLayout.NORTH);

		JButton salir = new JButton("Cancelar");
		salir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ConsultarPedidoGUI.this.dispose();
			}
		});

		JPanel bot = new JPanel(new GridLayout());
		bot.add(salir);

		this.add(bot, BorderLayout.SOUTH);

		/* #############
		 * TABLA PEDIDO
		 * #############
		 */

		JPanel center = new JPanel(new GridLayout());
		center.setBorder(BorderFactory.createTitledBorder("Productos"));

		modeloTablaPedido = new TablaPedido();
		tablaPedido = new JTable(modeloTablaPedido);

		center.add(new JScrollPane(tablaPedido));

		this.add(center, BorderLayout.CENTER);

		//set the configurations of the window
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		TComPedido tComPedido = new TComPedido();
		tComPedido.setPedido(this.pedido.getPedido());

		ControladorFrontal.getInstancia().accion(Acciones.pedidosConsultar,
				tComPedido);
	}

	private void setEstado(char estado) {
		switch (estado) {
		case 'P':
			this.estado.setText("En Proceso");
			this.estado.setBackground(Color.ORANGE);
			break;
		case 'A':
			this.estado.setText("Anulado");
			this.estado.setBackground(Color.RED);
			break;
		case 'C':
			this.estado.setText("Completado");
			this.estado.setBackground(Color.GREEN);
			break;
		default:
			this.estado.setText("UNKNOWN");
			break;
		}
	}

	private class TablaPedido extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private TComPedido pedido = new TComPedido();
		private final String[] colNames = new String[] { "Id", "Nombre",
				"Marca", "Cantidad", "Precio" };

		public TablaPedido() {
			this.pedido.setPedido(new TransferPedido());
		}

		public void update(TComPedido pedido) {
			this.pedido = pedido;
			this.fireTableDataChanged();
		}

		public int getColumnCount() {
			return colNames.length;
		}

		public int getRowCount() {
			return pedido.getPedido().getNumLineasPedido();
		}

		@Override
		public String getColumnName(int col) {
			return colNames[col];
		}

		public Object getValueAt(int row, int col) {

			String out = "";

			switch (col) {
			case 0:
				out = ""
						+ pedido.getPedido().getLineaPedido(row)
								.getIdProducto();
				break;
			case 1:
				out = pedido.getProductos().get(row).getProducto().getNombre();
				break;
			case 2:
				out = pedido.getProductos().get(row).getMarca().getNombre();
				break;
			case 3:
				out = "" + pedido.getPedido().getLineaPedido(row).getCantidad();
				break;
			case 4:
				out = "" + pedido.getPedido().getLineaPedido(row).getPrecio();
				break;
			}

			return out;
		}

	}

	public void actualiza(Integer evento, Retorno datos) {
		if (this.getChild() == null) {
			if (evento == Acciones.pedidosConsultar) {

				if (datos.tieneErrores()) {

					Iterator<TError> it = datos.getErrores().getLista()
							.iterator();
					String errormsj = "Se produjeron los siguientes errores: \n";
					while (it.hasNext()) {
						TError thisError = it.next();
						switch (thisError.getErrorId()) {
						case Errores.proveedorNoEncontrado:
							errormsj += " - No se encontro el proveedor con el id "
									+ (Integer) thisError.getDatos();
							break;
						case Errores.productoNoEncontrado:
							errormsj += " - No se encontro el producto con el id "
									+ (Integer) thisError.getDatos();
							break;
						case Errores.marcaNoEncontrada:
							errormsj += " - No se encontro la marca con el id "
									+ (Integer) thisError.getDatos();
							break;
						case Errores.pedidoNoEncontrado:
							errormsj += " - No se encontro el pedido con el id "
									+ (Integer) thisError.getDatos();
							break;
						}
						errormsj += "\n";
					}
					JOptionPane.showMessageDialog(this, errormsj, "Error",
							JOptionPane.ERROR_MESSAGE);
					this.dispose();

				} else {

					TComPedido temp = ((TComPedido) datos.getDatos());
					this.pedido = temp;

					this.id.setText("" + pedido.getPedido().getId());
					this.fecha.setText(pedido.getPedido().getFecha());
					double total = 0;
					for (int i = 0; i < pedido.getPedido().getNumLineasPedido(); i++)
						total += pedido.getPedido().getLineaPedido(i)
								.getPrecio()
								* pedido.getPedido().getLineaPedido(i)
										.getCantidad();
					this.total.setText("" + total + "€");
					this.setEstado(pedido.getPedido().getEstado());

					this.idProveedor
							.setText("" + pedido.getProveedor().getId());
					this.nombre.setText(pedido.getProveedor().getName());
					this.email.setText(pedido.getProveedor().getEmail());
					this.telefono.setText(""
							+ pedido.getProveedor().getTelephoneNumber());
					this.nif.setText("" + pedido.getProveedor().getNif());

					this.modeloTablaPedido.update(pedido);
				}
			}
		}
	}

	@Override
	public void alVolver(GUI who) {
	}
}
