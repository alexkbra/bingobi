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
import persistence.entity.TipoBingos;
import persistence.entity.ValorBingos;

/**
 *
 * @author kbra
 */
public class ValorBingosJpaController implements Serializable {

    public ValorBingosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ValorBingos valorBingos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoBingos idTipoBingo = valorBingos.getIdTipoBingo();
            if (idTipoBingo != null) {
                idTipoBingo = em.getReference(idTipoBingo.getClass(), idTipoBingo.getId());
                valorBingos.setIdTipoBingo(idTipoBingo);
            }
            em.persist(valorBingos);
            if (idTipoBingo != null) {
                idTipoBingo.getValorBingosList().add(valorBingos);
                idTipoBingo = em.merge(idTipoBingo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ValorBingos valorBingos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ValorBingos persistentValorBingos = em.find(ValorBingos.class, valorBingos.getId());
            TipoBingos idTipoBingoOld = persistentValorBingos.getIdTipoBingo();
            TipoBingos idTipoBingoNew = valorBingos.getIdTipoBingo();
            if (idTipoBingoNew != null) {
                idTipoBingoNew = em.getReference(idTipoBingoNew.getClass(), idTipoBingoNew.getId());
                valorBingos.setIdTipoBingo(idTipoBingoNew);
            }
            valorBingos = em.merge(valorBingos);
            if (idTipoBingoOld != null && !idTipoBingoOld.equals(idTipoBingoNew)) {
                idTipoBingoOld.getValorBingosList().remove(valorBingos);
                idTipoBingoOld = em.merge(idTipoBingoOld);
            }
            if (idTipoBingoNew != null && !idTipoBingoNew.equals(idTipoBingoOld)) {
                idTipoBingoNew.getValorBingosList().add(valorBingos);
                idTipoBingoNew = em.merge(idTipoBingoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = valorBingos.getId();
                if (findValorBingos(id) == null) {
                    throw new NonexistentEntityException("The valorBingos with id " + id + " no longer exists.");
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
            ValorBingos valorBingos;
            try {
                valorBingos = em.getReference(ValorBingos.class, id);
                valorBingos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The valorBingos with id " + id + " no longer exists.", enfe);
            }
            TipoBingos idTipoBingo = valorBingos.getIdTipoBingo();
            if (idTipoBingo != null) {
                idTipoBingo.getValorBingosList().remove(valorBingos);
                idTipoBingo = em.merge(idTipoBingo);
            }
            em.remove(valorBingos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ValorBingos> findValorBingosEntities() {
        return findValorBingosEntities(true, -1, -1);
    }

    public List<ValorBingos> findValorBingosEntities(int maxResults, int firstResult) {
        return findValorBingosEntities(false, maxResults, firstResult);
    }

    private List<ValorBingos> findValorBingosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ValorBingos.class));
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

    public ValorBingos findValorBingos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ValorBingos.class, id);
        } finally {
            em.close();
        }
    }

    public int getValorBingosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ValorBingos> rt = cq.from(ValorBingos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
