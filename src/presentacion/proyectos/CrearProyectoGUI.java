package presentacion.proyectos;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import constantes.Acciones;
import negocio.Retorno;
import negocio.TError;
import negocio.empleados.Empleado;
import negocio.proyectos.Proyecto;
import presentacion.GUI;
import negocio.controlador.ControladorAplicacion;
import presentacion.empleados.tabla.TablaEmpleados;
import presentacion.formulario.CampoFormularioSelector;
import presentacion.formulario.CampoFormularioTexto;
import presentacion.formulario.Formulario;

public class CrearProyectoGUI extends GUI {

	private static final long serialVersionUID = 5010767342268183606L;
	private CampoFormularioTexto nombre;
	private CampoFormularioTexto descripcion;
	private Formulario formulario;
	
	private Map<String,Empleado> empMap;
	private TablaEmpleados tablaEmpleadosModel;
	private JTable tablaEmpleados;
	private Set<Empleado> empleadosProyecto;
	
	private CampoFormularioSelector<String,Empleado> selectorEmp;
	private Formulario formularioEmpleados;
	
	public CrearProyectoGUI(GUI father) {
		super(father);
		this.setLayout(new BorderLayout());
		this.setTitle("Alta proyecto");
		
		JPanel panelCentral = new JPanel(new BorderLayout());
		//###############
		// Formulario
		//###############
		
		formulario = new Formulario();
		nombre = new CampoFormularioTexto("Nombre","");
		descripcion = new CampoFormularioTexto("Descripcion","");
		
		formulario.addCampo(nombre);
		formulario.addCampo(descripcion);
		
		panelCentral.add(formulario,BorderLayout.NORTH);
		
		//###############
		// Selector Empleados
		//###############
		
		JPanel panelFormularioEmpleados = new JPanel(new BorderLayout());
		JPanel panelTablaFormuEmpleados = new JPanel(new BorderLayout());
		empMap = new TreeMap<String,Empleado>();
		selectorEmp = new CampoFormularioSelector<String,Empleado>("Empleado",empMap);
		formularioEmpleados = new Formulario();
		formularioEmpleados.addCampo(selectorEmp);
		
		JButton aniadir = new JButton("Añadir");
		aniadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(formularioEmpleados.esCorrecto()){
					Empleado e = selectorEmp.getResultado();
					empMap.remove(e.getNombreCompleto());
					selectorEmp.setMap(empMap);
					empleadosProyecto.add(e);
					tablaEmpleadosModel.update(empleadosProyecto);
				}
			}
		});
		
		JButton quitar = new JButton("Quitar");
		quitar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Empleado e = getSelectedEmpleado();
				if(e!=null){
					empMap.put(e.getNombreCompleto(),e);
					selectorEmp.setMap(empMap);
					empleadosProyecto.remove(e);
					tablaEmpleadosModel.update(empleadosProyecto);
				}
			}
		});
		panelFormularioEmpleados.add(selectorEmp,BorderLayout.CENTER);
		panelFormularioEmpleados.add(aniadir,BorderLayout.WEST);
		panelFormularioEmpleados.add(quitar,BorderLayout.EAST);
		
		panelTablaFormuEmpleados.add(panelFormularioEmpleados,BorderLayout.NORTH);
		tablaEmpleadosModel = new TablaEmpleados();
		tablaEmpleados = new JTable(tablaEmpleadosModel);
		panelTablaFormuEmpleados.add(new JScrollPane(tablaEmpleados),BorderLayout.CENTER);
		panelCentral.add(panelTablaFormuEmpleados,BorderLayout.CENTER);
		
		this.add(panelCentral,BorderLayout.CENTER);
		
		//###############
		// Botones
		//###############		
		
		JPanel botonera = new JPanel(new GridLayout(1,2));
		
		JButton enviar,cancelar;
		enviar = new JButton("Enviar");
		enviar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				formulario.marcaErrores();
				if(formulario.esCorrecto()){
					Proyecto pro = new Proyecto();
					pro.setNombre(nombre.getResultado());
					pro.setDescripcion(descripcion.getResultado());
					pro.setEmpleado(empleadosProyecto);
					ControladorAplicacion.getInstancia().accion(Acciones.proyectosCrear, pro);
				}
			}
		});
		botonera.add(enviar);
		
		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CrearProyectoGUI.this.dispose();
			}
		});
		botonera.add(cancelar);
		this.add(botonera,BorderLayout.SOUTH);
		
		this.empleadosProyecto = new HashSet<Empleado>();
		ControladorAplicacion.getInstancia().accion(Acciones.empleadosListado, null);
		
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}
	
	private Empleado getSelectedEmpleado(){
		return( (tablaEmpleados.getSelectedRow() != -1)? tablaEmpleadosModel.getEmpleado(tablaEmpleados.getSelectedRow()):null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualiza(Acciones evento, Retorno datos) {
		if(evento == Acciones.proyectosCrear)
			if(datos.tieneErrores()){
				StringBuilder errores = new StringBuilder("Se produjeron los siguientes errores: \n");
				for(TError error: datos.getErrores().getLista()){
					errores.append("  - ");
					switch(error.getErrorId()){
					default: errores.append(error.getDatos().toString()); break;				
					}
					errores.append("\n");
				}
				JOptionPane.showMessageDialog(this, errores.toString(), "Error", JOptionPane.ERROR_MESSAGE);
			}else{
				JOptionPane.showMessageDialog(this, "Proyecto dado de alta con el id " + datos.getDatos(), "Correcto", JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
			}
		else if(evento == Acciones.empleadosListado)
			if(datos.tieneErrores()){
				StringBuilder errores = new StringBuilder("Se produjeron los siguientes errores: \n");
				for(TError error: datos.getErrores().getLista()){
					errores.append("  - ");
					switch(error.getErrorId()){
					default: errores.append(error.getDatos().toString()); break;				
					}
					errores.append("\n");
				}
				JOptionPane.showMessageDialog(this, errores.toString(), "Error", JOptionPane.ERROR_MESSAGE);
			}else{
				for(Empleado e:(Collection<Empleado>)datos.getDatos())
					empMap.put(e.getNombreCompleto(), e);
				selectorEmp.setMap(empMap);
			}
	}

	@Override
	public void alVolver(GUI who) {}

}
