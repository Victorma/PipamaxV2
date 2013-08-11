package presentacion.ventas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import negocio.ventas.TComVenta;
import negocio.ventas.TransferVenta;

import constantes.Acciones;
import constantes.Errores;

public class ConsultarVentaGUI extends GUI {

	private static final long serialVersionUID = 1L;

	private TComVenta venta;

	JLabel id, nombre, apellidos, telefono, email, idCliente, fecha, total;
	private JTable tablaVenta;
	private TablaVenta modeloTablaVenta;

	ConsultarVentaGUI(GUI father, TransferVenta tventa) {
		super(father);
		this.setTitle("Venta");
		this.setLayout(new BorderLayout());

		this.venta = new TComVenta();

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

		JPanel top = new JPanel(new GridLayout(2, 1));
		top.add(topTop);
		top.add(topBot);

		this.add(top, BorderLayout.NORTH);

		JButton salir = new JButton("Cancelar");
		salir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ConsultarVentaGUI.this.dispose();
			}
		});

		JPanel bot = new JPanel(new GridLayout());
		bot.add(salir);

		this.add(bot, BorderLayout.SOUTH);

		/* #############
		 * TABLA VENTA
		 * #############
		 */

		JPanel center = new JPanel(new GridLayout());
		center.setBorder(BorderFactory.createTitledBorder("Productos"));

		modeloTablaVenta = new TablaVenta();
		tablaVenta = new JTable(modeloTablaVenta);

		center.add(new JScrollPane(tablaVenta));

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
				out = "" + venta.getVenta().getLineaVenta(row).getPrecio();
				break;
			}

			return out;
		}

	}

	@Override
	public void actualiza(Acciones evento, Retorno datos) {
		if (this.getChild() == null) {
			if (evento == Acciones.ventasConsultar) {

				if (datos.tieneErrores()) {

					Iterator<TError> it = datos.getErrores().getLista()
							.iterator();
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

					this.id.setText("" + venta.getVenta().getId());
					this.fecha.setText(venta.getVenta().getFecha());
					float total = 0;
					for (int i = 0; i < venta.getVenta().getNumLineasVenta(); i++)
						total += venta.getVenta().getLineaVenta(i).getPrecio()
								* venta.getVenta().getLineaVenta(i)
										.getCantidad();
					this.total.setText(total
							* (1 - venta.getVenta().getDescuento())
							+ "€ (Descuento de "
							+ (venta.getVenta().getDescuento()) * 100
							+ " % sobre " + total + "€)");

					this.idCliente.setText("" + venta.getCliente().getId());
					this.nombre.setText(venta.getCliente().getName());
					this.apellidos.setText(venta.getCliente().getLastName());
					this.email.setText(venta.getCliente().getEmail());
					this.telefono.setText(""
							+ venta.getCliente().getTelephoneNumber());

					this.modeloTablaVenta.update(venta);
				}
			}
		}
	}

	@Override
	public void alVolver(GUI who) {
	}

}
