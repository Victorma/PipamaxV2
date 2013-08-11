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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import presentacion.GUI;
import presentacion.controlador.ControladorFrontal;

import negocio.Retorno;
import negocio.TError;
import negocio.pedidos.TComPedido;
import negocio.pedidos.TransferPedido;
import negocio.productos.TComProducto;
import negocio.productos.TransferListaProductos;
import negocio.proveedores.TComProveedorListaProductos;
import negocio.proveedores.TransferListaProveedores;

import com.toedter.calendar.JDateChooser;

import constantes.Acciones;
import constantes.Errores;

public class CrearPedidoGUI extends GUI {
	private static final long serialVersionUID = 1L;
	private JButton aceptarProveedor, aceptar, cancelar, agregar, quitar;

	private JComboBox<String> comboProveedores;
	private TransferListaProveedores listaProveedores;

	private TComPedido pedido;
	private List<TComProducto> listaProductos;

	private JDateChooser calendar;

	private JTable tablaPedido;
	private TablaPedido modeloTablaPedido;

	public CrearPedidoGUI(GUI padre) {
		super(padre);
		this.setTitle("Nuevo pedido");

		pedido = new TComPedido();
		pedido.setPedido(new TransferPedido());
		pedido.setProductos(new ArrayList<TComProducto>());
		listaProductos = new ArrayList<TComProducto>();

		padre.setEnabled(false);
		//create the frame
		this.requestFocusInWindow();
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		JPanel top = new JPanel(new FlowLayout());
		top.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createDashedBorder(Color.black, 5, 4),
				"Proveedor"));
		JLabel textoTop = new JLabel("Proveedor:");

		comboProveedores = new JComboBox<String>();
		comboProveedores.setPreferredSize(new Dimension(140, 20));

		aceptarProveedor = new JButton("Fijar");
		aceptarProveedor.setPreferredSize(new Dimension(58, 20));
		aceptarProveedor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboProveedores.getSelectedIndex() != -1) {
					pedido.setProveedor(listaProveedores.getLista().get(
							comboProveedores.getSelectedIndex()));
					agregar.setEnabled(true);
					quitar.setEnabled(true);
					aceptar.setEnabled(true);
					calendar.setEnabled(true);
					aceptarProveedor.setEnabled(false);
					comboProveedores.setEnabled(false);

					TComProveedorListaProductos tComPLP = new TComProveedorListaProductos();
					tComPLP.setProveedor(pedido.getProveedor());
					tComPLP.setProductos(new TransferListaProductos());

					ControladorFrontal.getInstancia().accion(
							Acciones.pedidosConsProdProv, tComPLP);
				}
			}
		});

		calendar = new JDateChooser();
		calendar.setEnabled(false);
		calendar.setDateFormatString("dd-MM-YYYY");

		ControladorFrontal.getInstancia().accion(Acciones.proveedoresListado,
				new TransferListaProveedores());

		top.add(textoTop);
		top.add(comboProveedores);
		top.add(aceptarProveedor);
		top.add(calendar);

		this.add(top, BorderLayout.NORTH);

		//tablaVentas.getRowCount();

		/* #############
		 * PANEL BOTONES
		 * #############
		 */
		JPanel left = new JPanel(new FlowLayout());
		left.setBorder(BorderFactory.createTitledBorder("Menu"));

		agregar = new JButton("Añadir");
		agregar.setEnabled(false);
		agregar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SelectorDeProductos(CrearPedidoGUI.this, listaProductos);
			}

		});
		agregar.setPreferredSize(new Dimension(80, 30));
		quitar = new JButton("Quitar");
		quitar.setEnabled(false);
		quitar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tablaPedido.getSelectedRow() != -1) {
					listaProductos.add(pedido.getProductos().get(
							tablaPedido.getSelectedRow()));
					pedido.getProductos().remove(tablaPedido.getSelectedRow());
					pedido.getPedido().removeLineaPedido(
							tablaPedido.getSelectedRow());
					modeloTablaPedido.update(pedido);
				}
			}
		});

		quitar.setPreferredSize(new Dimension(80, 30));

		aceptar = new JButton("Completar");
		aceptar.setEnabled(false);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				boolean incompleto = false;
				if (pedido.getPedido().getNumLineasPedido() == 0)
					incompleto = true;
				if (!incompleto)
					if (calendar.isValid())
						incompleto = true;
				int i = 0;
				while (i < pedido.getPedido().getNumLineasPedido()
						&& !incompleto) {
					if (pedido.getPedido().getLineaPedido(i).getCantidad() == 0) {
						JOptionPane.showMessageDialog(CrearPedidoGUI.this,
								"Hay productos sin completar.", "Error",
								JOptionPane.ERROR_MESSAGE);
						incompleto = true;
					}
					i++;
				}
				if (!incompleto) {
					DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
					pedido.getPedido().setFecha(
							dateFormat
									.format(calendar.getJCalendar().getDate()));
					pedido.getPedido().setIdProveedor(
							pedido.getProveedor().getId());
					ControladorFrontal.getInstancia().accion(
							Acciones.pedidosCrear, pedido.getPedido());
				}

			}

		});

		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		left.add(agregar);
		left.add(quitar);

		left.setPreferredSize(new Dimension(100, 0));
		this.add(left, BorderLayout.WEST);

		JPanel bot = new JPanel(new GridLayout(1, 2));
		bot.add(aceptar);
		bot.add(cancelar);

		this.add(bot, BorderLayout.SOUTH);

		/* #############
		 * TABLA VENTA
		 * #############
		 */

		JPanel right = new JPanel(new GridLayout());
		right.setBorder(BorderFactory.createTitledBorder("Productos"));

		modeloTablaPedido = new TablaPedido();
		tablaPedido = new JTable(modeloTablaPedido);

		right.add(new JScrollPane(tablaPedido));

		this.add(right, BorderLayout.CENTER);

		//set the configurations of the window
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@Override
	public void actualiza(Acciones evento, Retorno datos) {

		if (!transmiteActualiza(evento, datos)) {
			if (evento == Acciones.pedidosConsProdProv) {
				if (datos.tieneErrores()) {
					JOptionPane.showMessageDialog(this,
							"Error obteniendo la lista de productos.", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					this.dispose();
				} else {
					TComProveedorListaProductos aux = (TComProveedorListaProductos) datos
							.getDatos();
					if (aux.getProductos().getList().size() == 0) {
						JOptionPane.showMessageDialog(this,
								"No hay productos para ese proveedor.",
								"ERROR", JOptionPane.ERROR_MESSAGE);
						this.dispose();
					} else {
						pedido.setProveedor(aux.getProveedor());
						TComProducto producto = null;
						for (int i = 0; i < aux.getProductos().getList().size(); i++) {
							producto = new TComProducto();
							producto.setProducto(aux.getProductos().getList()
									.get(i));
							listaProductos.add(producto);
						}
					}
				}
			} else if (evento == Acciones.proveedoresListado) {
				if (datos.tieneErrores()) {
					JOptionPane.showMessageDialog(this,
							"Error obteniendo la lista de proveedores.",
							"ERROR", JOptionPane.ERROR_MESSAGE);
					this.dispose();
				} else {
					listaProveedores = (TransferListaProveedores) datos
							.getDatos();
					for (int i = 0; i < listaProveedores.getLista().size(); i++)
						comboProveedores.addItem(listaProveedores.getLista()
								.get(i).getName());

				}
			} else if (evento == Acciones.pedidosCrear) {
				if (!datos.tieneErrores()) {
					JOptionPane.showMessageDialog(CrearPedidoGUI.this,
							"El pedido se creo correctamente", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
				} else {
					Iterator<TError> it = datos.getErrores().getLista()
							.iterator();
					String errormsj = "Se produjeron los siguientes errores: \n";
					while (it.hasNext()) {
						TError thisError = it.next();
						switch (thisError.getErrorId()) {
						case Errores.proveedorNoEncontrado:
							errormsj += " - No existe el proveedor con el ID "
									+ (Integer) thisError.getDatos();
							break;
						case Errores.pedidoSinProductos:
							errormsj += " - No hay productos";
							break;
						case Errores.productoNoEncontrado:
							errormsj += " - No existe el producto "
									+ (Integer) thisError.getDatos();
							break;
						case Errores.pedidoProductoNoSuministrado:
							errormsj += " - El producto "
									+ ((int[]) thisError.getDatos())[0]
									+ " no lo suministra este proveedor.";
							break;
						case Errores.pedidoNoCreado:
							errormsj += " - ¡¡PEDIDO NO CREADO!!";
							break;
						}
						errormsj += "\n";
					}
					JOptionPane.showMessageDialog(CrearPedidoGUI.this,
							errormsj, "Error", JOptionPane.ERROR_MESSAGE);

				}
			}
		}

	}

	@Override
	public void alVolver(GUI who) {
		if (who instanceof SelectorDeProductos) {
			SelectorDeProductos aux = ((SelectorDeProductos) who);
			if (aux.seleccionado()) {
				this.listaProductos = aux.getListaProductos();
				this.pedido.getPedido().addLineaPedido(
						aux.getProducto().getProducto().getId(), 0);
				this.pedido.getProductos().add(aux.getProducto());
				this.modeloTablaPedido.update(pedido);
			}
		}
	}

	private class TablaPedido extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private TComPedido pedido = new TComPedido();
		private final String[] colNames = new String[] { "Id", "Nombre", "Cantidad", "Precio" };


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
				out = "" + pedido.getPedido().getLineaPedido(row).getCantidad();
				break;
			case 3:
				out = ""
						+ pedido.getProductos().get(row).getProducto()
								.getPrecio();
				break;
			}

			return out;
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			return col == 2;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			try {
				int numero = Integer.parseInt((String) aValue);
				if (numero <= 0)
					throw new NumberFormatException();
				switch (columnIndex) {
				case 2:
					pedido.getPedido().getLineaPedido(rowIndex)
							.setCantidad(numero);
					break;
				}
				this.fireTableDataChanged();
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(CrearPedidoGUI.this,
						"El valor introducido no es válido.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	private class SelectorDeProductos extends GUI {

		private static final long serialVersionUID = 1L;

		private boolean seleccionado = false;
		private TComProducto productoSeleccionado;
		private List<TComProducto> productos;
		private JTextField textIdProducto;
		private JComboBox<String> comboProductos;

		public SelectorDeProductos(GUI father, List<TComProducto> productos) {
			super(father);
			this.setLayout(new BorderLayout());

			this.productos = productos;

			JPanel top = new JPanel(new FlowLayout()), mid = new JPanel(
					new GridLayout(1, 2)), bot = new JPanel(
					new GridLayout(1, 2));

			this.add(top, BorderLayout.NORTH);
			this.add(mid, BorderLayout.CENTER);
			this.add(bot, BorderLayout.SOUTH);

			top.add(new JLabel(
					"Introduzca la Id del producto o seleccione uno de la lista."));

			textIdProducto = new JTextField();

			comboProductos = new JComboBox<String>();
			for (int i = 0; i < productos.size(); i++)
				comboProductos.addItem(productos.get(i).getProducto()
						.getNombre());

			comboProductos.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {
					if (comboProductos.getSelectedIndex() != -1)
						textIdProducto
								.setText(SelectorDeProductos.this.productos
										.get(comboProductos.getSelectedIndex())
										.getProducto().getId()
										+ "");
				}

			});

			mid.add(textIdProducto);
			mid.add(comboProductos);

			JButton aceptar, cancelar;

			aceptar = new JButton("Agregar");
			aceptar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {
					boolean incorrecto = false;
					boolean encontrado = false;
					try {
						int idProducto = Integer.parseInt(textIdProducto
								.getText());
						if (idProducto <= 0)
							incorrecto = true;
						else {

							int i = 0;
							while (!encontrado
									&& i < SelectorDeProductos.this.productos
											.size())
								if (idProducto == SelectorDeProductos.this.productos
										.get(i).getProducto().getId())
									encontrado = true;
								else
									i++;

							if (encontrado) {
								productoSeleccionado = SelectorDeProductos.this.productos
										.get(i);
								SelectorDeProductos.this.productos.remove(i);
								SelectorDeProductos.this.seleccionado = true;
								SelectorDeProductos.this.dispose();
							} else {
								JOptionPane
										.showMessageDialog(
												SelectorDeProductos.this,
												"El id que ha escrito no esta en la lista. ",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							}

						}
					} catch (NumberFormatException nfe) {
						incorrecto = true;
					}

					if (incorrecto) {
						JOptionPane.showMessageDialog(SelectorDeProductos.this,
								"El id que ha escrito no es válido. ", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});

			cancelar = new JButton("Cancelar");
			cancelar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					SelectorDeProductos.this.dispose();
				}
			});

			bot.add(aceptar);
			bot.add(cancelar);

			this.setSize(400, 120);
			this.setLocationRelativeTo(null);
			this.setVisible(true);

		}

		public boolean seleccionado() {
			return seleccionado;
		}

		public List<TComProducto> getListaProductos() {
			return this.productos;
		}

		public TComProducto getProducto() {
			return this.productoSeleccionado;
		}

		@Override
		public void actualiza(Acciones evento, Retorno datos) {
		}

		@Override
		public void alVolver(GUI who) {
		}

	}
}
