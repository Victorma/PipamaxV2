/**
 * 
 */
package negocio.empleados.imp;

import java.util.List;

import javax.persistence.*;

import constantes.Errores;
import negocio.Retorno;
import negocio.departamentos.Departamento;
import negocio.empleados.Empleado;
import negocio.empleados.SAEmpleados;
import negocio.proyectos.Proyecto;

public class SAEmpleadosImp implements SAEmpleados {
	private EntityManagerFactory emf;
	private EntityManager em;

	/**
	 * Da de alta un empleado.
	 * 
	 * @see SAEmpleados#altaEmpleado()
	 * @generated 
	 *            "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno altaEmpleado(Empleado emp) {
		Retorno retorno = new Retorno();
		try {
			emf = Persistence.createEntityManagerFactory("Implementacion Pipamax JPA");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Empleado> q = em.createNamedQuery(
					"negocio.empleados.Empleado.findBydni", Empleado.class);
			
			q.setParameter("dni", emp.getDni());
			q.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			
			if(q.getResultList().size()!=0){
				retorno.addError(Errores.empleadoDNIrepetido, emp.getDni());
			} else {
				boolean depCorrecto = true;
				if(emp.getDepartamento()!=null)
				{
					TypedQuery<Departamento> qDep = em.createNamedQuery(
							"negocio.departamentos.Departamento.findByid", Departamento.class);
					
					qDep.setParameter("id", emp.getDepartamento().getId());
					qDep.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
					
					if(qDep.getResultList().size()==0)
					{
						depCorrecto = false;
						retorno.addError(Errores.empleadoDepartamentoNoEncontrado, emp.getDepartamento().getId());
					}
					else 
						emp.setDepartamento(qDep.getResultList().get(0));
					
				}
				boolean proyCorrecto = true;
				if(emp.getProyecto()!=null&&emp.getProyecto().size()>0)
				{
					for(Proyecto p:emp.getProyecto())
					{
						TypedQuery<Proyecto> qD = em.createNamedQuery(
								"negocio.proyectos.Proyecto.findByid", Proyecto.class);
						qD.setParameter("id", p.getId());
						qD.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
						
						if(qD.getResultList().size() == 0)
						{
							proyCorrecto = false;
							retorno.addError(Errores.proyectoNoEncontrado, emp.getDepartamento().getId());
						}
						else
						{
							Proyecto proy = qD.getResultList().get(0);
							
							if(!emp.getProyecto().contains(proy))emp.getProyecto().add(proy);
							//TODO es absurdo, estamos recorriendo los proyectos del empleado
							if(!proy.getEmpleado().contains(emp))proy.getEmpleado().add(emp);
						}
					}
				}
				if(depCorrecto&&proyCorrecto){
					em.persist(emp);
					if(q.getResultList().size()!=1) // Dado que el acceso es concurrente, podrían crearse dos empleados con el mismo dni al mismo tiempo.
						em.getTransaction().rollback();
					else{ 
						em.getTransaction().commit();
						retorno.setDatos(emp.getId());
					}
				}
			}
		}catch (OptimisticLockException ole){
			em.getTransaction().rollback();
			retorno.addError(Errores.errorDeAccesoConcurrente, ole.getMessage());
		}catch (IllegalStateException e) {
			em.getTransaction().rollback();
		}catch (PersistenceException e1) {
			em.getTransaction().rollback();
			retorno.addError(Errores.empleadoNoCreado, null);
		}finally{
			em.close();
			emf.close();
		}
		return retorno;
	}

	/**
	 * (sin Javadoc)
	 * 
	 * @see SAEmpleados#bajaEmpleado()
	 * @generated 
	 *            "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno bajaEmpleado(Empleado emp) 
	{
		Retorno retorno = new Retorno();
		emf = Persistence.createEntityManagerFactory("Implementacion Pipamax JPA");
		em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			TypedQuery<Empleado> q = em.createNamedQuery(
					"negocio.empleados.Empleado.findBydni", Empleado.class);
			
			q.setParameter("dni", emp.getDni());
			
			//q.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			
			if(q.getResultList().size() == 0)
				retorno.addError(Errores.empleadoNoEncontrado, emp.getId());
			else{
				Empleado e = q.getResultList().get(0);
				if(e.getDepartamento() != null)
				{
					//Se bloquea también el departamento  
					//TODO Ahora que son typed query tambien se bloquea??
					TypedQuery<Departamento> qDep = em.createNamedQuery(
							"negocio.departamentos.Departamento.findByid", Departamento.class);
					
					qDep.setParameter("id", e.getDepartamento().getId());
					qDep.setLockMode(LockModeType.OPTIMISTIC);
					
					if(qDep.getResultList().size()==0)
						retorno.addError(Errores.empleadoDepartamentoNoEncontrado, e.getDepartamento().getId());
					//else
						// TODO AQUI HABRIA QUE QUITAR EL DEPARTAMENTO PARA EL EMPLEADO A BORRAR
				}
				if(e.getProyecto().size()>0){
					retorno.addError(Errores.empleadoTieneProyectos, e.getProyecto());
					em.getTransaction().rollback();
				}else{
					//Calling remove on an object will also cascade the remove operation across any relationship that is marked as cascade remove(Departamento-empleado).
					TypedQuery<Empleado> qRE = em.createNamedQuery(	"negocio.empleados.Empleado.removeEmpleado", Empleado.class);
					qRE.setParameter("id", e.getId());
					//ejecutamos la query
					int seborra = qRE.executeUpdate();
					if (seborra == 1)
					{
						System.out.println(5);
						em.getTransaction().commit();
					}
					else{
						System.out.println(4);
						retorno.addError(Errores.empleadoNoBorrado, emp.getId());
						em.getTransaction().rollback();
					}
				}
				
			}		
		} catch (OptimisticLockException ole){
			em.getTransaction().rollback();
			retorno.addError(Errores.errorDeAccesoConcurrente, ole.getMessage());
		} catch (PersistenceException pe) {
			retorno.addError(Errores.errorDeAcceso, emp.getDni());
		} catch (IllegalStateException e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
			emf.close();
		}
		
		return retorno;
		
	}

	/**
	 * (sin Javadoc)
	 * 
	 * @see SAEmpleados#consultaEmpleado()
	 * @generated 
	 *            "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno consultaEmpleado(Empleado emp) {
		Retorno retorno = new Retorno();
		try {
			emf = Persistence.createEntityManagerFactory("Implementacion Pipamax JPA");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Empleado> q = em.createNamedQuery(
					"negocio.empleados.Empleado.findBydni", Empleado.class);
			
			q.setParameter("dni", emp.getDni());
			q.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			
			if(q.getResultList().size() == 0)
				retorno.addError(Errores.empleadoNoEncontrado, emp.getId());
			else
			{
				Empleado empleado = q.getResultList().get(0);
				em.detach(empleado);
				em.getTransaction().commit();
				retorno.setDatos(empleado);
			}
		} catch (OptimisticLockException ole){
			em.getTransaction().rollback();
			retorno.addError(Errores.errorDeAccesoConcurrente, ole.getMessage());
		} catch (PersistenceException pe) {
			retorno.addError(Errores.errorDeAcceso, emp.getDni());
		} catch (IllegalStateException ise) {
			em.getTransaction().rollback();
		} finally {
			em.close();
			emf.close();
		}

		return retorno;
	}

	/**
	 * (sin Javadoc)
	 * 
	 * @see SAEmpleados#editaEmpleado()
	 * @generated 
	 *            "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Retorno editaEmpleado(Empleado emp) {
		Retorno retorno = new Retorno();
		try {
			emf = Persistence.createEntityManagerFactory("Implementacion Pipamax JPA");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			//TODO Primero sacamos el empleado por DNI y luego por ID?? porque no directamente por id?
			TypedQuery<Empleado> q = em.createNamedQuery(
					"negocio.empleados.Empleado.findBydni", Empleado.class);
			q.setParameter("dni", emp.getDni());
			q.setLockMode(LockModeType.OPTIMISTIC);
			List<Empleado> emps = q.getResultList();
			if (emps.size()==0 || emps.get(0).getId() == emp.getId())
			{
				TypedQuery<Empleado> q2 = em.createNamedQuery(
						"negocio.empleados.Empleado.findByid", Empleado.class);
				
				q2.setParameter("id", emp.getId());
				q2.setLockMode(LockModeType.OPTIMISTIC);
				
				Empleado empleado = null;
				if(q2.getResultList().size() == 0)
					retorno.addError(Errores.empleadoNoEncontrado, emp.getId());
				else
					empleado = q2.getResultList().get(0);
				
				if(empleado.getClass()!=emp.getClass())
					throw new RuntimeException("Se ha intentado modificar el tipo de un empleado");
				
				boolean depCorrecto = true;
				if(emp.getDepartamento()!=null)
				{
					TypedQuery<Departamento> qDep = em.createNamedQuery(
							"negocio.departamentos.Departamento.findByid", Departamento.class);
					
					qDep.setParameter("id", emp.getDepartamento().getId());
					qDep.setLockMode(LockModeType.OPTIMISTIC);
					
					Departamento dep = null;
					if(qDep.getResultList().size()==0)
					{
						depCorrecto = false;
						retorno.addError(Errores.empleadoDepartamentoNoEncontrado, emp.getDepartamento().getId());
					}
					else
					{
						dep = qDep.getResultList().get(0);
						emp.setDepartamento(dep);
						if(!dep.getEmpleado().contains(emp))
							dep.getEmpleado().add(emp);
					}
				}
				boolean proyCorrecto = true;
				if(emp.getProyecto() != null && emp.getProyecto().size()>0){
					for(Proyecto p:emp.getProyecto())
					{
						TypedQuery<Proyecto> qD = em.createNamedQuery(
								"negocio.proyectos.Proyecto.findByid", Proyecto.class);
						qD.setParameter("id", p.getId());
						qD.setLockMode(LockModeType.OPTIMISTIC);
						
						if(qD.getResultList().size() == 0)
						{
							proyCorrecto = false;
							retorno.addError(Errores.proyectoNoEncontrado, emp.getDepartamento().getId());
						}
						else
						{
							Proyecto proy = qD.getResultList().get(0);
							if(!emp.getProyecto().contains(proy))emp.getProyecto().add(proy);
							if(!proy.getEmpleado().contains(emp))proy.getEmpleado().add(emp);
						}
					}
				}
				if(depCorrecto&&proyCorrecto){
					emp = em.merge(emp);
					em.lock(emp, LockModeType.OPTIMISTIC);
					if(q.getResultList().size()!=1) // Dado que el acceso es concurrente, podrían crearse dos empleados con el mismo dni al mismo tiempo.
						em.getTransaction().rollback();
					else{ 
						em.getTransaction().commit();
						retorno.setDatos(emp.getId());
					}
				}
			} else {
				em.detach(emps.get(0));
				retorno.addError(Errores.empleadoDNIrepetido, emps.get(0));
			}
		} catch (NoResultException e1) {
			retorno.addError(701, emp.getId());
		} catch (IllegalStateException e) {
			em.getTransaction().rollback();
		}
		em.close();
		emf.close();
		return retorno;
	}

	@Override
	public Retorno consultaListaEmpleados() {
		Retorno retorno = new Retorno();
		try {
			emf = Persistence.createEntityManagerFactory("Implementacion Pipamax JPA");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Empleado> q = em.createQuery("select obj from Empleado obj where obj.activo = 1",Empleado.class);
			q.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			List<Empleado> emps = q.getResultList();
			for(Empleado e: emps){
				em.lock(e, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				em.detach(e);
			}
			em.getTransaction().commit();
			retorno.setDatos(emps);
			
		} catch (OptimisticLockException ole){
			em.getTransaction().rollback();
			retorno.addError(Errores.errorDeAccesoConcurrente, ole.getMessage());
		} catch (NoResultException nre) {
			retorno.addError(701, null);
		} catch (IllegalStateException ise) {
			ise.getMessage();
			em.getTransaction().rollback();
		}finally{
			em.close();
			emf.close();
		}
		
		return retorno;
	}

	@Override
	public Retorno calculaSueldoEmpleado(Empleado emp) {
		Retorno retorno = new Retorno();
		try {
			emf = Persistence.createEntityManagerFactory("Implementacion Pipamax JPA");
			em = emf.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<Empleado> q = em.createNamedQuery(
					"negocio.empleados.Empleado.findByid", Empleado.class);
			
			q.setParameter("id", emp.getId());
			q.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			
			if(q.getResultList().size() == 0)
				retorno.addError(Errores.empleadoNoEncontrado, emp.getId());
			else{
				Empleado empleado = q.getResultList().get(0);
				if(empleado.getDepartamento()!=null)
					em.lock(empleado.getDepartamento(), LockModeType.OPTIMISTIC);
				Double d = empleado.calcularSueldo();
				em.getTransaction().commit();
				retorno.setDatos(d);
			}
		} catch (OptimisticLockException ole){
			em.getTransaction().rollback();
			retorno.addError(Errores.errorDeAccesoConcurrente, ole.getMessage());
		} catch (PersistenceException pe) {
			retorno.addError(Errores.errorDeAcceso, emp.getDni());
		} catch (IllegalStateException ise) {
			em.getTransaction().rollback();
		} finally {
			em.close();
			emf.close();
		}

		return retorno;
	}
}