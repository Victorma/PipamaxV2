package presentacion.pedidos;

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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import presentacion.GUI;
import negocio.controlador.ControladorAplicacion;

import negocio.Retorno;
import negocio.TError;
import negocio.pedidos.TransferListaPedidos;
import negocio.pedidos.TransferPedido;
import javax.swing.JLabel;
import javax.swing.JTable;

public class PedidosGUI extends GUI {
	private static final long serialVersionUID = 1L;

	JLabel seleccionado;
	//table and model
	private TablaListaPedidos pedidosProc;
	private TablaListaPedidos pedidosComp;
	private TablaListaPedidos pedidosAnul;
	private JTable tableProc;
	private JTable tableComp;
	private JTable tableAnul;
	private JTable selectedTable;

	public PedidosGUI(GUI father) {
		super(father);

		this.setLayout(new BorderLayout());
		this.setTitle("PIPAMAX - PEDIDOS");

		JPanel top = new JPanel(new FlowLayout());
		top.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createDashedBorder(Color.RED, 5, 4),
				"Estadisticas"));
		JLabel textoTop = new JLabel("Pedidos - Tabla seleccionada: ");
		seleccionado = new JLabel("Ninguna");
		textoTop.setSize(0, 50);
		top.add(textoTop);
		top.add(seleccionado);
		this.add(top, BorderLayout.NORTH);

		//tablaVentas.getRowCount();

		/* #############
		 * PANEL BOTONES
		 * #############
		 */
		JPanel left = new JPanel(new BorderLayout());
		left.setBorder(BorderFactory.createTitledBorder("Menu"));
		JPanel leftTop = new JPanel(new FlowLayout()), leftBot = new JPanel(
				new FlowLayout());
		JButton abrir, consultar, anular, completar, borrar, salir;

		abrir = new JButton("Nuevo Pedido");
		abrir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				new CrearPedidoGUI(PedidosGUI.this);
			}
		});
		abrir.setPreferredSize(new Dimension(130, 30));
		leftTop.add(abrir);

		consultar = new JButton("Consultar");
		consultar.setPreferredSize(new Dimension(130, 30));
		consultar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TransferPedido pedido = getSelectedPedido();
				if (pedido == null)
					pedido = pideId();
				if (pedido != null)
					new ConsultarPedidoGUI(PedidosGUI.this, pedido);

			}
		});
		leftTop.add(consultar);

		anular = new JButton("Anular");
		anular.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				TransferPedido pedido = getSelectedPedido();
				if (pedido == null)
					pedido = pideId();
				if (pedido != null) {
					GUI consultar = new ConsultarPedidoGUI(PedidosGUI.this,
							pedido);
					if (getChild() != null) {
						int respuesta = JOptionPane.showConfirmDialog(
								consultar, "¿Desea anular este pedido?",
								"Borrar", JOptionPane.YES_NO_OPTION);
						consultar.dispose();
						if (respuesta == JOptionPane.YES_OPTION) {
							ControladorAplicacion.getInstancia().accion(
									Acciones.pedidosAnular, pedido);
						}
					}
					consultar.dispose();
				}
			}
		});
		anular.setPreferredSize(new Dimension(130, 30));
		leftTop.add(anular);

		completar = new JButton("Completar");
		completar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				TransferPedido pedido = getSelectedPedido();
				if (pedido == null)
					pedido = pideId();
				if (pedido != null) {
					GUI consultar = new ConsultarPedidoGUI(PedidosGUI.this,
							pedido);
					if (getChild() != null) {
						int respuesta = JOptionPane
								.showConfirmDialog(
										consultar,
										"¿Desea completar y actualizar el stock este pedido?",
										"Borrar", JOptionPane.YES_NO_OPTION);
						consultar.dispose();
						if (respuesta == JOptionPane.YES_OPTION) {
							ControladorAplicacion.getInstancia().accion(
									Acciones.pedidosCompletar, pedido);
						}
					}
					consultar.dispose();
				}

			}
		});
		completar.setPreferredSize(new Dimension(130, 30));
		leftTop.add(completar);

		borrar = new JButton("Borrar");
		borrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				TransferPedido pedido = getSelectedPedido();
				if (pedido == null)
					pedido = pideId();
				if (pedido != null) {
					GUI consultar = new ConsultarPedidoGUI(PedidosGUI.this,
							pedido);
					if (getChild() != null) {
						int respuesta = JOptionPane.showConfirmDialog(
								consultar, "¿Desea borrar este pedido?",
								"Borrar", JOptionPane.YES_NO_OPTION);
						consultar.dispose();
						if (respuesta == JOptionPane.YES_OPTION) {
							ControladorAplicacion.getInstancia().accion(
									Acciones.pedidosBorrar, pedido);
						}
					}
					consultar.dispose();
				}

			}
		});
		borrar.setPreferredSize(new Dimension(130, 30));
		leftTop.add(borrar);

		salir = new JButton("Salir");
		salir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		salir.setPreferredSize(new Dimension(130, 30));
		leftBot.add(salir);

		left.add(leftBot, BorderLayout.SOUTH);
		left.add(leftTop, BorderLayout.CENTER);

		left.setPreferredSize(new Dimension(150, 0));
		this.add(left, BorderLayout.WEST);

		/* #############
		 * PANELES PEDIDOS
		 * #############
		 */

		JPanel right = new JPanel(new GridLayout(1, 3));
		right.setBorder(BorderFactory.createTitledBorder("Pedidos"));

		pedidosProc = new TablaListaPedidos();
		pedidosComp = new TablaListaPedidos();
		pedidosAnul = new TablaListaPedidos();
		tableProc = new JTable(pedidosProc);
		tableProc.addFocusListener(selectorTablas);

		TransferListaPedidos pedidos = new TransferListaPedidos();
		pedidos.setLista(new ArrayList<TransferPedido>());
		TransferPedido pedido1 = new TransferPedido();
		TransferPedido pedido2 = new TransferPedido();
		pedido1.setId(1);
		pedido2.setId(2);
		pedido1.setFecha("a");
		pedido2.setFecha("b");
		pedido1.setIdProveedor(10);
		pedido2.setIdProveedor(20);

		pedidos.getLista().add(pedido1);
		pedidos.getLista().add(pedido2);
		pedidosProc.update(pedidos);
		pedidosComp.update(pedidos);

		tableComp = new JTable(pedidosComp);
		tableComp.addFocusListener(selectorTablas);

		tableAnul = new JTable(pedidosAnul);
		tableAnul.addFocusListener(selectorTablas);

		selectedTable = null;

		JScrollPane rightLeft = new JScrollPane(tableProc);
		rightLeft.setBorder(BorderFactory.createTitledBorder("En Proceso"));

		JScrollPane rightMid = new JScrollPane(tableComp);
		rightMid.setBorder(BorderFactory.createTitledBorder("Completados"));

		JScrollPane rightRight = new JScrollPane(tableAnul);
		rightRight.setBorder(BorderFactory.createTitledBorder("Anulados"));

		right.add(rightLeft);
		right.add(rightMid);
		right.add(rightRight);

		this.add(right, BorderLayout.CENTER);

		/* #############
		 * CONFIGURACIONES
		 * #############
		 */

		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		ControladorAplicacion.getInstancia().accion(Acciones.pedidosLista,
				new TransferListaPedidos());
	}

	private void distribuyePedidos(TransferListaPedidos todos,
			TransferListaPedidos proceso, TransferListaPedidos completados,
			TransferListaPedidos anulados) {

		for (int i = 0; i < todos.getLista().size(); i++) {
			if (todos.getLista().get(i).getEstado() == 'P')
				proceso.getLista().add(todos.getLista().get(i));
			else if (todos.getLista().get(i).getEstado() == 'C')
				completados.getLista().add(todos.getLista().get(i));
			else if (todos.getLista().get(i).getEstado() == 'A')
				anulados.getLista().add(todos.getLista().get(i));
		}
	}

	private FocusListener selectorTablas = new FocusListener() {

		@Override
		public void focusGained(FocusEvent e) {
			selectedTable = (JTable) e.getSource();
			if (selectedTable.equals(tableComp))
				seleccionado.setText("Completados");
			else if (selectedTable.equals(tableProc))
				seleccionado.setText("En proceso");
			else if (selectedTable.equals(tableAnul))
				seleccionado.setText("Anulados");
		}

		@Override
		public void focusLost(FocusEvent e) {
		}
	};

	private class TablaListaPedidos extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private TransferListaPedidos pedidos;
		private final String[] colNames = new String[] { "Id", "Fecha",	"Id Proveedor" };


		TablaListaPedidos() {
			pedidos = new TransferListaPedidos();
			pedidos.setLista(new ArrayList<TransferPedido>());
		}

		public void update(TransferListaPedidos pedidos) {
			this.pedidos.setLista(pedidos.getLista());
			this.fireTableDataChanged();
		}

		public TransferPedido getPedido(int pos) {
			return pedidos.getLista().get(pos);
		}

		public int getColumnCount() {
			return colNames.length;
		}

		public int getRowCount() {
			return pedidos.getLista().size();
		}

		@Override
		public String getColumnName(int col) {
			return colNames[col];
		}

		public Object getValueAt(int row, int col) {

			String out = "";

			switch (col) {
			case 0:
				out = "" + pedidos.getLista().get(row).getId();
				break;
			case 1:
				out = pedidos.getLista().get(row).getFecha();
				break;
			case 2:
				out = "" + pedidos.getLista().get(row).getIdProveedor();
				break;
			}

			return out;
		}

	}

	@Override
	public void actualiza(Acciones evento, Retorno datos) {
		if (!transmiteActualiza(evento, datos)) {
			switch (evento) {
			case pedidosLista:
				if (!datos.tieneErrores()) {

					TransferListaPedidos todos = (TransferListaPedidos) datos
							.getDatos();

					TransferListaPedidos proceso = new TransferListaPedidos(), completados = new TransferListaPedidos(), anulados = new TransferListaPedidos();
					proceso.setLista(new ArrayList<TransferPedido>());
					completados.setLista(new ArrayList<TransferPedido>());
					anulados.setLista(new ArrayList<TransferPedido>());

					distribuyePedidos(todos, proceso, completados, anulados);

					this.pedidosComp.update(completados);
					this.pedidosProc.update(proceso);
					this.pedidosAnul.update(anulados);

				} else
					JOptionPane.showMessageDialog(this,
							"Hubo un error al obtener el listado de pedidos",
							"Error", JOptionPane.ERROR_MESSAGE);

				break;
			case pedidosAnular:
				if (datos.tieneErrores()) {
					Iterator<TError> it = datos.getErrores().getLista()
							.iterator();
					String errormsj = "Se produjeron los siguientes errores: \n";
					while (it.hasNext()) {
						TError thisError = it.next();
						switch (thisError.getErrorId()) {
						case Errores.pedidoNoEnProceso:
							errormsj += " - No se anulo el pedido. ¡Solo pueden anularse pedidos en proceso!";
							break;
						case Errores.pedidoNoAnulado:
							errormsj += " - ¡¡NO SE ANULO!!";
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
				} else
					JOptionPane.showMessageDialog(this, "Pedido anulado.",
							"Correcto", JOptionPane.INFORMATION_MESSAGE);

				break;
			case pedidosCompletar:
				if (datos.tieneErrores()) {
					Iterator<TError> it = datos.getErrores().getLista()
							.iterator();
					String errormsj = "Se produjeron los siguientes errores: \n";
					while (it.hasNext()) {
						TError thisError = it.next();
						switch (thisError.getErrorId()) {
						case Errores.productoNoEncontrado:
							errormsj += " - No se completo el pedido porque uno de los productos se borró. Anule el pedido y cree uno nuevo.";
							break;
						case Errores.pedidoNoEnProceso:
							errormsj += " - No se completo el pedido. ¡Solo pueden completarse pedidos en proceso!";
							break;
						case Errores.pedidoNoCompletado:
							errormsj += " - ¡¡NO SE COMPLETO!!";
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
				} else
					JOptionPane.showMessageDialog(this, "Pedido completado.",
							"Correcto", JOptionPane.INFORMATION_MESSAGE);

				break;
			case pedidosBorrar:
				if (datos.tieneErrores()) {
					Iterator<TError> it = datos.getErrores().getLista()
							.iterator();
					String errormsj = "Se produjeron los siguientes errores: \n";
					while (it.hasNext()) {
						TError thisError = it.next();
						switch (thisError.getErrorId()) {
						case Errores.pedidoEnProceso:
							errormsj += " - No se borro el pedido. ¡Solo pueden anularse pedidos anulados o completados!";
							break;
						case Errores.pedidoNoBorrado:
							errormsj += " - ¡¡NO SE BORRO!!";
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
				} else
					JOptionPane.showMessageDialog(this, "Pedido borrado.",
							"Correcto", JOptionPane.INFORMATION_MESSAGE);

				break;
			}
		}
	}

	@Override
	public void alVolver(GUI who) {
		ControladorAplicacion.getInstancia().accion(Acciones.pedidosLista,
				new TransferListaPedidos());
	}

	private TransferPedido getSelectedPedido() {
		TransferPedido pedido = null;
		if (selectedTable != null)
			if (selectedTable.getSelectedRow() != -1) {
				if (selectedTable.equals(tableComp))
					pedido = pedidosComp.getPedido(tableComp.getSelectedRow());
				else if (selectedTable.equals(tableProc))
					pedido = pedidosProc.getPedido(tableProc.getSelectedRow());
				else if (selectedTable.equals(tableAnul))
					pedido = pedidosAnul.getPedido(tableAnul.getSelectedRow());
			}
		return pedido;
	}

	private TransferPedido pideId() {

		TransferPedido pedido = new TransferPedido();

		int idPedido = -1;
		String respuesta = "";
		do
			try {
				respuesta = JOptionPane.showInputDialog(this,
						"Introduce la ID del pedido: ");
				idPedido = Integer.parseInt(respuesta);
			} catch (NumberFormatException nfe) {
				idPedido = -1;
			}
		while (idPedido <= 0 && respuesta != null);

		pedido.setId(idPedido);

		return (idPedido == -1) ? null : pedido;

	}

}
