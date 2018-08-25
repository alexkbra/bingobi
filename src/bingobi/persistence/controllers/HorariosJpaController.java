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
import bingobi.persistence.entities.Dias;
import bingobi.persistence.entities.Horarios;
import bingobi.persistence.entities.Programaciones;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kbra
 */
public class HorariosJpaController implements Serializable {

    public HorariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Horarios horarios) {
        if (horarios.getProgramacionesList() == null) {
            horarios.setProgramacionesList(new ArrayList<Programaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dias idDia = horarios.getIdDia();
            if (idDia != null) {
                idDia = em.getReference(idDia.getClass(), idDia.getId());
                horarios.setIdDia(idDia);
            }
            List<Programaciones> attachedProgramacionesList = new ArrayList<Programaciones>();
            for (Programaciones programacionesListProgramacionesToAttach : horarios.getProgramacionesList()) {
                programacionesListProgramacionesToAttach = em.getReference(programacionesListProgramacionesToAttach.getClass(), programacionesListProgramacionesToAttach.getId());
                attachedProgramacionesList.add(programacionesListProgramacionesToAttach);
            }
            horarios.setProgramacionesList(attachedProgramacionesList);
            em.persist(horarios);
            if (idDia != null) {
                idDia.getHorariosList().add(horarios);
                idDia = em.merge(idDia);
            }
            for (Programaciones programacionesListProgramaciones : horarios.getProgramacionesList()) {
                Horarios oldIdHorarioOfProgramacionesListProgramaciones = programacionesListProgramaciones.getIdHorario();
                programacionesListProgramaciones.setIdHorario(horarios);
                programacionesListProgramaciones = em.merge(programacionesListProgramaciones);
                if (oldIdHorarioOfProgramacionesListProgramaciones != null) {
                    oldIdHorarioOfProgramacionesListProgramaciones.getProgramacionesList().remove(programacionesListProgramaciones);
                    oldIdHorarioOfProgramacionesListProgramaciones = em.merge(oldIdHorarioOfProgramacionesListProgramaciones);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Horarios horarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Horarios persistentHorarios = em.find(Horarios.class, horarios.getId());
            Dias idDiaOld = persistentHorarios.getIdDia();
            Dias idDiaNew = horarios.getIdDia();
            List<Programaciones> programacionesListOld = persistentHorarios.getProgramacionesList();
            List<Programaciones> programacionesListNew = horarios.getProgramacionesList();
            if (idDiaNew != null) {
                idDiaNew = em.getReference(idDiaNew.getClass(), idDiaNew.getId());
                horarios.setIdDia(idDiaNew);
            }
            List<Programaciones> attachedProgramacionesListNew = new ArrayList<Programaciones>();
            for (Programaciones programacionesListNewProgramacionesToAttach : programacionesListNew) {
                programacionesListNewProgramacionesToAttach = em.getReference(programacionesListNewProgramacionesToAttach.getClass(), programacionesListNewProgramacionesToAttach.getId());
                attachedProgramacionesListNew.add(programacionesListNewProgramacionesToAttach);
            }
            programacionesListNew = attachedProgramacionesListNew;
            horarios.setProgramacionesList(programacionesListNew);
            horarios = em.merge(horarios);
            if (idDiaOld != null && !idDiaOld.equals(idDiaNew)) {
                idDiaOld.getHorariosList().remove(horarios);
                idDiaOld = em.merge(idDiaOld);
            }
            if (idDiaNew != null && !idDiaNew.equals(idDiaOld)) {
                idDiaNew.getHorariosList().add(horarios);
                idDiaNew = em.merge(idDiaNew);
            }
            for (Programaciones programacionesListOldProgramaciones : programacionesListOld) {
                if (!programacionesListNew.contains(programacionesListOldProgramaciones)) {
                    programacionesListOldProgramaciones.setIdHorario(null);
                    programacionesListOldProgramaciones = em.merge(programacionesListOldProgramaciones);
                }
            }
            for (Programaciones programacionesListNewProgramaciones : programacionesListNew) {
                if (!programacionesListOld.contains(programacionesListNewProgramaciones)) {
                    Horarios oldIdHorarioOfProgramacionesListNewProgramaciones = programacionesListNewProgramaciones.getIdHorario();
                    programacionesListNewProgramaciones.setIdHorario(horarios);
                    programacionesListNewProgramaciones = em.merge(programacionesListNewProgramaciones);
                    if (oldIdHorarioOfProgramacionesListNewProgramaciones != null && !oldIdHorarioOfProgramacionesListNewProgramaciones.equals(horarios)) {
                        oldIdHorarioOfProgramacionesListNewProgramaciones.getProgramacionesList().remove(programacionesListNewProgramaciones);
                        oldIdHorarioOfProgramacionesListNewProgramaciones = em.merge(oldIdHorarioOfProgramacionesListNewProgramaciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = horarios.getId();
                if (findHorarios(id) == null) {
                    throw new NonexistentEntityException("The horarios with id " + id + " no longer exists.");
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
            Horarios horarios;
            try {
                horarios = em.getReference(Horarios.class, id);
                horarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The horarios with id " + id + " no longer exists.", enfe);
            }
            Dias idDia = horarios.getIdDia();
            if (idDia != null) {
                idDia.getHorariosList().remove(horarios);
                idDia = em.merge(idDia);
            }
            List<Programaciones> programacionesList = horarios.getProgramacionesList();
            for (Programaciones programacionesListProgramaciones : programacionesList) {
                programacionesListProgramaciones.setIdHorario(null);
                programacionesListProgramaciones = em.merge(programacionesListProgramaciones);
            }
            em.remove(horarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Horarios> findHorariosEntities() {
        return findHorariosEntities(true, -1, -1);
    }

    public List<Horarios> findHorariosEntities(int maxResults, int firstResult) {
        return findHorariosEntities(false, maxResults, firstResult);
    }

    private List<Horarios> findHorariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Horarios.class));
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

    public Horarios findHorarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Horarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getHorariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Horarios> rt = cq.from(Horarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
