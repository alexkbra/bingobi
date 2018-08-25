/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingobi.persistence.controllers;

import bingobi.persistence.controllers.exceptions.NonexistentEntityException;
import bingobi.persistence.entities.Dias;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import bingobi.persistence.entities.Horarios;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kbra
 */
public class DiasJpaController implements Serializable {

    public DiasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dias dias) {
        if (dias.getHorariosList() == null) {
            dias.setHorariosList(new ArrayList<Horarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Horarios> attachedHorariosList = new ArrayList<Horarios>();
            for (Horarios horariosListHorariosToAttach : dias.getHorariosList()) {
                horariosListHorariosToAttach = em.getReference(horariosListHorariosToAttach.getClass(), horariosListHorariosToAttach.getId());
                attachedHorariosList.add(horariosListHorariosToAttach);
            }
            dias.setHorariosList(attachedHorariosList);
            em.persist(dias);
            for (Horarios horariosListHorarios : dias.getHorariosList()) {
                Dias oldIdDiaOfHorariosListHorarios = horariosListHorarios.getIdDia();
                horariosListHorarios.setIdDia(dias);
                horariosListHorarios = em.merge(horariosListHorarios);
                if (oldIdDiaOfHorariosListHorarios != null) {
                    oldIdDiaOfHorariosListHorarios.getHorariosList().remove(horariosListHorarios);
                    oldIdDiaOfHorariosListHorarios = em.merge(oldIdDiaOfHorariosListHorarios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dias dias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dias persistentDias = em.find(Dias.class, dias.getId());
            List<Horarios> horariosListOld = persistentDias.getHorariosList();
            List<Horarios> horariosListNew = dias.getHorariosList();
            List<Horarios> attachedHorariosListNew = new ArrayList<Horarios>();
            for (Horarios horariosListNewHorariosToAttach : horariosListNew) {
                horariosListNewHorariosToAttach = em.getReference(horariosListNewHorariosToAttach.getClass(), horariosListNewHorariosToAttach.getId());
                attachedHorariosListNew.add(horariosListNewHorariosToAttach);
            }
            horariosListNew = attachedHorariosListNew;
            dias.setHorariosList(horariosListNew);
            dias = em.merge(dias);
            for (Horarios horariosListOldHorarios : horariosListOld) {
                if (!horariosListNew.contains(horariosListOldHorarios)) {
                    horariosListOldHorarios.setIdDia(null);
                    horariosListOldHorarios = em.merge(horariosListOldHorarios);
                }
            }
            for (Horarios horariosListNewHorarios : horariosListNew) {
                if (!horariosListOld.contains(horariosListNewHorarios)) {
                    Dias oldIdDiaOfHorariosListNewHorarios = horariosListNewHorarios.getIdDia();
                    horariosListNewHorarios.setIdDia(dias);
                    horariosListNewHorarios = em.merge(horariosListNewHorarios);
                    if (oldIdDiaOfHorariosListNewHorarios != null && !oldIdDiaOfHorariosListNewHorarios.equals(dias)) {
                        oldIdDiaOfHorariosListNewHorarios.getHorariosList().remove(horariosListNewHorarios);
                        oldIdDiaOfHorariosListNewHorarios = em.merge(oldIdDiaOfHorariosListNewHorarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dias.getId();
                if (findDias(id) == null) {
                    throw new NonexistentEntityException("The dias with id " + id + " no longer exists.");
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
            Dias dias;
            try {
                dias = em.getReference(Dias.class, id);
                dias.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dias with id " + id + " no longer exists.", enfe);
            }
            List<Horarios> horariosList = dias.getHorariosList();
            for (Horarios horariosListHorarios : horariosList) {
                horariosListHorarios.setIdDia(null);
                horariosListHorarios = em.merge(horariosListHorarios);
            }
            em.remove(dias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dias> findDiasEntities() {
        return findDiasEntities(true, -1, -1);
    }

    public List<Dias> findDiasEntities(int maxResults, int firstResult) {
        return findDiasEntities(false, maxResults, firstResult);
    }

    private List<Dias> findDiasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dias.class));
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

    public Dias findDias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dias.class, id);
        } finally {
            em.close();
        }
    }

    public int getDiasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dias> rt = cq.from(Dias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
