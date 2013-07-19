package presentacion.controlador.imp;

import presentacion.controlador.*;
import presentacion.dispacher.DispatcherView;
import constantes.Acciones;
import negocio.Retorno;
import negocio.controlador.*;
import negocio.controlador.clientes.*;
import negocio.controlador.departamentos.CommandDepartamentoBorrar;
import negocio.controlador.departamentos.CommandDepartamentoConsultar;
import negocio.controlador.departamentos.CommandDepartamentoConsultarLista;
import negocio.controlador.departamentos.CommandDepartamentoCrear;
import negocio.controlador.departamentos.CommandDepartamentoEditar;
import negocio.controlador.departamentos.CommandDepartamentoSalario;
import negocio.controlador.empleados.CommandEmpleadoBorrar;
import negocio.controlador.empleados.CommandEmpleadoCalcularSueldo;
import negocio.controlador.empleados.CommandEmpleadoConsultar;
import negocio.controlador.empleados.CommandEmpleadoConsultarLista;
import negocio.controlador.empleados.CommandEmpleadoCrear;
import negocio.controlador.empleados.CommandEmpleadoEditar;
import negocio.controlador.marcas.*;
import negocio.controlador.pedidos.*;
import negocio.controlador.productos.*;
import negocio.controlador.proveedores.*;
import negocio.controlador.proyectos.CommandProyectoBorrar;
import negocio.controlador.proyectos.CommandProyectoConsultar;
import negocio.controlador.proyectos.CommandProyectoConsultarLista;
import negocio.controlador.proyectos.CommandProyectoCrear;
import negocio.controlador.proyectos.CommandProyectoEditar;
import negocio.controlador.ventas.*;

public class ControladorFrontalImp extends ControladorFrontal {
	// TODO Actualizar el método actualiza.
	@Override
	public void accion(int evento, Object datos) {
		Retorno retorno = new Retorno();
		Command comando;
		switch (evento) {
		//Eventos clientes
		case Acciones.clientesListado: {
			comando = new CommandClienteListado();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.clientesCrear: {
			comando = new CommandClienteCrear();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.clientesConsultar: {
			comando = new CommandClienteConsultar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.clientesBorrar: {
			comando = new CommandClienteBorrar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.clientesEditar: {
			comando = new CommandClienteEditar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.clientesReactivar: {
			comando = new CommandClienteReactivar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
			// Fin eventos clientes

			//Eventos proveedores
		case Acciones.proveedoresListado: {
			comando = new CommandProveedorListado();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.proveedoresCrear: {
			comando = new CommandProveedorCrear();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.proveedoresConsultar: {
			comando = new CommandProveedorConsultar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.proveedoresBorrar: {
			comando = new CommandProveedorBorrar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.proveedoresEditar: {
			comando = new CommandProveedorEditar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.proveedoresReactivar: {
			comando = new CommandProveedorReactivar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
			//Fin eventos proveedores

			//Eventos marcas
		case Acciones.marcasListado: {
			comando = new CommandMarcaListado();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.marcasCrear: {
			comando = new CommandMarcaCrear();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.marcasConsultar: {
			comando = new CommandMarcaConsultar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.marcasBorrar: {
			comando = new CommandMarcaBorrar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.marcasEditar: {
			comando = new CommandMarcaEditar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.marcasReactivar: {
			comando = new CommandMarcaReactivar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.marcasListadoProductos: {
			//TODO: no hay command para marcasListadoProductos.
			comando = new CommandMarcaListadoProductos();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
			// Fin eventos marcas

			//Eventos Productos
		case Acciones.productosListado: {
			comando = new CommandProductoListado();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.productosCrear: {
			comando = new CommandProductoCrear();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.productosConsultar: {
			comando = new CommandProductoConsultar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.productosBorrar: {
			comando = new CommandProductoBorrar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.productosEditar: {
			comando = new CommandProductoEditar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.productosListaSuministros: {
			comando = new CommandProductoListaSuministros();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.productosCrearSuministro: {
			comando = new CommandProductoCrearSuministro();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		case Acciones.productosBorrarSuministro: {
			comando = new CommandProductoBorrarSuministro();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
			//Fin eventos productos

			//Inicio eventos ventas
		case Acciones.ventasListado: {
			comando = new CommandVentaListado();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.ventasConsultar: {
			comando = new CommandVentaConsultar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.ventasCrear: {
			comando = new CommandVentaCrear();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.ventasBorrar: {
			comando = new CommandVentaBorrar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.ventasDevolucion: {
			comando = new CommandVentaDevolucion();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

			//Fin eventos ventas

			//Inicio eventos pedidos
		case Acciones.pedidosLista: {
			comando = new CommandPedidoLista();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.pedidosConsultar: {
			comando = new CommandPedidoConsultar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.pedidosCrear: {
			comando = new CommandPedidoCrear();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.pedidosAnular: {
			comando = new CommandPedidoAnular();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.pedidosCompletar: {
			comando = new CommandPedidoCompletar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.pedidosBorrar: {
			comando = new CommandPedidoBorrar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.pedidosConsProdProv: {
			comando = new CommandPedidoConsProdProv();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

			//Fin eventos pedidos

			//Inicio eventos empleados
		case Acciones.empleadosListado: {
			comando = new CommandEmpleadoConsultarLista();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.empleadosConsultar: {
			comando = new CommandEmpleadoConsultar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.empleadosCrear: {
			comando = new CommandEmpleadoCrear();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.empleadosBorrar: {
			comando = new CommandEmpleadoBorrar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.empleadosEditar: {
			comando = new CommandEmpleadoEditar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		
		case Acciones.empleadosSalario: {
			comando = new CommandEmpleadoCalcularSueldo();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

			//Fin eventos empleados

			//Inicio eventos departamentos
		case Acciones.departamentosListado: {
			comando = new CommandDepartamentoConsultarLista();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.departamentosConsultar: {
			comando = new CommandDepartamentoConsultar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.departamentosCrear: {
			comando = new CommandDepartamentoCrear();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.departamentosBorrar: {
			comando = new CommandDepartamentoBorrar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.departamentosEditar: {
			comando = new CommandDepartamentoEditar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}
		
		case Acciones.departamentosSalario: {
			comando = new CommandDepartamentoSalario();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;			
		}
		

			//Fin eventos departamentos
		
		//Inicio eventos departamentos
		case Acciones.proyectosListado: {
			comando = new CommandProyectoConsultarLista();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.proyectosConsultar: {
			comando = new CommandProyectoConsultar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.proyectosCrear: {
			comando = new CommandProyectoCrear();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.proyectosBorrar: {
			comando = new CommandProyectoBorrar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

		case Acciones.proyectosEditar: {
			comando = new CommandProyectoEditar();
			comando.setContext(datos);
			retorno = ControladorAplicacion.getInstancia().ejecuta(comando);
			DispatcherView.getInstancia().actualiza(evento, retorno);
			break;
		}

			//Fin eventos departamentos

		}
	}

}
