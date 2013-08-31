/**
 * 
 */
package negocio.departamentos.imp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import constantes.Errores;

import negocio.Retorno;
import negocio.departamentos.Departamento;
import negocio.departamentos.SADepartamentos;
import negocio.empleados.Empleado;


public class SADepartamentosImp implements SADepartamentos {
	private EntityManagerFactory emf;
	private EntityManager em;
	
	@Override
	public Retorno crearDepartamento(Departamento dep) {
		Retorno retorno = new Retorno();
		try {
			emf = Persistence.createEntityManagerFactory("Implementacion Pipamax JPA");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Departamento> q1 = em.createNamedQuery("negocio.departamentos.Departamento.findBynombre", Departamento.class);
			TypedQuery<Departamento> q2 = em.createNamedQuery("negocio.departamentos.Departamento.findBycodigo", Departamento.class);
			q1.setParameter("nombre", dep.getNombre());
			q1.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			q2.setParameter("codigo", dep.getCodigo());
			q2.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			if(q1.getResultList().size()!=0 || q2.getResultList().size() != 0){
				if (q1.getResultList().size()!=0) retorno.addError(Errores.departamentoNombreRepetido, dep.getNombre());
				if (q2.getResultList().size()!=0) retorno.addError(Errores.departamentoCodigoRepetido, dep.getCodigo());
			} else {
				em.persist(dep);
				if (q1.getResultList().size()!=1 || q2.getResultList().size()!=1){
					retorno.addError(Errores.errorDeAccesoConcurrente, null);
					em.getTransaction().rollback();
				}else{
					em.getTransaction().commit();
					retorno.setDatos(dep.getId());
				}
			}
		} catch (PersistenceException pe) {
			em.getTransaction().rollback();
			retorno.addError(Errores.departamentoNoCreado, null);
		} catch (IllegalStateException ise){
			em.getTransaction().rollback();
		}
		em.close();
		emf.close();
		return retorno;
	}

