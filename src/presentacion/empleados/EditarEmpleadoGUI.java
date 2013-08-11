package presentacion.empleados;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import constantes.Acciones;
import constantes.Errores;
import negocio.Retorno;
import negocio.TError;
import negocio.departamentos.Departamento;
import negocio.empleados.Empleado;
import negocio.empleados.EmpleadoCompleto;
import negocio.empleados.EmpleadoParcial;
import presentacion.GUI;
import presentacion.controlador.ControladorFrontal;
import presentacion.formulario.CampoFormularioDNI;
import presentacion.formulario.CampoFormularioNumeroEntero;
import presentacion.formulario.CampoFormularioSelector;
import presentacion.formulario.CampoFormularioTexto;
import presentacion.formulario.Formulario;

public class EditarEmpleadoGUI extends GUI {

	private static final long serialVersionUID = 3290328323991638286L;
	private CampoFormularioTexto nombre;
	private CampoFormularioTexto apellido1;
	private CampoFormularioTexto apellido2;
	private CampoFormularioDNI dni;
	private CampoFormularioTexto ciudad;
	private CampoFormularioTexto direccion;
	private CampoFormularioNumeroEntero cp;
	private CampoFormularioSelector<String, String> tiempo;
	private CampoFormularioSelector<String, Departamento> departamento;
	private CampoFormularioNumeroEntero telefono;
	private Formulario formulario;
	private String ninguno = " -- Ninguno -- ";

	private Map<String, Departamento> camposDep;

	private CampoFormularioNumeroEntero id;
	private Formulario formularioId;
	private Empleado empleado;

