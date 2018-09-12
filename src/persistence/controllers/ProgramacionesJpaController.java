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
import persistence.entity.Horarios;
import persistence.entity.Sedes;
import persistence.entity.TipoBingos;
import persistence.entity.Bingos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistence.controllers.exceptions.NonexistentEntityException;
import persistence.entity.ControlProgramación;
import persistence.entity.Programaciones;

/**
 *
 * @author kbra
 */
public class ProgramacionesJpaController implements Serializable {

    public ProgramacionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Programaciones programaciones) {
        if (programaciones.getBingosList() == null) {
            programaciones.setBingosList(new ArrayList<Bingos>());
        }
        if (programaciones.getControlProgramaciónList() == null) {
            programaciones.setControlProgramaciónList(new ArrayList<ControlProgramación>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Horarios idHorario = programaciones.getIdHorario();
            if (idHorario != null) {
                idHorario = em.getReference(idHorario.getClass(), idHorario.getId());
                programaciones.setIdHorario(idHorario);
            }
            Sedes idSede = programaciones.getIdSede();
            if (idSede != null) {
                idSede = em.getReference(idSede.getClass(), idSede.getId());
                programaciones.setIdSede(idSede);
            }
            TipoBingos idTipoBingo = programaciones.getIdTipoBingo();
            if (idTipoBingo != null) {
                idTipoBingo = em.getReference(idTipoBingo.getClass(), idTipoBingo.getId());
                programaciones.setIdTipoBingo(idTipoBingo);
            }
            List<Bingos> attachedBingosList = new ArrayList<Bingos>();
            for (Bingos bingosListBingosToAttach : programaciones.getBingosList()) {
                bingosListBingosToAttach = em.getReference(bingosListBingosToAttach.getClass(), bingosListBingosToAttach.getId());
                attachedBingosList.add(bingosListBingosToAttach);
            }
            programaciones.setBingosList(attachedBingosList);
            List<ControlProgramación> attachedControlProgramaciónList = new ArrayList<ControlProgramación>();
            for (ControlProgramación controlProgramaciónListControlProgramaciónToAttach : programaciones.getControlProgramaciónList()) {
                controlProgramaciónListControlProgramaciónToAttach = em.getReference(controlProgramaciónListControlProgramaciónToAttach.getClass(), controlProgramaciónListControlProgramaciónToAttach.getId());
                attachedControlProgramaciónList.add(controlProgramaciónListControlProgramaciónToAttach);
            }
            programaciones.setControlProgramaciónList(attachedControlProgramaciónList);
            em.persist(programaciones);
            if (idHorario != null) {
                idHorario.getProgramacionesList().add(programaciones);
                idHorario = em.merge(idHorario);
            }
            if (idSede != null) {
                idSede.getProgramacionesList().add(programaciones);
                idSede = em.merge(idSede);
            }
            if (idTipoBingo != null) {
                idTipoBingo.getProgramacionesList().add(programaciones);
                idTipoBingo = em.merge(idTipoBingo);
            }
            for (Bingos bingosListBingos : programaciones.getBingosList()) {
                Programaciones oldIdProgramacionOfBingosListBingos = bingosListBingos.getIdProgramacion();
                bingosListBingos.setIdProgramacion(programaciones);
                bingosListBingos = em.merge(bingosListBingos);
                if (oldIdProgramacionOfBingosListBingos != null) {
                    oldIdProgramacionOfBingosListBingos.getBingosList().remove(bingosListBingos);
                    oldIdProgramacionOfBingosListBingos = em.merge(oldIdProgramacionOfBingosListBingos);
                }
            }
            for (ControlProgramación controlProgramaciónListControlProgramación : programaciones.getControlProgramaciónList()) {
                Programaciones oldIdProgramacionOfControlProgramaciónListControlProgramación = controlProgramaciónListControlProgramación.getIdProgramacion();
                controlProgramaciónListControlProgramación.setIdProgramacion(programaciones);
                controlProgramaciónListControlProgramación = em.merge(controlProgramaciónListControlProgramación);
                if (oldIdProgramacionOfControlProgramaciónListControlProgramación != null) {
                    oldIdProgramacionOfControlProgramaciónListControlProgramación.getControlProgramaciónList().remove(controlProgramaciónListControlProgramación);
                    oldIdProgramacionOfControlProgramaciónListControlProgramación = em.merge(oldIdProgramacionOfControlProgramaciónListControlProgramación);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Programaciones programaciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Programaciones persistentProgramaciones = em.find(Programaciones.class, programaciones.getId());
            Horarios idHorarioOld = persistentProgramaciones.getIdHorario();
            Horarios idHorarioNew = programaciones.getIdHorario();
            Sedes idSedeOld = persistentProgramaciones.getIdSede();
            Sedes idSedeNew = programaciones.getIdSede();
            TipoBingos idTipoBingoOld = persistentProgramaciones.getIdTipoBingo();
            TipoBingos idTipoBingoNew = programaciones.getIdTipoBingo();
            List<Bingos> bingosListOld = persistentProgramaciones.getBingosList();
            List<Bingos> bingosListNew = programaciones.getBingosList();
            List<ControlProgramación> controlProgramaciónListOld = persistentProgramaciones.getControlProgramaciónList();
            List<ControlProgramación> controlProgramaciónListNew = programaciones.getControlProgramaciónList();
            if (idHorarioNew != null) {
                idHorarioNew = em.getReference(idHorarioNew.getClass(), idHorarioNew.getId());
                programaciones.setIdHorario(idHorarioNew);
            }
            if (idSedeNew != null) {
                idSedeNew = em.getReference(idSedeNew.getClass(), idSedeNew.getId());
                programaciones.setIdSede(idSedeNew);
            }
            if (idTipoBingoNew != null) {
                idTipoBingoNew = em.getReference(idTipoBingoNew.getClass(), idTipoBingoNew.getId());
                programaciones.setIdTipoBingo(idTipoBingoNew);
            }
            List<Bingos> attachedBingosListNew = new ArrayList<Bingos>();
            for (Bingos bingosListNewBingosToAttach : bingosListNew) {
                bingosListNewBingosToAttach = em.getReference(bingosListNewBingosToAttach.getClass(), bingosListNewBingosToAttach.getId());
                attachedBingosListNew.add(bingosListNewBingosToAttach);
            }
            bingosListNew = attachedBingosListNew;
            programaciones.setBingosList(bingosListNew);
            List<ControlProgramación> attachedControlProgramaciónListNew = new ArrayList<ControlProgramación>();
            for (ControlProgramación controlProgramaciónListNewControlProgramaciónToAttach : controlProgramaciónListNew) {
                controlProgramaciónListNewControlProgramaciónToAttach = em.getReference(controlProgramaciónListNewControlProgramaciónToAttach.getClass(), controlProgramaciónListNewControlProgramaciónToAttach.getId());
                attachedControlProgramaciónListNew.add(controlProgramaciónListNewControlProgramaciónToAttach);
            }
            controlProgramaciónListNew = attachedControlProgramaciónListNew;
            programaciones.setControlProgramaciónList(controlProgramaciónListNew);
            programaciones = em.merge(programaciones);
            if (idHorarioOld != null && !idHorarioOld.equals(idHorarioNew)) {
                idHorarioOld.getProgramacionesList().remove(programaciones);
                idHorarioOld = em.merge(idHorarioOld);
            }
            if (idHorarioNew != null && !idHorarioNew.equals(idHorarioOld)) {
                idHorarioNew.getProgramacionesList().add(programaciones);
                idHorarioNew = em.merge(idHorarioNew);
            }
            if (idSedeOld != null && !idSedeOld.equals(idSedeNew)) {
                idSedeOld.getProgramacionesList().remove(programaciones);
                idSedeOld = em.merge(idSedeOld);
            }
            if (idSedeNew != null && !idSedeNew.equals(idSedeOld)) {
                idSedeNew.getProgramacionesList().add(programaciones);
                idSedeNew = em.merge(idSedeNew);
            }
            if (idTipoBingoOld != null && !idTipoBingoOld.equals(idTipoBingoNew)) {
                idTipoBingoOld.getProgramacionesList().remove(programaciones);
                idTipoBingoOld = em.merge(idTipoBingoOld);
            }
            if (idTipoBingoNew != null && !idTipoBingoNew.equals(idTipoBingoOld)) {
                idTipoBingoNew.getProgramacionesList().add(programaciones);
                idTipoBingoNew = em.merge(idTipoBingoNew);
            }
            for (Bingos bingosListOldBingos : bingosListOld) {
                if (!bingosListNew.contains(bingosListOldBingos)) {
                    bingosListOldBingos.setIdProgramacion(null);
                    bingosListOldBingos = em.merge(bingosListOldBingos);
                }
            }
            for (Bingos bingosListNewBingos : bingosListNew) {
                if (!bingosListOld.contains(bingosListNewBingos)) {
                    Programaciones oldIdProgramacionOfBingosListNewBingos = bingosListNewBingos.getIdProgramacion();
                    bingosListNewBingos.setIdProgramacion(programaciones);
                    bingosListNewBingos = em.merge(bingosListNewBingos);
                    if (oldIdProgramacionOfBingosListNewBingos != null && !oldIdProgramacionOfBingosListNewBingos.equals(programaciones)) {
                        oldIdProgramacionOfBingosListNewBingos.getBingosList().remove(bingosListNewBingos);
                        oldIdProgramacionOfBingosListNewBingos = em.merge(oldIdProgramacionOfBingosListNewBingos);
                    }
                }
            }
            for (ControlProgramación controlProgramaciónListOldControlProgramación : controlProgramaciónListOld) {
                if (!controlProgramaciónListNew.contains(controlProgramaciónListOldControlProgramación)) {
                    controlProgramaciónListOldControlProgramación.setIdProgramacion(null);
                    controlProgramaciónListOldControlProgramación = em.merge(controlProgramaciónListOldControlProgramación);
                }
            }
            for (ControlProgramación controlProgramaciónListNewControlProgramación : controlProgramaciónListNew) {
                if (!controlProgramaciónListOld.contains(controlProgramaciónListNewControlProgramación)) {
                    Programaciones oldIdProgramacionOfControlProgramaciónListNewControlProgramación = controlProgramaciónListNewControlProgramación.getIdProgramacion();
                    controlProgramaciónListNewControlProgramación.setIdProgramacion(programaciones);
                    controlProgramaciónListNewControlProgramación = em.merge(controlProgramaciónListNewControlProgramación);
                    if (oldIdProgramacionOfControlProgramaciónListNewControlProgramación != null && !oldIdProgramacionOfControlProgramaciónListNewControlProgramación.equals(programaciones)) {
                        oldIdProgramacionOfControlProgramaciónListNewControlProgramación.getControlProgramaciónList().remove(controlProgramaciónListNewControlProgramación);
                        oldIdProgramacionOfControlProgramaciónListNewControlProgramación = em.merge(oldIdProgramacionOfControlProgramaciónListNewControlProgramación);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = programaciones.getId();
                if (findProgramaciones(id) == null) {
                    throw new NonexistentEntityException("The programaciones with id " + id + " no longer exists.");
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
            Programaciones programaciones;
            try {
                programaciones = em.getReference(Programaciones.class, id);
                programaciones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The programaciones with id " + id + " no longer exists.", enfe);
            }
            Horarios idHorario = programaciones.getIdHorario();
            if (idHorario != null) {
                idHorario.getProgramacionesList().remove(programaciones);
                idHorario = em.merge(idHorario);
            }
            Sedes idSede = programaciones.getIdSede();
            if (idSede != null) {
                idSede.getProgramacionesList().remove(programaciones);
                idSede = em.merge(idSede);
            }
            TipoBingos idTipoBingo = programaciones.getIdTipoBingo();
            if (idTipoBingo != null) {
                idTipoBingo.getProgramacionesList().remove(programaciones);
                idTipoBingo = em.merge(idTipoBingo);
            }
            List<Bingos> bingosList = programaciones.getBingosList();
            for (Bingos bingosListBingos : bingosList) {
                bingosListBingos.setIdProgramacion(null);
                bingosListBingos = em.merge(bingosListBingos);
            }
            List<ControlProgramación> controlProgramaciónList = programaciones.getControlProgramaciónList();
            for (ControlProgramación controlProgramaciónListControlProgramación : controlProgramaciónList) {
                controlProgramaciónListControlProgramación.setIdProgramacion(null);
                controlProgramaciónListControlProgramación = em.merge(controlProgramaciónListControlProgramación);
            }
            em.remove(programaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Programaciones> findProgramacionesEntities() {
        return findProgramacionesEntities(true, -1, -1);
    }

    public List<Programaciones> findProgramacionesEntities(int maxResults, int firstResult) {
        return findProgramacionesEntities(false, maxResults, firstResult);
    }

    private List<Programaciones> findProgramacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Programaciones.class));
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

    public Programaciones findProgramaciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Programaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getProgramacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Programaciones> rt = cq.from(Programaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