	@Override
	public Retorno bajaDepartamento(Departamento dep) {
		Retorno retorno = new Retorno();
		emf = Persistence.createEntityManagerFactory("Implementacion Pipamax JPA");
		em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			
			TypedQuery<Departamento> q = em.createNamedQuery(
					"negocio.departamentos.Departamento.findByid", Departamento.class);
			
			q.setParameter("id", dep.getId());
			q.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			
			Departamento depart = null;
			
			if(q.getResultList().size() == 1)
				depart = q.getResultList().get(0);
			
			if(depart==null)
				retorno.addError(Errores.departamentoNoEncontrado, dep.getId());
			else{
				if(depart.getEmpleado().size()!=0){
					Set<Empleado> empleados = new HashSet<Empleado>();
					for(Empleado e:depart.getEmpleado()){
						em.detach(e); empleados.add(e);
					}
					retorno.addError(Errores.departamentoConEmpleados, empleados);
				} else {
					
					TypedQuery<Departamento> qRE = em.createNamedQuery(
							"negocio.departamentos.Departamento.removeDepartamento", Departamento.class);
					qRE.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
					qRE.setParameter("id", depart.getId());
					if (qRE.executeUpdate() == 1) 
						em.getTransaction().commit();
					else{
						retorno.addError(Errores.departamentoNoBorrado, dep.getId());
						em.getTransaction().rollback();
					}
				}
			}
		} catch (OptimisticLockException ole){
			retorno.addError(Errores.errorDeAccesoConcurrente, ole.getMessage());
			em.getTransaction().rollback();
		} catch (PersistenceException e1) {
			retorno.addError(Errores.departamentoNoBorrado, dep.getId());
			em.getTransaction().rollback();
		} catch (IllegalStateException ise) {
			em.getTransaction().rollback();
		}finally{
			em.close();
			emf.close();
		}
		return retorno;
	}

	@Override
	public Retorno consultaDepartamento(Departamento dep) {
		Retorno retorno = new Retorno();
		try {
			emf = Persistence.createEntityManagerFactory("Implementacion Pipamax JPA");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			TypedQuery<Departamento> q = em.createNamedQuery(
					"negocio.departamentos.Departamento.findByid", Departamento.class);
			
			q.setParameter("id", dep.getId());
			q.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			
			Departamento departamento = null;
			
			if(q.getResultList().size() == 1)
				departamento = q.getResultList().get(0);
			
			if(departamento == null)
				retorno.addError(Errores.departamentoNoEncontrado, dep.getId());
			else{
				for(Empleado e:departamento.getEmpleado()){
					em.lock(e, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
					em.detach(e);
				}
				em.detach(departamento);
				em.getTransaction().commit();
				retorno.setDatos(departamento);
			}
		} catch (OptimisticLockException ole){
			retorno.addError(Errores.errorDeAccesoConcurrente, ole.getMessage());
			em.getTransaction().rollback();
		} catch (PersistenceException pe) {
			retorno.addError(Errores.departamentoNoEncontrado, dep.getId());
			em.getTransaction().rollback();
		} catch (IllegalStateException ise) {
			em.getTransaction().rollback();
		}finally{
			em.close();
			emf.close();
		}
		
		return retorno;
	}

	@Override
	public Retorno editarDepartamento(Departamento dep) {
		Retorno retorno = new Retorno();
		try {
			emf = Persistence.createEntityManagerFactory("Implementacion Pipamax JPA");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Departamento> q1 = em.createNamedQuery("negocio.departamentos.Departamento.findBynombre", Departamento.class);
			TypedQuery<Departamento> q2 = em.createNamedQuery("negocio.departamentos.Departamento.findBycodigo", Departamento.class);
			q1.setParameter("nombre", dep.getNombre());
			q1.setLockMode(LockModeType.OPTIMISTIC);
			q2.setParameter("codigo", dep.getCodigo());
			q2.setLockMode(LockModeType.OPTIMISTIC);
			if(q1.getResultList().size()!=0 && q1.getResultList().get(0).getId()!=dep.getId() || q2.getResultList().size() != 0 && q2.getResultList().get(0).getId()!=dep.getId() ){
				if (q1.getResultList().size()!=0) retorno.addError(Errores.departamentoNombreRepetido, dep.getNombre());
				if (q2.getResultList().size()!=0) retorno.addError(Errores.departamentoCodigoRepetido, dep.getCodigo());
			} else {
				for(Empleado e:dep.getEmpleado())
				{
					TypedQuery<Empleado> qEmp = em.createNamedQuery(
							"negocio.empleados.Empleado.findByid", Empleado.class);
					
					qEmp.setParameter("id", e.getId());
					qEmp.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
					
					Empleado empBuscado = null;
					
					if(qEmp.getResultList().size() == 1)
						empBuscado = qEmp.getResultList().get(0);
					
					if(empBuscado == null){
						retorno.addError(Errores.empleadoNoEncontrado, e.getId());
						break;
					}else{
						dep.getEmpleado().remove(e);
						dep.getEmpleado().add(empBuscado);
					}
				}				
				Departamento editado = em.merge(dep);
				for(Empleado e:editado.getEmpleado())
					e.setDepartamento(editado);
				if (q1.getResultList().size()!=1 || q2.getResultList().size()!=1){
					retorno.addError(Errores.errorDeAccesoConcurrente, null);
					em.getTransaction().rollback();
				}else{
					em.getTransaction().commit();
					retorno.setDatos(dep.getId());
				}
			}
		} catch (PersistenceException pe) {
			em.getTransaction().rollback();
			retorno.addError(Errores.departamentoNoCreado, null);
		} catch (IllegalStateException ise){
			em.getTransaction().rollback();
		}finally{
			em.close();
			emf.close();
		}
		
		return retorno;
	}

	@Override
	public Retorno consultarListaDepartamentos() {
		Retorno retorno = new Retorno();
		try {
			emf = Persistence.createEntityManagerFactory("Implementacion Pipamax JPA");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
				TypedQuery<Departamento> q = em.createQuery("select obj from Departamento obj where obj.activo = 1", Departamento.class);
				q.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				List<Departamento> deps = q.getResultList();
				for(Departamento d: deps){
					em.lock(d, LockModeType.OPTIMISTIC);
					em.detach(d);
				}
				
			em.getTransaction().commit();
			retorno.setDatos(deps);
		} catch (NoResultException nre) {
			retorno.addError(Errores.departamentoNoEncontrado, null);
		} catch (IllegalStateException ise) {
			em.getTransaction().rollback();
		} finally {
			em.close();
			emf.close();
		}
		
		return retorno;
	}
	
	
	@Override
	public Retorno consultarSalarioDepartamento(Departamento dep)
	{
		Retorno retorno = new Retorno();
		try {
			emf = Persistence.createEntityManagerFactory("Implementacion Pipamax JPA");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			TypedQuery<Departamento> q = em.createNamedQuery(
					"negocio.departamentos.Departamento.findByid", Departamento.class);
			
			q.setParameter("id", dep.getId());
			q.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			
			Departamento departamento = null;
			
			if(q.getResultList().size() == 1)
				departamento = q.getResultList().get(0);
			
			if(departamento == null)
				retorno.addError(Errores.departamentoNoEncontrado, dep.getId());
			else{
				em.getTransaction().commit();
				retorno.setDatos(departamento.getNumEpleados()*departamento.getSueldo());
			}
		} catch (OptimisticLockException ole){
			retorno.addError(Errores.errorDeAccesoConcurrente, ole.getMessage());
			em.getTransaction().rollback();
		} catch (PersistenceException pe) {
			retorno.addError(Errores.departamentoNoEncontrado, dep.getId());
			em.getTransaction().rollback();
		} catch (IllegalStateException ise) {
			em.getTransaction().rollback();
		} finally {
			em.close();
			emf.close();
		}
		
		return retorno;
		
	}
	
}