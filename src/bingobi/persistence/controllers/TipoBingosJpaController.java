/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingobi.persistence.controllers;

import bingobi.persistence.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import bingobi.persistence.entities.Programaciones;
import bingobi.persistence.entities.TipoBingos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kbra
 */
public class TipoBingosJpaController implements Serializable {

    public TipoBingosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoBingos tipoBingos) {
        if (tipoBingos.getProgramacionesList() == null) {
            tipoBingos.setProgramacionesList(new ArrayList<Programaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Programaciones> attachedProgramacionesList = new ArrayList<Programaciones>();
            for (Programaciones programacionesListProgramacionesToAttach : tipoBingos.getProgramacionesList()) {
                programacionesListProgramacionesToAttach = em.getReference(programacionesListProgramacionesToAttach.getClass(), programacionesListProgramacionesToAttach.getId());
                attachedProgramacionesList.add(programacionesListProgramacionesToAttach);
            }
            tipoBingos.setProgramacionesList(attachedProgramacionesList);
            em.persist(tipoBingos);
            for (Programaciones programacionesListProgramaciones : tipoBingos.getProgramacionesList()) {
                TipoBingos oldIdTipoBingoOfProgramacionesListProgramaciones = programacionesListProgramaciones.getIdTipoBingo();
                programacionesListProgramaciones.setIdTipoBingo(tipoBingos);
                programacionesListProgramaciones = em.merge(programacionesListProgramaciones);
                if (oldIdTipoBingoOfProgramacionesListProgramaciones != null) {
                    oldIdTipoBingoOfProgramacionesListProgramaciones.getProgramacionesList().remove(programacionesListProgramaciones);
                    oldIdTipoBingoOfProgramacionesListProgramaciones = em.merge(oldIdTipoBingoOfProgramacionesListProgramaciones);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoBingos tipoBingos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoBingos persistentTipoBingos = em.find(TipoBingos.class, tipoBingos.getId());
            List<Programaciones> programacionesListOld = persistentTipoBingos.getProgramacionesList();
            List<Programaciones> programacionesListNew = tipoBingos.getProgramacionesList();
            List<Programaciones> attachedProgramacionesListNew = new ArrayList<Programaciones>();
            for (Programaciones programacionesListNewProgramacionesToAttach : programacionesListNew) {
                programacionesListNewProgramacionesToAttach = em.getReference(programacionesListNewProgramacionesToAttach.getClass(), programacionesListNewProgramacionesToAttach.getId());
                attachedProgramacionesListNew.add(programacionesListNewProgramacionesToAttach);
            }
            programacionesListNew = attachedProgramacionesListNew;
            tipoBingos.setProgramacionesList(programacionesListNew);
            tipoBingos = em.merge(tipoBingos);
            for (Programaciones programacionesListOldProgramaciones : programacionesListOld) {
                if (!programacionesListNew.contains(programacionesListOldProgramaciones)) {
                    programacionesListOldProgramaciones.setIdTipoBingo(null);
                    programacionesListOldProgramaciones = em.merge(programacionesListOldProgramaciones);
                }
            }
            for (Programaciones programacionesListNewProgramaciones : programacionesListNew) {
                if (!programacionesListOld.contains(programacionesListNewProgramaciones)) {
                    TipoBingos oldIdTipoBingoOfProgramacionesListNewProgramaciones = programacionesListNewProgramaciones.getIdTipoBingo();
                    programacionesListNewProgramaciones.setIdTipoBingo(tipoBingos);
                    programacionesListNewProgramaciones = em.merge(programacionesListNewProgramaciones);
                    if (oldIdTipoBingoOfProgramacionesListNewProgramaciones != null && !oldIdTipoBingoOfProgramacionesListNewProgramaciones.equals(tipoBingos)) {
                        oldIdTipoBingoOfProgramacionesListNewProgramaciones.getProgramacionesList().remove(programacionesListNewProgramaciones);
                        oldIdTipoBingoOfProgramacionesListNewProgramaciones = em.merge(oldIdTipoBingoOfProgramacionesListNewProgramaciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoBingos.getId();
                if (findTipoBingos(id) == null) {
                    throw new NonexistentEntityException("The tipoBingos with id " + id + " no longer exists.");
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
            TipoBingos tipoBingos;
            try {
                tipoBingos = em.getReference(TipoBingos.class, id);
                tipoBingos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoBingos with id " + id + " no longer exists.", enfe);
            }
            List<Programaciones> programacionesList = tipoBingos.getProgramacionesList();
            for (Programaciones programacionesListProgramaciones : programacionesList) {
                programacionesListProgramaciones.setIdTipoBingo(null);
                programacionesListProgramaciones = em.merge(programacionesListProgramaciones);
            }
            em.remove(tipoBingos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoBingos> findTipoBingosEntities() {
        return findTipoBingosEntities(true, -1, -1);
    }

    public List<TipoBingos> findTipoBingosEntities(int maxResults, int firstResult) {
        return findTipoBingosEntities(false, maxResults, firstResult);
    }

    private List<TipoBingos> findTipoBingosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoBingos.class));
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

    public TipoBingos findTipoBingos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoBingos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoBingosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoBingos> rt = cq.from(TipoBingos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
