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
import persistence.entity.Cartones;
import persistence.entity.Tablas;

/**
 *
 * @author kbra
 */
public class CartonesJpaController implements Serializable {

    public CartonesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cartones cartones) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tablas idTabla = cartones.getIdTabla();
            if (idTabla != null) {
                idTabla = em.getReference(idTabla.getClass(), idTabla.getId());
                cartones.setIdTabla(idTabla);
            }
            em.persist(cartones);
            if (idTabla != null) {
                idTabla.getCartonesList().add(cartones);
                idTabla = em.merge(idTabla);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cartones cartones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cartones persistentCartones = em.find(Cartones.class, cartones.getId());
            Tablas idTablaOld = persistentCartones.getIdTabla();
            Tablas idTablaNew = cartones.getIdTabla();
            if (idTablaNew != null) {
                idTablaNew = em.getReference(idTablaNew.getClass(), idTablaNew.getId());
                cartones.setIdTabla(idTablaNew);
            }
            cartones = em.merge(cartones);
            if (idTablaOld != null && !idTablaOld.equals(idTablaNew)) {
                idTablaOld.getCartonesList().remove(cartones);
                idTablaOld = em.merge(idTablaOld);
            }
            if (idTablaNew != null && !idTablaNew.equals(idTablaOld)) {
                idTablaNew.getCartonesList().add(cartones);
                idTablaNew = em.merge(idTablaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cartones.getId();
                if (findCartones(id) == null) {
                    throw new NonexistentEntityException("The cartones with id " + id + " no longer exists.");
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
            Cartones cartones;
            try {
                cartones = em.getReference(Cartones.class, id);
                cartones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cartones with id " + id + " no longer exists.", enfe);
            }
            Tablas idTabla = cartones.getIdTabla();
            if (idTabla != null) {
                idTabla.getCartonesList().remove(cartones);
                idTabla = em.merge(idTabla);
            }
            em.remove(cartones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cartones> findCartonesEntities() {
        return findCartonesEntities(true, -1, -1);
    }

    public List<Cartones> findCartonesEntities(int maxResults, int firstResult) {
        return findCartonesEntities(false, maxResults, firstResult);
    }

    private List<Cartones> findCartonesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cartones.class));
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

    public Cartones findCartones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cartones.class, id);
        } finally {
            em.close();
        }
    }

    public int getCartonesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cartones> rt = cq.from(Cartones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Cartones> getCartonesXTablas(Tablas idTabla) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cartones> rt = cq.from(Cartones.class);
            cq.select(em.getCriteriaBuilder().equal(rt.get("idTabla"), idTabla));
            Query q = em.createQuery(cq);
            return ((List<Cartones>) q.getResultList());
        } finally {
            em.close();
        }
    }
    
}
