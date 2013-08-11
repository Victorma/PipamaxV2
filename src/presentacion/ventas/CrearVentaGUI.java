package presentacion.ventas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;

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
import negocio.clientes.TransferListaClientes;
import negocio.ventas.TComVenta;
import negocio.ventas.TransferVenta;

import com.toedter.calendar.JDateChooser;

import constantes.Acciones;
import constantes.Errores;

public class CrearVentaGUI extends GUI {

	private static final long serialVersionUID = 1L;
	private TComVenta venta;

	private JTable tablaVenta;
	private TablaVenta modeloTablaVenta;
	private JTextField fieldIdCliente;

	private TransferListaClientes listaClientes;
	private JComboBox<String> panelClientes;
	private JDateChooser calendar;

	public CrearVentaGUI(GUI padre) {
		super(padre);
		this.setTitle("Nueva venta");

		this.venta = new TComVenta();
		venta.setVenta(new TransferVenta());

		padre.setEnabled(false);
		//create the frame
		this.requestFocusInWindow();
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		JPanel top = new JPanel(new FlowLayout());
		top.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createDashedBorder(Color.black, 5, 4), "Cliente"));
		JLabel textoTop = new JLabel("Id Cliente:");

		fieldIdCliente = new JTextField();
		fieldIdCliente.setPreferredSize(new Dimension(50, 20));
		panelClientes = new JComboBox<String>();
		panelClientes.setPreferredSize(new Dimension(140, 20));
		panelClientes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (panelClientes.getSelectedIndex() != -1) {
					fieldIdCliente.setText(""
							+ listaClientes.getLista()
									.get(panelClientes.getSelectedIndex())
									.getId());
				}
			}
		});

		calendar = new JDateChooser();
		calendar.setDateFormatString("dd-MM-YYYY");

		ControladorFrontal.getInstancia().accion(Acciones.clientesListado,
				new TransferListaClientes());

		/*listaClientes.getLista().add(new TransferCliente());
		listaClientes.getLista().get(0).setEmail("Victorma-fire@hotmail.com");
		listaClientes.getLista().get(0).setId(1);
		for(TransferCliente cliente: listaClientes.getLista().toArray(new TransferCliente[listaClientes.getLista().size()]))
			this.panelClientes.addItem(cliente.getEmail());*/

		top.add(textoTop);
		top.add(fieldIdCliente);
		top.add(panelClientes);
		top.add(calendar);

		this.add(top, BorderLayout.NORTH);

		//tablaVentas.getRowCount();

		/* #############
		 * PANEL BOTONES
		 * #############
		 */
		JPanel left = new JPanel(new FlowLayout());
		left.setBorder(BorderFactory.createTitledBorder("Menu"));
		JButton agregar, quitar, guardar, salir;

		agregar = new JButton("Añadir");
		agregar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				venta.getVenta().addLineaVenta(0, 0);
				modeloTablaVenta.update(venta.getVenta());
			}

		});
		agregar.setPreferredSize(new Dimension(80, 30));
		quitar = new JButton("Quitar");
		quitar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (tablaVenta.getSelectedRow() != -1) {
					venta.getVenta().removeLineaVenta(
							tablaVenta.getSelectedRow());
					modeloTablaVenta.update(venta.getVenta());
				}
			}
		});

		quitar.setPreferredSize(new Dimension(80, 30));

		guardar = new JButton("Completar");
		guardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int i = 0;
				boolean incompleto = false;
				if (venta.getVenta().getNumLineasVenta() == 0)
					incompleto = true;
				if (!incompleto)
					try {
						int idCliente = Integer.parseInt(fieldIdCliente
								.getText());
						if (idCliente <= 0)
							incompleto = true;
						else {
							venta.getVenta().setIdCliente(idCliente);
						}
					} catch (NumberFormatException nfe) {
						incompleto = true;
					}
				if (!incompleto)
					if (calendar.isValid())
						incompleto = true;

				while (i < venta.getVenta().getNumLineasVenta() && !incompleto) {
					if (venta.getVenta().getLineaVenta(i).getIdProducto() == 0
							|| venta.getVenta().getLineaVenta(i).getCantidad() == 0) {
						JOptionPane.showMessageDialog(CrearVentaGUI.this,
								"Hay productos sin completar.", "Error",
								JOptionPane.ERROR_MESSAGE);
						incompleto = true;
					}
					i++;
				}
				if (!incompleto) {
					DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
					venta.getVenta().setFecha(
							dateFormat
									.format(calendar.getJCalendar().getDate()));
					ControladorFrontal.getInstancia().accion(
							Acciones.ventasCrear, venta.getVenta());
				}

			}

		});

		salir = new JButton("Cancelar");
		salir.addActionListener(new ActionListener() {

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
		bot.add(guardar);
		bot.add(salir);

		this.add(bot, BorderLayout.SOUTH);

		/* #############
		 * TABLA VENTA
		 * #############
		 */

		JPanel right = new JPanel(new GridLayout());
		right.setBorder(BorderFactory.createTitledBorder("Productos"));

		modeloTablaVenta = new TablaVenta();
		tablaVenta = new JTable(modeloTablaVenta);

		right.add(new JScrollPane(tablaVenta));

		this.add(right, BorderLayout.CENTER);

		//set the configurations of the window
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private class TablaVenta extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private TransferVenta venta = new TransferVenta();
		private final String[] colNames = new String[] { "Id", "Cantidad" };

		public void update(TransferVenta venta) {
			this.venta = venta;
			this.fireTableDataChanged();
		}

		public int getColumnCount() {
			return colNames.length;
		}

		public int getRowCount() {
			return venta.getNumLineasVenta();
		}

		@Override
		public String getColumnName(int col) {
			return colNames[col];
		}

		public Object getValueAt(int row, int col) {

			int out = 0;

			switch (col) {
			case 0:
				out = venta.getLineaVenta(row).getIdProducto();
				break;
			case 1:
				out = venta.getLineaVenta(row).getCantidad();
				break;
			}

			return out;
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return true;
		}

		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			try {
				int numero = Integer.parseInt((String) aValue);
				if (numero <= 0)
					throw new NumberFormatException();
				switch (columnIndex) {
				case 0:
					boolean encontrado = false;
					for (int i = 0; i < venta.getNumLineasVenta()
							&& !encontrado; i++)
						if ((i != rowIndex)
								&& venta.getLineaVenta(i).getIdProducto() == numero)
							encontrado = true;
					if (encontrado)
						JOptionPane
								.showMessageDialog(
										CrearVentaGUI.this,
										"El id introducido ya se encuentra en la venta.",
										"Error", JOptionPane.ERROR_MESSAGE);
					else
						venta.getLineaVenta(rowIndex).setIdProducto(numero);
					break;
				case 1:
					venta.getLineaVenta(rowIndex).setCantidad(numero);
					break;
				}
				this.fireTableDataChanged();
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(CrearVentaGUI.this,
						"El valor introducido no es válido.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	@Override
	public void actualiza(Acciones evento, Retorno datos) {
		if (this.getChild() == null) {
			if (evento == Acciones.ventasCrear) {
				if (!datos.tieneErrores()) {
					JOptionPane.showMessageDialog(CrearVentaGUI.this,
							"La venta se creo correctamente", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
				} else {
					Iterator<TError> it = datos.getErrores().getLista()
							.iterator();
					String errormsj = "Se produjeron los siguientes errores: \n";
					while (it.hasNext()) {
						TError thisError = it.next();
						switch (thisError.getErrorId()) {
						case Errores.clienteNoEncontrado:
							errormsj += " - No existe el cliente con el ID "
									+ (Integer) thisError.getDatos();
							break;
						case Errores.ventaSinProductos:
							errormsj += " - No hay productos";
							break;
						case Errores.productoNoEncontrado:
							errormsj += " - No existe el producto "
									+ (Integer) thisError.getDatos();
							break;
						case Errores.ventaProductoStockInsuficiente:
							errormsj += " - No hay Stock suficiente para "
									+ ((int[]) thisError.getDatos())[0]
									+ " (Stock "
									+ ((int[]) thisError.getDatos())[1] + ")";
							break;
						case Errores.productoNoModificado:
							errormsj += " - Producto no modificado!";
							break;
						}
						errormsj += "\n";
					}
					JOptionPane.showMessageDialog(CrearVentaGUI.this, errormsj,
							"Error", JOptionPane.ERROR_MESSAGE);

				}
			} else if (evento == Acciones.clientesListado) {
				if (datos.tieneErrores()) {
					JOptionPane.showMessageDialog(this,
							"No se pudo obtener la lista de clientes", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					listaClientes = (TransferListaClientes) datos.getDatos();
					for (int i = 0; i < listaClientes.getLista().size(); i++)
						this.panelClientes.addItem(""
								+ listaClientes.getLista().get(i).getDNI());
				}
			}
		}
	}

	@Override
	public void alVolver(GUI who) {
	}

}
