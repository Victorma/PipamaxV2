package presentacion;

import constantes.Acciones;
import constantes.Errores;

/*
 * ----------
 * External libraries
 * ----------
 */

import integracion.DAOException;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.*;

import negocio.Retorno;
import negocio.TError;
import negocio.controlador.CommandRegenerar;
import negocio.controlador.ControladorAplicacion;
import presentacion.clientes.ClientesGUI;
import presentacion.departamentos.DepartamentosGUI;
import presentacion.empleados.EmpleadosGUI;
import presentacion.marcas.MarcasGUI;
import presentacion.pedidos.PedidosGUI;
import presentacion.productos.ProductosGUI;
import presentacion.proveedores.ProveedoresGUI;
import presentacion.proyectos.ProyectosGUI;
import presentacion.ventas.VentasGUI;

public class PrincipalGUI extends GUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3540207658392862002L;
	//buttons
	private JButton botonClientes;
	private JButton botonProveedores;
	private JButton botonMarcas;
	private JButton botonProductos;
	private JButton botonPedidos;
	private JButton botonVentas;
	private JButton botonSalir;
	private JButton botonEmpleados;
	private JButton botonDepartamentos;
	private JButton botonProyectos;

	public PrincipalGUI(GUI father) {
		super(father);

		this.setLayout(new BorderLayout());

		JPanel titulo = new JPanel(new FlowLayout());
		titulo.setPreferredSize(new Dimension(0, 140));
		JLabel siglas = new JLabel("- P.I.P.A.M.A.X - (v2.0)");
		siglas.setFont(new Font("Dialog", Font.BOLD, 72));
		JLabel nombrecomp = new JLabel(
				"-Productos Informáticos Potenciados Al MÁXIMO-");
		nombrecomp.setFont(new Font("Dialog", Font.ITALIC, 12));

		titulo.add(siglas);
		titulo.add(nombrecomp);
		JPanel superCentro = new JPanel(new GridLayout(2, 1, 10, 10));
		JPanel centro = new JPanel(new GridLayout(1, 6, 10, 10));
		botonClientes = new JButton("Clientes");
		centro.add(botonClientes);
		botonClientes.addActionListener(new ListenerClientes());//add CrearListener

		botonProveedores = new JButton("Proveedores");
		centro.add(botonProveedores);
		botonProveedores.addActionListener(new ListenerProveedores());//add ConsultarListener

		botonMarcas = new JButton("Marcas");
		centro.add(botonMarcas);
		botonMarcas.addActionListener(new ListenerMarcas());//add EditarListener

		botonProductos = new JButton("Productos");
		centro.add(botonProductos);
		botonProductos.addActionListener(new ListenerProductos());//add EditarListener

		botonPedidos = new JButton("Pedidos");
		centro.add(botonPedidos);
		botonPedidos.addActionListener(new ListenerPedidos());//add VolverListener

		botonVentas = new JButton("Ventas");
		centro.add(botonVentas);
		botonVentas.addActionListener(new ListenerVentas());//add VolverListener

		botonSalir = new JButton("Salir");
		centro.add(botonSalir);
		botonSalir.addActionListener(new ListenerSalir());//add VolverListener
		superCentro.add(centro);
		JPanel nuevosBotones = new JPanel(new GridLayout(1, 3, 10, 10));
		botonEmpleados = new JButton("Empleados");
		nuevosBotones.add(botonEmpleados);
		botonEmpleados.addActionListener(new ListenerEmpleados());//add CrearListener

		botonDepartamentos = new JButton("Departamentos");
		nuevosBotones.add(botonDepartamentos);
		botonDepartamentos.addActionListener(new ListenerDepartamentos());//add CrearListener

		botonProyectos = new JButton("Proyectos");
		nuevosBotones.add(botonProyectos);
		botonProyectos.addActionListener(new ListenerProyectos());//add CrearListener	

		JButton botonRegenerar = new JButton("Regenerar");
		nuevosBotones.add(botonRegenerar);
		botonRegenerar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ControladorAplicacion.getInstancia().accion(Acciones.regenerar, null);
			}
		});//add CrearListener

		superCentro.add(nuevosBotones);

		JPanel nombres = new JPanel(new FlowLayout());
		nombres.setPreferredSize(new Dimension(0, 60));
		JPanel nombresTop = new JPanel(new GridLayout(2, 4));
		nombresTop.add(new JLabel("Ismael Fernandez Fuentes   "));
		nombresTop.add(new JLabel("Roberto de Miguel López "));
		nombresTop.add(new JLabel("Rubén Moreira López "));
		nombresTop.add(new JLabel("Daniel Novillo Villarejo "));
		nombresTop.add(new JLabel("Iván J. Pérez Colado "));
		nombresTop.add(new JLabel("Víctor M. Pérez Colado "));
		nombresTop.add(new JLabel("Carlos Ruiz Martín "));
		nombresTop.add(new JLabel());
		nombres.add(nombresTop);
		nombres.add(new JLabel(
				"© 2012-2013 Universidad Complutense de Madrid - Ingeniería de Software - PIPAMAX"));

		//set the window title
		this.setTitle("PIPAMAX");
		this.add(titulo, BorderLayout.NORTH);
		this.add(superCentro, BorderLayout.CENTER);
		this.add(nombres, BorderLayout.SOUTH);

		//set the configurations of the window
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 500);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/*
	 * ---------------Listeners---------------
	 */

	private class ListenerClientes implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new ClientesGUI(PrincipalGUI.this);
		}
	}

	private class ListenerProveedores implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new ProveedoresGUI(PrincipalGUI.this);
		}
	}

	private class ListenerMarcas implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new MarcasGUI(PrincipalGUI.this);
		}
	}

	private class ListenerProductos implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new ProductosGUI(PrincipalGUI.this);
		}
	}

	private class ListenerPedidos implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new PedidosGUI(PrincipalGUI.this);
		}
	}

	private class ListenerVentas implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new VentasGUI(PrincipalGUI.this);
		}
	}

	private class ListenerEmpleados implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new EmpleadosGUI(PrincipalGUI.this);
		}
	}

	private class ListenerDepartamentos implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new DepartamentosGUI(PrincipalGUI.this);
		}
	}

	private class ListenerProyectos implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new ProyectosGUI(PrincipalGUI.this);
		}
	}

	private class ListenerSalir implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	/*
	 * ------------- Actualizar ------------
	 */

	@Override
	public void actualiza(Acciones evento, Retorno datos) {
		if (datos.tieneErrores()) {
			Iterator<TError> it = datos.getErrores().getLista().iterator();
			TError current = null;
			while (it.hasNext()) {
				current = it.next();
				switch (current.getErrorId()) {
				case Errores.errorDeAcceso:
					JOptionPane.showMessageDialog(this,
							((DAOException) current.getDatos()).getMessage(),
							"Error de acceso", JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
		}
		transmiteActualiza(evento, datos);
	}

	@Override
	public void alVolver(GUI who) {
	}

}
