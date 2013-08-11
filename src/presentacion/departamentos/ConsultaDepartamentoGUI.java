package presentacion.departamentos;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import negocio.Retorno;
import negocio.TError;
import negocio.departamentos.Departamento;
import presentacion.GUI;
import presentacion.controlador.ControladorFrontal;
import presentacion.empleados.tabla.TablaEmpleados;
import presentacion.formulario.CampoFormularioNumeroEntero;
import presentacion.formulario.CampoFormularioTexto;
import presentacion.formulario.Formulario;
import constantes.Acciones;

public class ConsultaDepartamentoGUI extends GUI {

	private static final long serialVersionUID = -1861676066072740318L;
	private CampoFormularioTexto nombre;
	private CampoFormularioNumeroEntero codigo;
	private CampoFormularioTexto sueldo;
	private Formulario formulario;
	private TablaEmpleados tablaEmpleadosModel;
	private JButton calcularSalario;
	private	Departamento depCons;

	CampoFormularioNumeroEntero id;
	Formulario formularioId;

	public ConsultaDepartamentoGUI(GUI father, Departamento dep) {
		super(father);
		this.setLayout(new BorderLayout());
		this.setTitle("Consulta departamento");
		//###############
		// Formulario id
		//###############
		JPanel panelFormuyTabla = new JPanel(new BorderLayout());

		JPanel panelFormuId = new JPanel(new BorderLayout());
		formularioId = new Formulario();
		id = new CampoFormularioNumeroEntero("Id", (dep != null) ? dep.getId()
				: 0, 0, null);
		formularioId.addCampo(id);
		panelFormuId.add(formularioId, BorderLayout.CENTER);
		JButton consultar = new JButton("Consultar");

		consultar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				formularioId.marcaErrores();
				if (formularioId.esCorrecto()) {
					Departamento dep = new Departamento();
					dep.setId(id.getResultado());
					ControladorFrontal.getInstancia().accion(
							Acciones.departamentosConsultar, dep);
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
		codigo = new CampoFormularioNumeroEntero("Codigo", 0, 0, 999);
		sueldo = new CampoFormularioTexto("Sueldo", "");
		
		formulario.addCampo(nombre);
		formulario.addCampo(codigo);
		formulario.addCampo(sueldo);
		formulario.setModificable(false);

		panelFormuyTabla.add(formulario, BorderLayout.NORTH);

		tablaEmpleadosModel = new TablaEmpleados();
		JTable tablaEmpleados = new JTable(tablaEmpleadosModel);
		panelFormuyTabla.add(new JScrollPane(tablaEmpleados),
				BorderLayout.CENTER);

		this.add(panelFormuyTabla, BorderLayout.CENTER);
		//###############
		// Botones
		//###############		

		JPanel botonera = new JPanel(new GridLayout());
		JButton cancelar;
		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConsultaDepartamentoGUI.this.dispose();
			}
		});
		
		this.calcularSalario = new JButton("Calcular Salario");
		this.calcularSalario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ControladorFrontal.getInstancia().accion(Acciones.departamentosSalario, depCons);
			}
		});
		
		if(dep ==null)
			calcularSalario.setEnabled(false);
	
		botonera.add(this.calcularSalario);
		botonera.add(cancelar);
		this.add(botonera, BorderLayout.SOUTH);

		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		if (dep != null && dep.getId() > 0)
			ControladorFrontal.getInstancia().accion(
					Acciones.departamentosConsultar, dep);
	}

	@Override
	public void actualiza(Acciones evento, Retorno datos) {
		if (evento == Acciones.departamentosConsultar){
			if (datos.tieneErrores()) {
				id.setIndicador(false);
				nombre.setValue("");
				codigo.setValue("");
				sueldo.setValue("");
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
				depCons = (Departamento) datos.getDatos();
				nombre.setValue(depCons.getNombre());
				codigo.setValue(depCons.getCodigo());
				sueldo.setValue(depCons.getSueldo().toString());
				tablaEmpleadosModel.update(depCons.getEmpleado());
			}
		}
		else if (evento == Acciones.departamentosSalario){
			
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
				JOptionPane.showMessageDialog(this,"Salario: " + datos.getDatos().toString());
			}
			
		}
	}
		

	@Override
	public void alVolver(GUI who) {
	}

}
