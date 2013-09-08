package presentacion.productos;

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

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

import presentacion.GUI;
import presentacion.util.Operaciones;
import negocio.controlador.ControladorAplicacion;
import negocio.Retorno;
import negocio.TError;
import negocio.productos.TransferListaProductos;

public class ProductosGUI extends GUI {
	private static final long serialVersionUID = 1732982881027998497L;

	//frames and panels
	private JPanel left, right , top;

	//buttons
	private JButton botonCrear, botonBorrar, botonEditar, botonConsultar,
			botonVolver;

	//table and model
	private TablaProductos tabla;

	public ProductosGUI(GUI father) {
		super(father);
		//create the frame
		this.setLayout(new BorderLayout());

		//set the left part of the window as a panel
		left = new JPanel();
		left.setBorder(BorderFactory.createTitledBorder("Acciones"));

		//set de dimensions of the left part
		left.setLayout(new BorderLayout());
		left.setPreferredSize(new Dimension(150, 0));

		//Botones
		JPanel leftTop = new JPanel(new FlowLayout());
		botonCrear = new JButton("Crear");
		botonCrear.setPreferredSize(new Dimension(130, 30));
		leftTop.add(botonCrear);
		botonCrear.addActionListener(new CrearListener());//add CrearListener

		botonConsultar = new JButton("Consultar");
		botonConsultar.setPreferredSize(new Dimension(130, 30));
		leftTop.add(botonConsultar);
		botonConsultar.addActionListener(new ConsultarListener());//add ConsultarListener

		botonEditar = new JButton("Editar");
		botonEditar.setPreferredSize(new Dimension(130, 30));
		leftTop.add(botonEditar);
		botonEditar.addActionListener(new EditarListener());//add EditarListener

		botonBorrar = new JButton("Borrar");
		botonBorrar.setPreferredSize(new Dimension(130, 30));
		leftTop.add(botonBorrar);
		botonBorrar.addActionListener(new BorrarListener());//add VolverListener

		left.add(leftTop, BorderLayout.CENTER);

		botonVolver = new JButton("Volver");
		botonVolver.setPreferredSize(new Dimension(130, 30));
		left.add(botonVolver, BorderLayout.SOUTH);
		botonVolver.addActionListener(new VolverListener());//add VolverListener

		right = new JPanel(new GridLayout());
		right.setBorder(BorderFactory.createTitledBorder("Lista"));

		//add the scrollPanel to the right 
		tabla = new TablaProductos();
		right.add(tabla.getPane());
		
		//top
		top = new JPanel(new GridLayout());
		top.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createDashedBorder(Color.BLUE, 5, 4),
				"Productos m�s vendidos"));
		
		this.add(top, BorderLayout.NORTH);

		//set the window title
		this.add(left, BorderLayout.WEST);
		this.add(right, BorderLayout.CENTER);
		this.setTitle("PIPAMAX - PRODUCTOS");

		//set the configurations of the window
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		//TODO Comprobar que sea necesario el new aqui
		this.alVolver(null);
		this.setVisible(true);

	}

	private int pideId() {
		int id = tabla.getSelectedId();
		boolean cancelar = false;

		while (id == -1 && !cancelar) {
			try {
				String dialogo = JOptionPane.showInputDialog(ProductosGUI.this,
						"Introduce ID");
				cancelar = (dialogo == null);
				if (!cancelar)
					id = Integer.parseInt(dialogo);
			} catch (NumberFormatException ex) {
				id = -1;
			}
		}
		return id;
	}

	/*
	 * ---------------Listeners---------------
	 */

	class CrearListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			new CrearProductoGUI(ProductosGUI.this);
		}

	}

	class ConsultarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int id = pideId();

			if (id != -1) {
				new ConsultarProductosGUI(ProductosGUI.this, id, false);
			}
		}

	}

	class EditarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int id = pideId();

			if (id != -1)
				new EditarProductoGUI(ProductosGUI.this, id);

		}

	}

	class BorrarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int id = pideId();

			if (id != -1) {
				new ConsultarProductosGUI(ProductosGUI.this, id, true);
			}
		}

	}

	class VolverListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}

	}

	@Override
	public void actualiza(Acciones evento, Retorno datos) {

		if (!transmiteActualiza(evento, datos))
			switch (evento) {

			case productosListado:
				if (datos.tieneErrores()) {

					Iterator<TError> it = datos.getErrores().getLista()
							.iterator();
					TError current = null;

					while (it.hasNext()) {

						current = it.next();
						switch (current.getErrorId()) {
						case Errores.errorDeAcceso:
							JOptionPane.showMessageDialog(this,
									"Error actualizando la base de datos.");
							break;
						default: {
						}
						}
					}
				} else {
					TransferListaProductos productos = (TransferListaProductos) datos
							.getDatos();
					tabla.updateTable(productos);
				}

				break;
				
			case productosMasVendidos:
				if (datos.tieneErrores()) {

					Iterator<TError> it = datos.getErrores().getLista().iterator();
					TError current = null;

					while (it.hasNext()) {
						current = it.next();
						switch (current.getErrorId()) {
						case Errores.errorDeAcceso:
							JOptionPane.showMessageDialog(this,
									"Error al accedes a los productos mas vendidos.");
							break;
						default: {}
						}
					}
				} else {
					List<Object[]> lista = (List<Object[]>) datos.getDatos();
					top.removeAll();
					if (lista.isEmpty()) {
						top.setLayout(new GridLayout());
						JLabel textoTop = new JLabel(" -- No hay productos vendidos --");
						textoTop.setSize(0, 50);
						top.add(textoTop);
					}else{
						top.setLayout(new GridLayout(lista.size(),1));
						for(Object[] mes: lista){
							JLabel labelmes = new JLabel("Producto: " + mes[0] + " Cantidad: "+ mes[1] +" Beneficio: "+ Operaciones.round((double)mes[2], 2)+" �");
							labelmes.setSize(0, 50);
							top.add(labelmes);
						}
					}
					this.setVisible(true);
				}

				break;
			}

	}

	@Override
	public void alVolver(GUI who) {
		ControladorAplicacion.getInstancia().accion(Acciones.productosListado,
				new TransferListaProductos());
		ControladorAplicacion.getInstancia().accion(Acciones.productosMasVendidos,
				null);
	}
}
