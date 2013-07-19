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
			
			if(q.getResultList().size()!=0){
				retorno.addError(Errores.empleadoDNIrepetido, emp.getDni());
			} else {
				boolean depCorrecto = true;
				if(emp.getDepartamento()!=null){
					Departamento dep = em.find(Departamento.class, emp.getDepartamento().getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
					if(dep == null){
						depCorrecto = false;
						retorno.addError(Errores.empleadoDepartamentoNoEncontrado, emp.getDepartamento().getId());
					}else
						emp.setDepartamento(dep);
				}
				boolean proyCorrecto = true;
				if(emp.getProyecto()!=null&&emp.getProyecto().size()>0){
					for(Proyecto p:emp.getProyecto()){
						Proyecto proy = em.find(Proyecto.class, p.getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
						if(proy == null){
							proyCorrecto = false;
							retorno.addError(Errores.proyectoNoEncontrado, emp.getDepartamento().getId());
						}else{
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
	public Retorno bajaEmpleado(Empleado emp) {
		Retorno retorno = new Retorno();
		emf = Persistence.createEntityManagerFactory("Implementacion Pipamax JPA");
		em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Empleado e = em.find(Empleado.class, emp.getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			
			if(e == null)
				retorno.addError(Errores.empleadoNoEncontrado, emp.getId());
			else{
				//Se bloquea también el departamento 
				Departamento dep = em.find(Departamento.class, e.getDepartamento().getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				
				if(e.getProyecto().size()>0){
					retorno.addError(Errores.empleadoTieneProyectos, e.getProyecto());
					em.getTransaction().rollback();
				}else{
					//Calling remove on an object will also cascade the remove operation across any relationship that is marked as cascade remove(Departamento-empleado).
					em.remove(e);
					em.getTransaction().commit();
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
			Empleado empleado = em.find(Empleado.class, emp.getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			if(empleado == null)
				retorno.addError(Errores.empleadoNoEncontrado, emp.getId());
			else{
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
			TypedQuery<Empleado> q = em.createNamedQuery(
					"negocio.empleados.Empleado.findBydni", Empleado.class);
			q.setParameter("dni", emp.getDni());
			List<Empleado> emps = q.getResultList();
			if (emps.size()==0 || emps.get(0).getId() == emp.getId()) {
				Empleado empleado = em.find(Empleado.class, emp.getId());
				if(empleado.getClass()!=emp.getClass())
					throw new RuntimeException("Se ha intentado modificar el tipo de un empleado");
				
				boolean depCorrecto = true;
				if(emp.getDepartamento()!=null){
					Departamento dep = em.find(Departamento.class, emp.getDepartamento().getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
					if(dep == null){
						depCorrecto = false;
						retorno.addError(Errores.empleadoDepartamentoNoEncontrado, emp.getDepartamento().getId());
					}else{
						emp.setDepartamento(dep);
						if(!dep.getEmpleado().contains(emp))
							dep.getEmpleado().add(emp);
					}
				}
				boolean proyCorrecto = true;
				if(emp.getProyecto() != null && emp.getProyecto().size()>0){
					for(Proyecto p:emp.getProyecto()){
						Proyecto proy = em.find(Proyecto.class, p.getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
						if(proy == null){
							proyCorrecto = false;
							retorno.addError(Errores.proyectoNoEncontrado, emp.getDepartamento().getId());
						}else{
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
			TypedQuery<Empleado> q = em.createQuery("select obj from Empleado obj",Empleado.class);
			
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
			Empleado empleado = em.find(Empleado.class, emp.getId(), LockModeType.OPTIMISTIC);
			if(empleado == null)
				retorno.addError(Errores.empleadoNoEncontrado, emp.getId());
			else{
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