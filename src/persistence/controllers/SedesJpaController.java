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
import persistence.entity.Programaciones;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistence.controllers.exceptions.NonexistentEntityException;
import persistence.entity.Sedes;

/**
 *
 * @author kbra
 */
public class SedesJpaController implements Serializable {

    public SedesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sedes sedes) {
        if (sedes.getProgramacionesList() == null) {
            sedes.setProgramacionesList(new ArrayList<Programaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Programaciones> attachedProgramacionesList = new ArrayList<Programaciones>();
            for (Programaciones programacionesListProgramacionesToAttach : sedes.getProgramacionesList()) {
                programacionesListProgramacionesToAttach = em.getReference(programacionesListProgramacionesToAttach.getClass(), programacionesListProgramacionesToAttach.getId());
                attachedProgramacionesList.add(programacionesListProgramacionesToAttach);
            }
            sedes.setProgramacionesList(attachedProgramacionesList);
            em.persist(sedes);
            for (Programaciones programacionesListProgramaciones : sedes.getProgramacionesList()) {
                Sedes oldIdSedeOfProgramacionesListProgramaciones = programacionesListProgramaciones.getIdSede();
                programacionesListProgramaciones.setIdSede(sedes);
                programacionesListProgramaciones = em.merge(programacionesListProgramaciones);
                if (oldIdSedeOfProgramacionesListProgramaciones != null) {
                    oldIdSedeOfProgramacionesListProgramaciones.getProgramacionesList().remove(programacionesListProgramaciones);
                    oldIdSedeOfProgramacionesListProgramaciones = em.merge(oldIdSedeOfProgramacionesListProgramaciones);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sedes sedes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sedes persistentSedes = em.find(Sedes.class, sedes.getId());
            List<Programaciones> programacionesListOld = persistentSedes.getProgramacionesList();
            List<Programaciones> programacionesListNew = sedes.getProgramacionesList();
            List<Programaciones> attachedProgramacionesListNew = new ArrayList<Programaciones>();
            for (Programaciones programacionesListNewProgramacionesToAttach : programacionesListNew) {
                programacionesListNewProgramacionesToAttach = em.getReference(programacionesListNewProgramacionesToAttach.getClass(), programacionesListNewProgramacionesToAttach.getId());
                attachedProgramacionesListNew.add(programacionesListNewProgramacionesToAttach);
            }
            programacionesListNew = attachedProgramacionesListNew;
            sedes.setProgramacionesList(programacionesListNew);
            sedes = em.merge(sedes);
            for (Programaciones programacionesListOldProgramaciones : programacionesListOld) {
                if (!programacionesListNew.contains(programacionesListOldProgramaciones)) {
                    programacionesListOldProgramaciones.setIdSede(null);
                    programacionesListOldProgramaciones = em.merge(programacionesListOldProgramaciones);
                }
            }
            for (Programaciones programacionesListNewProgramaciones : programacionesListNew) {
                if (!programacionesListOld.contains(programacionesListNewProgramaciones)) {
                    Sedes oldIdSedeOfProgramacionesListNewProgramaciones = programacionesListNewProgramaciones.getIdSede();
                    programacionesListNewProgramaciones.setIdSede(sedes);
                    programacionesListNewProgramaciones = em.merge(programacionesListNewProgramaciones);
                    if (oldIdSedeOfProgramacionesListNewProgramaciones != null && !oldIdSedeOfProgramacionesListNewProgramaciones.equals(sedes)) {
                        oldIdSedeOfProgramacionesListNewProgramaciones.getProgramacionesList().remove(programacionesListNewProgramaciones);
                        oldIdSedeOfProgramacionesListNewProgramaciones = em.merge(oldIdSedeOfProgramacionesListNewProgramaciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sedes.getId();
                if (findSedes(id) == null) {
                    throw new NonexistentEntityException("The sedes with id " + id + " no longer exists.");
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
            Sedes sedes;
            try {
                sedes = em.getReference(Sedes.class, id);
                sedes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sedes with id " + id + " no longer exists.", enfe);
            }
            List<Programaciones> programacionesList = sedes.getProgramacionesList();
            for (Programaciones programacionesListProgramaciones : programacionesList) {
                programacionesListProgramaciones.setIdSede(null);
                programacionesListProgramaciones = em.merge(programacionesListProgramaciones);
            }
            em.remove(sedes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sedes> findSedesEntities() {
        return findSedesEntities(true, -1, -1);
    }

    public List<Sedes> findSedesEntities(int maxResults, int firstResult) {
        return findSedesEntities(false, maxResults, firstResult);
    }

    private List<Sedes> findSedesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sedes.class));
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

    public Sedes findSedes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sedes.class, id);
        } finally {
            em.close();
        }
    }

    public int getSedesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sedes> rt = cq.from(Sedes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
