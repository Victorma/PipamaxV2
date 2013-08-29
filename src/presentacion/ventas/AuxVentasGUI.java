package presentacion.ventas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import constantes.Acciones;
import presentacion.GUI;
import presentacion.formulario.CampoFormularioNumeroEntero;
import presentacion.formulario.CampoFormularioSelector;
import presentacion.formulario.Formulario;
import negocio.Retorno;
import negocio.clientes.TransferCliente;
import negocio.clientes.TransferListaClientes;
import negocio.controlador.ControladorAplicacion;
import negocio.productos.TransferListaProductos;
import negocio.productos.TransferProducto;

public class AuxVentasGUI {
	
	private static TransferCliente selected = null;
	
	public static TransferCliente pideCliente(GUI father){
		selected = null;
		
		new VentanaPideCliente(father);
		
		return selected;
	}
	
	private static class VentanaPideCliente extends GUI{

		private static final long serialVersionUID = 374066996415544947L;
		private TransferListaClientes clientes = null;
		private CampoFormularioSelector<String, TransferCliente> selectorClientes = null;
		
		public VentanaPideCliente(GUI father) {
			super(father,true);

			Formulario formu = new Formulario();
			
			ControladorAplicacion.getInstancia().accion(Acciones.clientesListado, null);
			
			Map<String, TransferCliente> mapaClientes = new HashMap<String, TransferCliente>();
			for(TransferCliente cliente: clientes.getLista())
				mapaClientes.put("(" + cliente.getId() + ") " + cliente.getName() + " " + cliente.getLastName() + " - DNI: " + cliente.getDNI(), cliente);
			
			selectorClientes = new CampoFormularioSelector<String, TransferCliente>("Cliente", mapaClientes);
			formu.addCampo(selectorClientes);
			
			this.setLayout(new BorderLayout());
			this.add(formu, BorderLayout.CENTER);
			
			// botonera
			
			JPanel botonera = new JPanel(new GridLayout(1,2));
			
			JButton enviar = new JButton("Enviar");
			enviar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					envia();
				}
			});
			
			
			JButton cancelar = new JButton("Cancelar");
			cancelar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					cancela();
				}
			});
			
			botonera.add(enviar);
			botonera.add(cancelar);
			
			this.add(botonera, BorderLayout.SOUTH);	
			this.setSize(400, 100);
			this.setLocationRelativeTo(father);
			this.setVisible(true);
		}

		protected void envia() {
			AuxVentasGUI.selected = selectorClientes.getResultado();
			this.dispose();
		}
		
		protected void cancela(){
			this.dispose();
		}

		@Override
		public void actualiza(Acciones evento, Retorno datos) {
			switch (evento) {
			case clientesListado:
				if(!datos.tieneErrores())
					this.clientes = (TransferListaClientes) datos.getDatos();
				break;
			default:break;
			}
		}

		@Override
		public void alVolver(GUI who) {}
		
	}
	
	private static Integer cantidad = null;

	public static Integer pideCantidad(GUI father, Integer maxCantidad) {
		cantidad = null;
		
		new VentanaPideCantidad(father, maxCantidad);
		
		return cantidad;
	}
	
	private static class VentanaPideCantidad extends GUI{

		private static final long serialVersionUID = 1L;
		private CampoFormularioNumeroEntero campoCantidad;
		private Formulario formu;
		
		public VentanaPideCantidad(GUI father, Integer max) {
			super(father,true);

			
			formu = new Formulario();
			
			campoCantidad = new CampoFormularioNumeroEntero("Cantidad",0,0,max);
			formu.addCampo(campoCantidad);
			
			this.setLayout(new BorderLayout());
			this.add(formu, BorderLayout.CENTER);
			
			// botonera
			
			JPanel botonera = new JPanel(new GridLayout(1,2));
			
			JButton enviar = new JButton("Enviar");
			enviar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					envia();
				}
			});
			
			
			JButton cancelar = new JButton("Cancelar");
			cancelar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					cancela();
				}
			});
			
			botonera.add(enviar);
			botonera.add(cancelar);
			
			this.add(botonera, BorderLayout.SOUTH);		
			this.setSize(400, 100);
			this.setLocationRelativeTo(father);
			this.setVisible(true);
		}

		protected void envia() {
			if(formu.esCorrecto()){
				AuxVentasGUI.cantidad = campoCantidad.getResultado();
				this.dispose();
			}
		}
		
		protected void cancela(){
			this.dispose();
		}

		@Override
		public void actualiza(Acciones evento, Retorno datos) {}

		@Override
		public void alVolver(GUI who) {}
		
	}

	private static TransferProducto producto = null;
	
	public static TransferProducto pideProducto(GUI father) {
		producto = null;
		
		new VentanaPideProducto(father);
		
		return producto;
	}

	private static class VentanaPideProducto extends GUI{

		private static final long serialVersionUID = -2952295314096934988L;
		private TransferListaProductos productos = null;
		private CampoFormularioSelector<String, TransferProducto> selectorProductos = null;
		
		public VentanaPideProducto(GUI father) {
			super(father,true);

			Formulario formu = new Formulario();
			
			ControladorAplicacion.getInstancia().accion(Acciones.productosListado, null);
			
			Map<String, TransferProducto> mapaClientes = new HashMap<String, TransferProducto>();
			for(TransferProducto producto: productos.getList())
				mapaClientes.put("(" + producto.getId() + ") " + producto.getNombre(), producto);
			
			selectorProductos = new CampoFormularioSelector<String, TransferProducto>("Producto", mapaClientes);
			formu.addCampo(selectorProductos);
			
			this.setLayout(new BorderLayout());
			this.add(formu, BorderLayout.CENTER);
			
			// botonera
			
			JPanel botonera = new JPanel(new GridLayout(1,2));
			
			JButton enviar = new JButton("Enviar");
			enviar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					envia();
				}
			});
			
			
			JButton cancelar = new JButton("Cancelar");
			cancelar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					cancela();
				}
			});
			
			botonera.add(enviar);
			botonera.add(cancelar);
			
			this.add(botonera, BorderLayout.SOUTH);		
			this.setSize(400, 100);
			this.setLocationRelativeTo(father);
			this.setVisible(true);
		}

		protected void envia() {
			AuxVentasGUI.producto = selectorProductos.getResultado();
			this.dispose();
		}
		
		protected void cancela(){
			this.dispose();
		}

		@Override
		public void actualiza(Acciones evento, Retorno datos) {
			switch (evento) {
			case productosListado:
				if(!datos.tieneErrores())
					this.productos = (TransferListaProductos) datos.getDatos();
				break;
			default:break;
			}
		}

		@Override
		public void alVolver(GUI who) {}
		
	}
}
