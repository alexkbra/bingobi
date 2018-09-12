/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import persistence.entity.ValorBingos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistence.controllers.exceptions.NonexistentEntityException;
import persistence.entity.Programaciones;
import persistence.entity.TipoBingos;

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
        if (tipoBingos.getValorBingosList() == null) {
            tipoBingos.setValorBingosList(new ArrayList<ValorBingos>());
        }
        if (tipoBingos.getProgramacionesList() == null) {
            tipoBingos.setProgramacionesList(new ArrayList<Programaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ValorBingos> attachedValorBingosList = new ArrayList<ValorBingos>();
            for (ValorBingos valorBingosListValorBingosToAttach : tipoBingos.getValorBingosList()) {
                valorBingosListValorBingosToAttach = em.getReference(valorBingosListValorBingosToAttach.getClass(), valorBingosListValorBingosToAttach.getId());
                attachedValorBingosList.add(valorBingosListValorBingosToAttach);
            }
            tipoBingos.setValorBingosList(attachedValorBingosList);
            List<Programaciones> attachedProgramacionesList = new ArrayList<Programaciones>();
            for (Programaciones programacionesListProgramacionesToAttach : tipoBingos.getProgramacionesList()) {
                programacionesListProgramacionesToAttach = em.getReference(programacionesListProgramacionesToAttach.getClass(), programacionesListProgramacionesToAttach.getId());
                attachedProgramacionesList.add(programacionesListProgramacionesToAttach);
            }
            tipoBingos.setProgramacionesList(attachedProgramacionesList);
            em.persist(tipoBingos);
            for (ValorBingos valorBingosListValorBingos : tipoBingos.getValorBingosList()) {
                TipoBingos oldIdTipoBingoOfValorBingosListValorBingos = valorBingosListValorBingos.getIdTipoBingo();
                valorBingosListValorBingos.setIdTipoBingo(tipoBingos);
                valorBingosListValorBingos = em.merge(valorBingosListValorBingos);
                if (oldIdTipoBingoOfValorBingosListValorBingos != null) {
                    oldIdTipoBingoOfValorBingosListValorBingos.getValorBingosList().remove(valorBingosListValorBingos);
                    oldIdTipoBingoOfValorBingosListValorBingos = em.merge(oldIdTipoBingoOfValorBingosListValorBingos);
                }
            }
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
            List<ValorBingos> valorBingosListOld = persistentTipoBingos.getValorBingosList();
            List<ValorBingos> valorBingosListNew = tipoBingos.getValorBingosList();
            List<Programaciones> programacionesListOld = persistentTipoBingos.getProgramacionesList();
            List<Programaciones> programacionesListNew = tipoBingos.getProgramacionesList();
            List<ValorBingos> attachedValorBingosListNew = new ArrayList<ValorBingos>();
            for (ValorBingos valorBingosListNewValorBingosToAttach : valorBingosListNew) {
                valorBingosListNewValorBingosToAttach = em.getReference(valorBingosListNewValorBingosToAttach.getClass(), valorBingosListNewValorBingosToAttach.getId());
                attachedValorBingosListNew.add(valorBingosListNewValorBingosToAttach);
            }
            valorBingosListNew = attachedValorBingosListNew;
            tipoBingos.setValorBingosList(valorBingosListNew);
            List<Programaciones> attachedProgramacionesListNew = new ArrayList<Programaciones>();
            for (Programaciones programacionesListNewProgramacionesToAttach : programacionesListNew) {
                programacionesListNewProgramacionesToAttach = em.getReference(programacionesListNewProgramacionesToAttach.getClass(), programacionesListNewProgramacionesToAttach.getId());
                attachedProgramacionesListNew.add(programacionesListNewProgramacionesToAttach);
            }
            programacionesListNew = attachedProgramacionesListNew;
            tipoBingos.setProgramacionesList(programacionesListNew);
            tipoBingos = em.merge(tipoBingos);
            for (ValorBingos valorBingosListOldValorBingos : valorBingosListOld) {
                if (!valorBingosListNew.contains(valorBingosListOldValorBingos)) {
                    valorBingosListOldValorBingos.setIdTipoBingo(null);
                    valorBingosListOldValorBingos = em.merge(valorBingosListOldValorBingos);
                }
            }
            for (ValorBingos valorBingosListNewValorBingos : valorBingosListNew) {
                if (!valorBingosListOld.contains(valorBingosListNewValorBingos)) {
                    TipoBingos oldIdTipoBingoOfValorBingosListNewValorBingos = valorBingosListNewValorBingos.getIdTipoBingo();
                    valorBingosListNewValorBingos.setIdTipoBingo(tipoBingos);
                    valorBingosListNewValorBingos = em.merge(valorBingosListNewValorBingos);
                    if (oldIdTipoBingoOfValorBingosListNewValorBingos != null && !oldIdTipoBingoOfValorBingosListNewValorBingos.equals(tipoBingos)) {
                        oldIdTipoBingoOfValorBingosListNewValorBingos.getValorBingosList().remove(valorBingosListNewValorBingos);
                        oldIdTipoBingoOfValorBingosListNewValorBingos = em.merge(oldIdTipoBingoOfValorBingosListNewValorBingos);
                    }
                }
            }
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
            List<ValorBingos> valorBingosList = tipoBingos.getValorBingosList();
            for (ValorBingos valorBingosListValorBingos : valorBingosList) {
                valorBingosListValorBingos.setIdTipoBingo(null);
                valorBingosListValorBingos = em.merge(valorBingosListValorBingos);
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
