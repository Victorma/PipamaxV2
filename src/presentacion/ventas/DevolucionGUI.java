package presentacion.ventas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import presentacion.GUI;
import presentacion.controlador.ControladorFrontal;

import negocio.Retorno;
import negocio.TError;
import negocio.productos.TComProducto;
import negocio.ventas.TComVenta;
import negocio.ventas.TransferVenta;

import constantes.Acciones;
import constantes.Errores;

public class DevolucionGUI extends GUI {

	private static final long serialVersionUID = 1L;
	private TComVenta venta;
	private TComVenta aDevolver;

	JLabel id, nombre, apellidos, telefono, email, idCliente, fecha, total;
	private JTable tablaVenta, tablaDevolucion;
	private TablaVenta modeloTablaVenta, modeloTablaDevolucion;

	public DevolucionGUI(GUI father, TransferVenta tventa) {
		super(father);
		this.setTitle("Venta");

		this.venta = new TComVenta();
		this.venta.setVenta(tventa);
		this.aDevolver = new TComVenta();
		this.aDevolver.setVenta(new TransferVenta());
		this.aDevolver.setProductos(new ArrayList<TComProducto>());

		father.setEnabled(false);
		//create the frame
		this.requestFocusInWindow();
		this.setLayout(new BorderLayout());

		/* #############
		 * PANEL TOP
		 * #############
		 */

		// TOP-TOP
		JPanel topTop = new JPanel(new FlowLayout());
		topTop.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createDashedBorder(Color.black, 5, 4), "Cliente"));
		JLabel textoId = new JLabel("Id Cliente: "), textoNombre = new JLabel(
				"Nombre: "), textoApellidos = new JLabel("Apellidos: "), textoTelefono = new JLabel(
				"Telefono: "), textoEmail = new JLabel("Email: ");
		idCliente = new JLabel();
		nombre = new JLabel();
		apellidos = new JLabel();
		telefono = new JLabel();
		email = new JLabel();

		topTop.add(textoId);
		topTop.add(idCliente);
		topTop.add(textoNombre);
		topTop.add(nombre);
		topTop.add(textoApellidos);
		topTop.add(apellidos);
		topTop.add(textoTelefono);
		topTop.add(telefono);
		topTop.add(textoEmail);
		topTop.add(email);

		//TOP-BOT
		JPanel topBot = new JPanel(new FlowLayout());
		topBot.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createDashedBorder(Color.blue, 5, 4), "Venta"));
		JLabel textoIdVenta = new JLabel("Id Venta: "), textoFecha = new JLabel(
				"Fecha: "), textoTotal = new JLabel("Total: ");
		id = new JLabel();
		fecha = new JLabel();
		total = new JLabel();

		topBot.add(textoIdVenta);
		topBot.add(id);
		topBot.add(textoFecha);
		topBot.add(fecha);
		topBot.add(textoTotal);
		topBot.add(total);

		//TOP
		JPanel top = new JPanel(new GridLayout(2, 1));
		top.add(topTop);
		top.add(topBot);

		this.add(top, BorderLayout.NORTH);

		/* #############
		 * PANEL BOTONES
		 * #############
		 */
		JPanel left = new JPanel(new FlowLayout());
		left.setBorder(BorderFactory.createTitledBorder("Menu"));
		JButton devolver, nodevolver, guardar, salir;

		//BOTON PARA DEVOLVER
		devolver = new JButton("Devolver");
		devolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tablaVenta.getSelectedRow();
				if (row != -1) {
					venta.getVenta()
							.getLineaVenta(row)
							.setCantidad(
									venta.getVenta().getLineaVenta(row)
											.getCantidad() - 1);
					boolean propuesto = false;
					int propuestoEn = 0;
					for (int i = 0; i < aDevolver.getVenta()
							.getNumLineasVenta() && !propuesto; i++)
						if (aDevolver.getVenta().getLineaVenta(i)
								.getIdProducto() == venta.getVenta()
								.getLineaVenta(row).getIdProducto()) {
							propuesto = true;
							propuestoEn = i;
						}

					if (propuesto)
						aDevolver
								.getVenta()
								.getLineaVenta(propuestoEn)
								.setCantidad(
										aDevolver.getVenta()
												.getLineaVenta(propuestoEn)
												.getCantidad() + 1);
					else {
						aDevolver.getProductos().add(
								venta.getProductos().get(row));
						aDevolver.getVenta()
								.addLineaVenta(
										venta.getVenta().getLineaVenta(row)
												.getIdProducto(),
										1,
										venta.getVenta().getLineaVenta(row)
												.getPrecio(),
										venta.getVenta().getId());
					}

					if (venta.getVenta().getLineaVenta(row).getCantidad() == 0) {
						venta.getVenta().removeLineaVenta(row);
						venta.getProductos().remove(row);
					}
				}
				modeloTablaVenta.update(venta);
				modeloTablaDevolucion.update(aDevolver);
			}

		});
		devolver.setPreferredSize(new Dimension(80, 30));

		//BOTON PARA NO DEVOLVER
		nodevolver = new JButton("No devolver");
		nodevolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tablaDevolucion.getSelectedRow();
				if (row != -1) {
					aDevolver
							.getVenta()
							.getLineaVenta(row)
							.setCantidad(
									aDevolver.getVenta().getLineaVenta(row)
											.getCantidad() - 1);
					boolean propuesto = false;
					int propuestoEn = 0;
					for (int i = 0; i < venta.getVenta().getNumLineasVenta()
							&& !propuesto; i++)
						if (aDevolver.getVenta().getLineaVenta(row)
								.getIdProducto() == venta.getVenta()
								.getLineaVenta(i).getIdProducto()) {
							propuesto = true;
							propuestoEn = i;
						}

					if (propuesto)
						venta.getVenta()
								.getLineaVenta(propuestoEn)
								.setCantidad(
										venta.getVenta()
												.getLineaVenta(propuestoEn)
												.getCantidad() + 1);
					else {
						venta.getProductos().add(
								aDevolver.getProductos().get(row));
						venta.getVenta().addLineaVenta(
								aDevolver.getVenta().getLineaVenta(row)
										.getIdProducto(),
								1,
								aDevolver.getVenta().getLineaVenta(row)
										.getPrecio(),
								aDevolver.getVenta().getId());
					}

					if (aDevolver.getVenta().getLineaVenta(row).getCantidad() == 0) {
						aDevolver.getVenta().removeLineaVenta(row);
						aDevolver.getProductos().remove(row);
					}
				}
				modeloTablaVenta.update(venta);
				modeloTablaDevolucion.update(aDevolver);
			}

		});
		nodevolver.setPreferredSize(new Dimension(80, 30));

		guardar = new JButton("Completar");
		guardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ControladorFrontal.getInstancia().accion(
						Acciones.ventasDevolucion, aDevolver.getVenta());
			}

		});

		left.add(devolver);
		left.add(nodevolver);
		left.setPreferredSize(new Dimension(100, 0));
		this.add(left, BorderLayout.WEST);

		salir = new JButton("Cancelar");
		salir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		JPanel bot = new JPanel(new GridLayout());
		bot.add(guardar);
		bot.add(salir);

		this.add(bot, BorderLayout.SOUTH);

		/* #############
		 * TABLA VENTA
		 * #############
		 */

		JPanel center = new JPanel(new GridLayout(2, 1));

		JPanel centerTop = new JPanel(new GridLayout());
		centerTop.setBorder(BorderFactory.createTitledBorder("Productos"));

		modeloTablaVenta = new TablaVenta();
		tablaVenta = new JTable(modeloTablaVenta);

		JPanel centerBot = new JPanel(new GridLayout());
		centerBot.setBorder(BorderFactory.createTitledBorder("Devolucion"));

		modeloTablaDevolucion = new TablaVenta();
		tablaDevolucion = new JTable(modeloTablaDevolucion);

		centerTop.add(new JScrollPane(tablaVenta));
		centerBot.add(new JScrollPane(tablaDevolucion));

		center.add(centerTop);
		center.add(centerBot);

		this.add(center, BorderLayout.CENTER);

		//set the configurations of the window
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		ControladorFrontal.getInstancia().accion(Acciones.ventasConsultar,
				tventa);
	}

	private class TablaVenta extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private TComVenta venta = new TComVenta();
		private final String[] colNames = new String[] { "Id", "Nombre",
				"Marca", "Cantidad", "Precio" };


		public TablaVenta() {
			this.venta.setVenta(new TransferVenta());
		}

		public void update(TComVenta venta) {
			this.venta = venta;
			this.fireTableDataChanged();
		}

		public int getColumnCount() {
			return colNames.length;
		}

		public int getRowCount() {
			return venta.getVenta().getNumLineasVenta();
		}

		@Override
		public String getColumnName(int col) {
			return colNames[col];
		}

		public Object getValueAt(int row, int col) {

			String out = "";

			switch (col) {
			case 0:
				out = "" + venta.getVenta().getLineaVenta(row).getIdProducto();
				break;
			case 1:
				out = venta.getProductos().get(row).getProducto().getNombre();
				break;
			case 2:
				out = venta.getProductos().get(row).getMarca().getNombre();
				break;
			case 3:
				out = "" + venta.getVenta().getLineaVenta(row).getCantidad();
				break;
			case 4:
				out = ""
						+ venta.getProductos().get(row).getProducto()
								.getPrecio();
				break;
			}

			return out;
		}

	}

	@Override
	public void actualiza(Integer evento, Retorno datos) {
		if (evento == Acciones.ventasConsultar) {

			if (datos.tieneErrores()) {

				Iterator<TError> it = datos.getErrores().getLista().iterator();
				String errormsj = "Se produjeron los siguientes errores: \n";
				while (it.hasNext()) {
					TError thisError = it.next();
					switch (thisError.getErrorId()) {
					case Errores.clienteNoEncontrado:
						errormsj += " - No se encontro el cliente con el id "
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
					case Errores.ventaNoEncontrada:
						errormsj += " - No se encontro la venta con el id "
								+ (Integer) thisError.getDatos();
						break;
					}
					errormsj += "\n";
				}
				JOptionPane.showMessageDialog(this, errormsj, "Error",
						JOptionPane.ERROR_MESSAGE);
				this.dispose();
			} else {

				TComVenta temp = ((TComVenta) datos.getDatos());
				this.venta = temp;
				this.aDevolver.getVenta().setId(temp.getVenta().getId());

				this.id.setText("" + venta.getVenta().getId());
				this.fecha.setText(venta.getVenta().getFecha());
				float total = 0;
				for (int i = 0; i < venta.getVenta().getNumLineasVenta(); i++)
					total += venta.getVenta().getLineaVenta(i).getPrecio()
							* venta.getVenta().getLineaVenta(i).getCantidad();
				this.total.setText(total
						* (1 - venta.getVenta().getDescuento())
						+ "€ (Descuento de "
						+ (venta.getVenta().getDescuento()) * 100 + " % sobre "
						+ total + "€)");

				this.idCliente.setText("" + venta.getCliente().getId());
				this.nombre.setText(venta.getCliente().getName());
				this.apellidos.setText(venta.getCliente().getLastName());
				this.email.setText(venta.getCliente().getEmail());
				this.telefono.setText(""
						+ venta.getCliente().getTelephoneNumber());

				this.modeloTablaVenta.update(venta);
			}
		} else if (evento == Acciones.ventasDevolucion) {

			if (datos.tieneErrores()) {

				Iterator<TError> it = datos.getErrores().getLista().iterator();
				String errormsj = "Se produjeron los siguientes errores: \n";
				while (it.hasNext()) {
					TError thisError = it.next();
					switch (thisError.getErrorId()) {
					case Errores.ventaProductoNoPertenece:
						errormsj += " - Se intento devolver un producto que no pertenecia a la venta, ID: "
								+ (Integer) thisError.getDatos();
						break;
					case Errores.ventaNoEncontrada:
						errormsj += " - No se encontro la venta con el id "
								+ (Integer) thisError.getDatos();
						break;
					case Errores.ventaDevolucionNoRealizada:
						errormsj += " - ¡¡NO SE REALIZO LA DEVOLUCION!! "
								+ (Integer) thisError.getDatos();
						break;
					}
					errormsj += "\n";
				}
				JOptionPane.showMessageDialog(this, errormsj, "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				TransferVenta devuelto = (TransferVenta) datos.getDatos();
				double total = 0;
				int totalUds = 0;
				for (int i = 0; i < devuelto.getNumLineasVenta(); i++) {
					totalUds += devuelto.getLineaVenta(i).getCantidad();
					total += devuelto.getLineaVenta(i).getPrecio()
							* devuelto.getLineaVenta(i).getCantidad();
				}
				total *= (1 - venta.getVenta().getDescuento());

				JOptionPane
						.showMessageDialog(
								this,
								"Se devolvieron "
										+ totalUds
										+ " productos en total. Se devolveran al cliente "
										+ total
										+ "€ . \n(Advertencia, el stock de los productos no es sumado automaticamente,\n"
										+ " solo manualmente pues se debe comprobar que los productos devueltos esten \n"
										+ "en correcto estado)");
				this.dispose();
			}

		}

	}

	@Override
	public void alVolver(GUI who) {
	}

}
