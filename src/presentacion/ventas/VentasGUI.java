package presentacion.ventas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import presentacion.GUI;
import negocio.controlador.ControladorAplicacion;

import negocio.Retorno;
import negocio.ventas.TransferListaVentas;
import negocio.ventas.TransferVenta;

import constantes.Acciones;

public class VentasGUI extends GUI {

	private static final long serialVersionUID = 1L;
	private JTable tablaVentas;
	private TablaVentas tablaVentasModel;

	public VentasGUI(GUI father) {
		super(father);
		this.setLayout(new BorderLayout());
		this.setTitle("PIPAMAX - VENTAS");

		JPanel top = new JPanel(new FlowLayout());
		top.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createDashedBorder(Color.RED, 5, 4),
				"Estadisticas"));
		JLabel textoTop = new JLabel("Ventas");
		textoTop.setSize(0, 50);
		top.add(textoTop);
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
		JButton abrir, consultar, devolucion, borrar, salir;

		abrir = new JButton("Nueva Venta");
		abrir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				new AdminVentaGUI(VentasGUI.this, null);
			}
		});
		abrir.setPreferredSize(new Dimension(130, 30));
		leftTop.add(abrir);

		consultar = new JButton("Consultar");
		consultar.setPreferredSize(new Dimension(130, 30));
		consultar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TransferVenta venta = getSelectedVenta();
				if (venta == null)
					venta = pideId();
				if (venta != null)
					new ConsultarVentaGUI(VentasGUI.this, venta);

			}
		});
		leftTop.add(consultar);

		devolucion = new JButton("Modificar venta / Devolución");
		devolucion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TransferVenta venta = getSelectedVenta();
				if (venta == null)
					venta = pideId();
				if (venta != null)
					new AdminVentaGUI(VentasGUI.this, venta);

			}
		});
		devolucion.setPreferredSize(new Dimension(130, 30));
		leftTop.add(devolucion);

		borrar = new JButton("Borrar");
		borrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				TransferVenta venta = getSelectedVenta();
				if (venta == null)
					venta = pideId();
				if (venta != null) {
					GUI consultar = new ConsultarVentaGUI(VentasGUI.this, venta);
					if (getChild() != null) {
						int respuesta = JOptionPane.showConfirmDialog(
								consultar, "¿Desea borrar esta venta?",
								"Borrar", JOptionPane.YES_NO_OPTION);
						consultar.dispose();
						if (respuesta == JOptionPane.YES_OPTION) {
							ControladorAplicacion.getInstancia().accion(
									Acciones.ventasBorrar, venta);
							ControladorAplicacion.getInstancia().accion(
									Acciones.ventasListado,
									new TransferListaVentas());
						}
					}
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
		 * PANEL VENTAS
		 * #############
		 */

		JPanel right = new JPanel(new GridLayout());
		right.setBorder(BorderFactory.createTitledBorder("Ventas"));

		tablaVentasModel = new TablaVentas();
		tablaVentas = new JTable(tablaVentasModel);

		right.add(new JScrollPane(tablaVentas));

		this.add(right, BorderLayout.CENTER);

		/* #############
		 * CONFIGURACIONES
		 * #############
		 */

		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		ControladorAplicacion.getInstancia().accion(Acciones.ventasListado,
				new TransferListaVentas());
	}

	private class TablaVentas extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private TransferListaVentas ventas;
		private final String[] colNames = new String[] { "Id", "Fecha",
				"Id Cliente", "Estado" };


		TablaVentas() {
			ventas = new TransferListaVentas();
			ventas.setListaVentas(new ArrayList<TransferVenta>());
		}

		public void update(TransferListaVentas ventas) {
			this.ventas.setListaVentas(ventas.getListaVentas());
			this.fireTableDataChanged();
		}

		public TransferVenta getVenta(int pos) {
			return ventas.getListaVentas().get(pos);
		}

		public int getColumnCount() {
			return colNames.length;
		}

		public int getRowCount() {
			return ventas.getListaVentas().size();
		}

		@Override
		public String getColumnName(int col) {
			return colNames[col];
		}

		public Object getValueAt(int row, int col) {

			String out = "";

			switch (col) {
			case 0:
				out = "" + ventas.getListaVentas().get(row).getId();
				break;
			case 1:
				out = ventas.getListaVentas().get(row).getFecha();
				break;
			case 2:
				out = "" + ventas.getListaVentas().get(row).getIdCliente();
				break;
			case 3:
				if(ventas.getListaVentas().get(row).isCerrada()){
					out = "Cerrada";
				}else{
					out = "Abierta";
					
				}
			}

			return out;
		}

	}

	@Override
	public void actualiza(Acciones evento, Retorno retorno) {

		if (!transmiteActualiza(evento, retorno)) {
			if (evento == Acciones.ventasListado)
				if (retorno.tieneErrores())
					JOptionPane.showMessageDialog(this,
							"Hubo un error al obtener el listado de ventas",
							"Error", JOptionPane.ERROR_MESSAGE);
				else
					tablaVentasModel.update((TransferListaVentas) retorno
							.getDatos());
			else if (evento == Acciones.ventasBorrar)
				if (evento == Acciones.ventasListado)
					if (retorno.tieneErrores())
						JOptionPane.showMessageDialog(this,
								"Hubo un error al borrar la venta", "Error",
								JOptionPane.ERROR_MESSAGE);

		}
	}

	@Override
	public void alVolver(GUI who) {

		ControladorAplicacion.getInstancia().accion(Acciones.ventasListado,
				new TransferListaVentas());

	}

	private TransferVenta getSelectedVenta() {
		return ((tablaVentas.getSelectedRow() != -1) ? tablaVentasModel
				.getVenta(tablaVentas.getSelectedRow()) : null);
	}

	private TransferVenta pideId() {

		TransferVenta tventa = new TransferVenta();

		int idVenta = -1;
		String respuesta = "";
		do
			try {
				respuesta = JOptionPane.showInputDialog(this,
						"Introduce la ID de la venta: ");
				idVenta = Integer.parseInt(respuesta);
			} catch (NumberFormatException nfe) {
				idVenta = -1;
			}
		while (idVenta <= 0 && respuesta != null);

		tventa.setId(idVenta);

		return (idVenta == -1) ? null : tventa;

	}

}
