package presentacion.departamentos;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import constantes.Acciones;
import negocio.Retorno;
import negocio.TError;
import negocio.departamentos.Departamento;
import presentacion.GUI;
import presentacion.controlador.ControladorFrontal;
import presentacion.formulario.CampoFormularioNumeroEntero;
import presentacion.formulario.CampoFormularioTexto;
import presentacion.formulario.Formulario;

public class CrearDepartamentoGUI extends GUI {

	private static final long serialVersionUID = -7467166064842817033L;
	private CampoFormularioTexto nombre;
	private CampoFormularioNumeroEntero codigo;
	private CampoFormularioTexto sueldo;
	private Formulario formulario;

	public CrearDepartamentoGUI(GUI father) {
		super(father);
		this.setLayout(new BorderLayout());
		this.setTitle("Alta departamento");
		//###############
		// Formulario
		//###############

		formulario = new Formulario();
		nombre = new CampoFormularioTexto("Nombre", "");
		codigo = new CampoFormularioNumeroEntero("Código", 0, 0, 999);
		sueldo = new CampoFormularioTexto("Sueldo", "");

		formulario.addCampo(nombre);
		formulario.addCampo(codigo);
		formulario.addCampo(sueldo);

		this.add(formulario, BorderLayout.CENTER);

		//###############
		// Botones
		//###############		

		JPanel botonera = new JPanel(new GridLayout(1, 2));

		JButton enviar, cancelar;
		enviar = new JButton("Enviar");
		enviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				formulario.marcaErrores();
				try{
					Double s = Double.parseDouble(sueldo.getResultado());
					if (formulario.esCorrecto()) {
						Departamento dep = new Departamento();
						dep.setNombre(nombre.getResultado());
						dep.setCodigo(codigo.getResultado());
						dep.setSueldo(s);
						ControladorFrontal.getInstancia().accion(
								Acciones.departamentosCrear, dep);
					}
				}catch (IllegalArgumentException ile){
					sueldo.setIndicador(false);
				}
			}
		});
		botonera.add(enviar);

		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CrearDepartamentoGUI.this.dispose();
			}
		});
		botonera.add(cancelar);
		this.add(botonera, BorderLayout.SOUTH);

		this.setSize(400, 150);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	@Override
	public void actualiza(Integer evento, Retorno datos) {
		if (evento == Acciones.departamentosCrear)
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
				JOptionPane.showMessageDialog(
						this,
						"Departamento dado de alta con el id "
								+ datos.getDatos(), "Correcto",
						JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
			}
	}

	@Override
	public void alVolver(GUI who) {
		// TODO Apéndice de método generado automáticamente

	}

}
