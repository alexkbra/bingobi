/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import persistence.controllers.exceptions.NonexistentEntityException;
import persistence.entity.ControlProgramación;
import persistence.entity.Programaciones;

/**
 *
 * @author kbra
 */
public class ControlProgramaciónJpaController implements Serializable {

    public ControlProgramaciónJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ControlProgramación controlProgramación) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Programaciones idProgramacion = controlProgramación.getIdProgramacion();
            if (idProgramacion != null) {
                idProgramacion = em.getReference(idProgramacion.getClass(), idProgramacion.getId());
                controlProgramación.setIdProgramacion(idProgramacion);
            }
            em.persist(controlProgramación);
            if (idProgramacion != null) {
                idProgramacion.getControlProgramaciónList().add(controlProgramación);
                idProgramacion = em.merge(idProgramacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ControlProgramación controlProgramación) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ControlProgramación persistentControlProgramación = em.find(ControlProgramación.class, controlProgramación.getId());
            Programaciones idProgramacionOld = persistentControlProgramación.getIdProgramacion();
            Programaciones idProgramacionNew = controlProgramación.getIdProgramacion();
            if (idProgramacionNew != null) {
                idProgramacionNew = em.getReference(idProgramacionNew.getClass(), idProgramacionNew.getId());
                controlProgramación.setIdProgramacion(idProgramacionNew);
            }
            controlProgramación = em.merge(controlProgramación);
            if (idProgramacionOld != null && !idProgramacionOld.equals(idProgramacionNew)) {
                idProgramacionOld.getControlProgramaciónList().remove(controlProgramación);
                idProgramacionOld = em.merge(idProgramacionOld);
            }
            if (idProgramacionNew != null && !idProgramacionNew.equals(idProgramacionOld)) {
                idProgramacionNew.getControlProgramaciónList().add(controlProgramación);
                idProgramacionNew = em.merge(idProgramacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = controlProgramación.getId();
                if (findControlProgramación(id) == null) {
                    throw new NonexistentEntityException("The controlProgramaci\u00f3n with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ControlProgramación controlProgramación;
            try {
                controlProgramación = em.getReference(ControlProgramación.class, id);
                controlProgramación.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The controlProgramaci\u00f3n with id " + id + " no longer exists.", enfe);
            }
            Programaciones idProgramacion = controlProgramación.getIdProgramacion();
            if (idProgramacion != null) {
                idProgramacion.getControlProgramaciónList().remove(controlProgramación);
                idProgramacion = em.merge(idProgramacion);
            }
            em.remove(controlProgramación);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ControlProgramación> findControlProgramaciónEntities() {
        return findControlProgramaciónEntities(true, -1, -1);
    }

    public List<ControlProgramación> findControlProgramaciónEntities(int maxResults, int firstResult) {
        return findControlProgramaciónEntities(false, maxResults, firstResult);
    }

    private List<ControlProgramación> findControlProgramaciónEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ControlProgramación.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ControlProgramación findControlProgramación(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ControlProgramación.class, id);
        } finally {
            em.close();
        }
    }

    public int getControlProgramaciónCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ControlProgramación> rt = cq.from(ControlProgramación.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
