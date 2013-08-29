package presentacion.ventas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import negocio.Retorno;
import negocio.TError;
import negocio.clientes.TransferCliente;
import negocio.controlador.ControladorAplicacion;
import negocio.productos.TComProducto;
import negocio.productos.TransferProducto;
import negocio.ventas.TComVenta;
import negocio.ventas.TransferVenta;
import constantes.Acciones;
import presentacion.GUI;

public class AdminVentaGUI extends GUI {

	private static final long serialVersionUID = 1992882018450386631L;
	private TComVenta venta;
	private JTable tablaVenta;
	private TablaVenta modeloTablaVenta;
	private TransferProducto tmpProducto;
	private Integer tmpCantidad;
	
	public AdminVentaGUI(GUI father, TransferVenta venta) {
		super(father);
		
		this.venta = new TComVenta();
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
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
		
		if(venta == null){
			TransferCliente cliente = AuxVentasGUI.pideCliente(this);
			if(cliente == null)
				this.dispose();
			else{
				this.venta.setCliente(cliente);
				ControladorAplicacion.getInstancia().accion(Acciones.ventasAbrir, cliente);
			}
		}else
			ControladorAplicacion.getInstancia().accion(Acciones.ventasConsultar, venta);
		


		JPanel top = new JPanel(new FlowLayout());
		top.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createDashedBorder(Color.black, 5, 4), "Cliente"));
		JLabel textoTop = new JLabel("Cliente: " + this.venta.getCliente());
		top.add(textoTop);

		this.add(top, BorderLayout.NORTH);


		/* #############
		 * PANEL BOTONES
		 * #############
		 */
		JPanel left = new JPanel(new FlowLayout());
		left.setBorder(BorderFactory.createTitledBorder("Menu"));
		
		JPanel bot;
		JButton salir = new JButton("Cancelar");
		salir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		if(this.venta.getVenta().isCerrada()){
			
			bot = new JPanel(new GridLayout());
			
			JButton devolver  = new JButton("Devolver");
			devolver.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if (tablaVenta.getSelectedRow() != -1) {
						int maxCantidad = AdminVentaGUI.this.venta.getVenta().getLineaVenta(tablaVenta.getSelectedRow()).getCantidad();
						Integer cantidad = AuxVentasGUI.pideCantidad(AdminVentaGUI.this, maxCantidad);
						
						if (cantidad > 0) {
							tmpCantidad = cantidad;
							ControladorAplicacion.getInstancia().accion(Acciones.ventasDevolucion, new Object[]{
									AdminVentaGUI.this.venta.getVenta(),
									AdminVentaGUI.this.venta.getProductos().get(tablaVenta.getSelectedRow()),
									cantidad
							});
						}
					}					
				}
			});
			
		}else{
		
			JButton agregar, quitar, cerrar;
	
			agregar = new JButton("Añadir");
			agregar.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent e) {
					
					TransferProducto producto = AuxVentasGUI.pideProducto(AdminVentaGUI.this);
					
					if(producto != null){
						Integer cantidad = AuxVentasGUI.pideCantidad(AdminVentaGUI.this, null);
						if(cantidad != null){
							tmpProducto = producto;
							tmpCantidad = cantidad;
							ControladorAplicacion.getInstancia().accion(Acciones.ventasAgregarProducto, 
									new Object[]{AdminVentaGUI.this.venta.getVenta(),
												producto,
												cantidad										
									});
						}
					}
				}
	
			});
			agregar.setPreferredSize(new Dimension(80, 30));
			quitar = new JButton("Quitar");
			quitar.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent e) {
					if (tablaVenta.getSelectedRow() != -1) {
						
						ControladorAplicacion.getInstancia().accion(Acciones.ventasQuitarProducto, 
								new Object[]{AdminVentaGUI.this.venta.getVenta(),
									AdminVentaGUI.this.venta.getProductos().get(tablaVenta.getSelectedRow()).getProducto()									
								});
					}
				}
			});
	
			quitar.setPreferredSize(new Dimension(80, 30));
	
			cerrar = new JButton("Cerrar venta");
			cerrar.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent e) {
	
					if (AdminVentaGUI.this.venta.getVenta().getNumLineasVenta() != 0)
						ControladorAplicacion.getInstancia().accion(
								Acciones.ventasCerrar, AdminVentaGUI.this.venta.getVenta());
					
				}
	
			});
	
			left.add(agregar);
			left.add(quitar);
	
			left.setPreferredSize(new Dimension(100, 0));
			this.add(left, BorderLayout.WEST);
	
			bot = new JPanel(new GridLayout(1, 2));
			bot.add(cerrar);
			
		}
		
		bot.add(salir);
		this.add(bot, BorderLayout.SOUTH);

		//set the configurations of the window
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}
	

	private class TablaVenta extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private TComVenta venta;
		private final String[] colNames = new String[] { "Id", "Producto", "Cantidad", "Precio", "Sub" };

		public TablaVenta(){
			venta = new TComVenta();
			venta.setVenta(new TransferVenta());
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

			Object out = 0;

			switch (col) {
			case 0:
				out = venta.getVenta().getLineaVenta(row).getIdProducto();
				break;
			case 1:
				out = venta.getProductos().get(row).getProducto().getNombre();
				break;
			case 2:
				out = venta.getVenta().getLineaVenta(row).getCantidad();
				break;
			case 3:
				out = venta.getVenta().getLineaVenta(row).getPrecio();
				break;
			case 4:
				out = venta.getVenta().getLineaVenta(row).getCantidad() * venta.getVenta().getLineaVenta(row).getPrecio();
				break;
			}

			return out;
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}
	}
	

	@Override
	public void actualiza(Acciones evento, Retorno datos) {
		if(!transmiteActualiza(evento, datos)){
			switch (evento) {
			case ventasAbrir:
				if(!datos.tieneErrores()){
					venta.setVenta(new TransferVenta());
					venta.getVenta().setId((int) datos.getDatos());
					venta.getVenta().setCerrada(false);
				}else
					for(TError error: datos.getErrores().getLista()){
						JOptionPane.showMessageDialog(this, error.getErrorId());
					}
				break;
			
			case ventasConsultar: 
				if(!datos.tieneErrores()){
					this.venta = (TComVenta) datos.getDatos();
					this.modeloTablaVenta.update(venta);
				}break;
			case ventasCerrar:
				if(!datos.tieneErrores()){
					double total = 0;
					for(int i = 0; i<venta.getVenta().getNumLineasVenta(); i++)
						total += venta.getVenta().getLineaVenta(i).getPrecio() * venta.getVenta().getLineaVenta(i).getCantidad();
					double totalConDescuento = total*venta.getVenta().getDescuento();
					
					JOptionPane.showMessageDialog(this, "Venta cerrada correctamente. \n Total a pagar: " + totalConDescuento +" € (Sin descuento: " + total + " €)");
					this.dispose();
				}
				break;
			
			case ventasAgregarProducto:
				if(!datos.tieneErrores()){
					venta.getVenta().addLineaVenta(tmpProducto.getId(), tmpCantidad, tmpProducto.getPrecio(), AdminVentaGUI.this.venta.getVenta().getId());
					TComProducto tcomprod = new TComProducto();
					tcomprod.setProducto(tmpProducto);
					venta.getProductos().add(tcomprod);
					this.modeloTablaVenta.update(venta);
				}
				break;
			case ventasQuitarProducto:
				if(!datos.tieneErrores()){
					venta.getVenta().removeLineaVenta(tablaVenta.getSelectedRow());
					venta.getProductos().remove(tablaVenta.getSelectedRow());
					modeloTablaVenta.update(AdminVentaGUI.this.venta);
				}
				break;
			case ventasDevolucion:
				if(!datos.tieneErrores()){
					int maxCantidad = venta.getVenta().getLineaVenta(tablaVenta.getSelectedRow()).getCantidad();
					if(tmpCantidad == maxCantidad){
						venta.getVenta().removeLineaVenta(tablaVenta.getSelectedRow());
						venta.getProductos().remove(tablaVenta.getSelectedRow());
					}else{
						venta.getVenta().getLineaVenta(tablaVenta.getSelectedRow()).setCantidad(maxCantidad - tmpCantidad);
					}						
					modeloTablaVenta.update(AdminVentaGUI.this.venta);
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void alVolver(GUI who) {}

}