	public EditarEmpleadoGUI(GUI father, Empleado emp) {
		super(father);
		this.setLayout(new BorderLayout());
		this.setTitle("Editar empleado");
		//###############
		// Formulario id
		//###############
		JPanel panelFormuId = new JPanel(new BorderLayout());
		formularioId = new Formulario();
		id = new CampoFormularioNumeroEntero("Id",emp==null?0:emp.getId(), 0, null);
		formularioId.addCampo(id);
		panelFormuId.add(formularioId, BorderLayout.CENTER);
		JButton consultar = new JButton("Consultar");

		consultar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				formularioId.marcaErrores();
				if (formularioId.esCorrecto()) {
					Empleado emp = (tiempo.getResultado().equalsIgnoreCase("completo")?new EmpleadoCompleto():new EmpleadoParcial());
					emp.setId(id.getResultado());
					ControladorFrontal.getInstancia().accion(
							Acciones.empleadosConsultar, emp);
				}
			}
		});

		panelFormuId.add(consultar, BorderLayout.EAST);
		this.add(panelFormuId, BorderLayout.NORTH);
		//###############
		// Formulario
		//###############

		formulario = new Formulario();
		nombre = new CampoFormularioTexto("Nombre", "");
		apellido1 = new CampoFormularioTexto("Primer Apellido", "");
		apellido2 = new CampoFormularioTexto("Segundo Apellido", "");
		dni = new CampoFormularioDNI("Dni", "");
		ciudad = new CampoFormularioTexto("Ciudad", "");
		direccion = new CampoFormularioTexto("Direccion", "");
		cp = new CampoFormularioNumeroEntero("CP", 0, 10000, 99999);

		Map<String, String> campos = new TreeMap<String, String>();
		campos.put("Completo", "completo");
		campos.put("Parcial", "parcial");
		tiempo = new CampoFormularioSelector<String, String>("Tiempo", campos);

		tiempo.setModificable(false);
		
		camposDep = new TreeMap<String, Departamento>();
		camposDep.put(ninguno, null);
		departamento = new CampoFormularioSelector<String, Departamento>(
				"Departamento", camposDep);

		telefono = new CampoFormularioNumeroEntero("Telefono", 0, 100000000,
				999999999);

		formulario.addCampo(nombre);
		formulario.addCampo(apellido1);
		formulario.addCampo(apellido2);
		formulario.addCampo(dni);
		formulario.addCampo(ciudad);
		formulario.addCampo(direccion);
		formulario.addCampo(cp);
		formulario.addCampo(tiempo);
		formulario.addCampo(departamento);
		formulario.addCampo(telefono);

		this.add(formulario, BorderLayout.CENTER);

		//###############
		// Botones
		//###############		

		JPanel botonera = new JPanel(new GridLayout());
		JButton cancelar, enviar;
		enviar = new JButton("Enviar");
		enviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (empleado == null) {
					JOptionPane
							.showMessageDialog(EditarEmpleadoGUI.this,
									"El empleado no es válido, para poder enviar, realice una consulta válida.");
				} else {
					formulario.marcaErrores();
					if (formulario.esCorrecto()) {
						empleado.setNombre(nombre.getResultado());
						empleado.setApellido1(apellido1.getResultado());
						empleado.setApellido2(apellido2.getResultado());
						empleado.setDni(dni.getResultado());
						empleado.setCiudad(ciudad.getResultado());
						empleado.setDireccion(direccion.getResultado());
						empleado.setDepartamento(departamento.getResultado());
						empleado.setCp(cp.getResultado());
						empleado.setTelefono(telefono.getResultado());
						ControladorFrontal.getInstancia().accion(
								Acciones.empleadosEditar, empleado);
					}
				}
			}
		});
		botonera.add(enviar);

		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EditarEmpleadoGUI.this.dispose();
			}
		});
		botonera.add(cancelar);
		this.add(botonera, BorderLayout.SOUTH);

		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		ControladorFrontal.getInstancia().accion(Acciones.departamentosListado,	null);
		if (emp != null && emp.getId() > 0)
			ControladorFrontal.getInstancia().accion(Acciones.empleadosConsultar, emp);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualiza(Acciones evento, Retorno datos) {
		if (evento == Acciones.empleadosConsultar)
			if (datos.tieneErrores()) {
				id.setIndicador(false);
				empleado = null;
				nombre.setValue("");
				apellido1.setValue("");
				apellido2.setValue("");
				dni.setValue("");
				ciudad.setValue("");
				direccion.setValue("");
				cp.setValue("");
				telefono.setValue("");
				StringBuilder errores = new StringBuilder(
						"Se produjeron los siguientes errores: \n");
				for (TError error : datos.getErrores().getLista()) {
					errores.append("  - ");
					switch (error.getErrorId()) {
					default:
						errores.append(error.getDatos().toString());
						break;
					}
					errores.append("\n");
				}
				JOptionPane.showMessageDialog(this, errores.toString(),
						"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				empleado = (Empleado) datos.getDatos();
				nombre.setValue(empleado.getNombre());
				apellido1.setValue(empleado.getApellido1());
				apellido2.setValue(empleado.getApellido2());
				dni.setValue(empleado.getDni());
				ciudad.setValue(empleado.getCiudad());
				tiempo.setValue(empleado.getClass()==EmpleadoCompleto.class?"Completo":"Parcial");
				direccion.setValue(empleado.getDireccion());
				if (empleado.getDepartamento() != null)
					departamento
							.setValue(empleado.getDepartamento().toString());
				else
					departamento.setValue(ninguno);
				cp.setValue(empleado.getCp());
				telefono.setValue(empleado.getTelefono());
			}
		else if (evento == Acciones.empleadosEditar)
			if (datos.tieneErrores()) {
				StringBuilder errores = new StringBuilder(
						"Se produjeron los siguientes errores: \n");
				for (TError error : datos.getErrores().getLista()) {
					errores.append("  - ");
					switch (error.getErrorId()) {
					case Errores.empleadoDNIrepetido: {
						dni.setIndicador(false);
						errores.append("Dni repetido, encontrado en el empleado: "
								+ error.getDatos());
					}
						break;
					default:
						errores.append(error.getDatos().toString());
						break;
					}
					errores.append("\n");
				}
				JOptionPane.showMessageDialog(this, errores.toString(),
						"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this,
						"Empleado editado correctamente", "Correcto",
						JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
			}
		else if (evento == Acciones.departamentosListado)
			if (datos.tieneErrores()) {
				StringBuilder errores = new StringBuilder(
						"Se produjeron los siguientes errores: \n");
				for (TError error : datos.getErrores().getLista()) {
					errores.append("  - ");
					switch (error.getErrorId()) {
					default:
						errores.append(error.getDatos().toString());
						break;
					}
					errores.append("\n");
				}
				JOptionPane.showMessageDialog(this, errores.toString(),
						"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				for (Departamento dep : (Collection<Departamento>) datos
						.getDatos())
					camposDep.put(dep.toString(), dep);
				departamento.setMap(camposDep);
			}
	}

	@Override
	public void alVolver(GUI who) {
	}

}
