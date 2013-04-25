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
import presentacion.controlador.ControladorFrontal;
import presentacion.empleados.tabla.TablaEmpleados;
import presentacion.formulario.CampoFormularioNumeroEntero;
import presentacion.formulario.CampoFormularioSelector;
import presentacion.formulario.CampoFormularioTexto;
import presentacion.formulario.Formulario;

public class EditarProyectoGUI extends GUI {

	private static final long serialVersionUID = 5010767342268183606L;
	private CampoFormularioTexto nombre;
	private CampoFormularioTexto descripcion;
	private CampoFormularioNumeroEntero id;
	private Formulario formulario;
	private Formulario formularioId;
	
	private Map<String,Empleado> empMap;
	private TablaEmpleados tablaEmpleadosModel;
	private JTable tablaEmpleados;
	private Set<Empleado> empleadosProyecto;
	
	private CampoFormularioSelector<String,Empleado> selectorEmp;
	private Formulario formularioEmpleados;
	
	private Proyecto proyecto;
	
	public EditarProyectoGUI(GUI father, Proyecto pry) {
		super(father);
		
		this.setLayout(new BorderLayout());
		this.setTitle("Editar proyecto");
		
		JPanel panelCentral = new JPanel(new BorderLayout());
		
		//###############
		// Formulario id
		//###############
		JPanel panelFormuId = new JPanel(new BorderLayout());
		formularioId = new Formulario();
		id = new CampoFormularioNumeroEntero("Id",pry==null?0:pry.getId(),0,null);
		formularioId.addCampo(id);
		panelFormuId.add(formularioId,BorderLayout.CENTER);
		JButton consultar = new JButton("Consultar");
		
		consultar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				formularioId.marcaErrores();
				if(formularioId.esCorrecto()){
					Proyecto pro = new Proyecto();
					pro.setId(id.getResultado());
					ControladorFrontal.getInstancia().accion(Acciones.proyectosConsultar, pro);
				}
			}
		});
		
		panelFormuId.add(consultar,BorderLayout.EAST);
		this.add(panelFormuId,BorderLayout.NORTH);
		
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
				if(proyecto == null)
					JOptionPane.showMessageDialog(EditarProyectoGUI.this,"Realice una consulta válida para poder editar.");
				else{formulario.marcaErrores();
					if(formulario.esCorrecto()){
						proyecto.setNombre(nombre.getResultado());
						proyecto.setDescripcion(descripcion.getResultado());
						proyecto.setEmpleado(empleadosProyecto);
						ControladorFrontal.getInstancia().accion(Acciones.proyectosEditar, proyecto);
					}
				}
			}
		});
		botonera.add(enviar);
		
		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EditarProyectoGUI.this.dispose();
			}
		});
		botonera.add(cancelar);
		this.add(botonera,BorderLayout.SOUTH);
		
		this.empleadosProyecto = new HashSet<Empleado>();
		
		if(pry!=null && pry.getId() > 0)
			ControladorFrontal.getInstancia().accion(Acciones.proyectosConsultar, pry);
		
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		
		
	}
	
	private Empleado getSelectedEmpleado(){
		return( (tablaEmpleados.getSelectedRow() != -1)? tablaEmpleadosModel.getEmpleado(tablaEmpleados.getSelectedRow()):null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualiza(Integer evento, Retorno datos) {
		if(evento == Acciones.proyectosEditar)
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
				JOptionPane.showMessageDialog(this, "Proyecto editado correctamente.", "Correcto", JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
			}
		else if(evento == Acciones.proyectosConsultar)
			if(datos.tieneErrores()){
				proyecto = null;
				empMap.clear();
				selectorEmp.setMap(empMap);
				empleadosProyecto.clear();
				tablaEmpleadosModel.update(empleadosProyecto);
				this.nombre.setValue("");
				this.descripcion.setValue("");
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
				proyecto = (Proyecto)datos.getDatos();
				empMap.clear();
				selectorEmp.setMap(empMap);
				empleadosProyecto.clear();
				this.nombre.setValue(proyecto.getNombre());
				this.descripcion.setValue(proyecto.getDescripcion());
				ControladorFrontal.getInstancia().accion(Acciones.empleadosListado, null);
				for(Empleado e:proyecto.getEmpleado()){
					empMap.remove(e.getNombreCompleto());
					empleadosProyecto.add(e);
				}
				selectorEmp.setMap(empMap);
				tablaEmpleadosModel.update(empleadosProyecto);
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
