/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingobi.persistence.controllers;

import bingobi.persistence.controllers.exceptions.NonexistentEntityException;
import bingobi.persistence.entities.Bingos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import bingobi.persistence.entities.Personas;
import bingobi.persistence.entities.Programaciones;
import bingobi.persistence.entities.Tablas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kbra
 */
public class BingosJpaController implements Serializable {

    public BingosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bingos bingos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personas idPersona = bingos.getIdPersona();
            if (idPersona != null) {
                idPersona = em.getReference(idPersona.getClass(), idPersona.getId());
                bingos.setIdPersona(idPersona);
            }
            Programaciones idProgramacion = bingos.getIdProgramacion();
            if (idProgramacion != null) {
                idProgramacion = em.getReference(idProgramacion.getClass(), idProgramacion.getId());
                bingos.setIdProgramacion(idProgramacion);
            }
            Tablas idTabla = bingos.getIdTabla();
            if (idTabla != null) {
                idTabla = em.getReference(idTabla.getClass(), idTabla.getId());
                bingos.setIdTabla(idTabla);
            }
            em.persist(bingos);
            if (idPersona != null) {
                idPersona.getBingosList().add(bingos);
                idPersona = em.merge(idPersona);
            }
            if (idProgramacion != null) {
                idProgramacion.getBingosList().add(bingos);
                idProgramacion = em.merge(idProgramacion);
            }
            if (idTabla != null) {
                idTabla.getBingosList().add(bingos);
                idTabla = em.merge(idTabla);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bingos bingos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bingos persistentBingos = em.find(Bingos.class, bingos.getId());
            Personas idPersonaOld = persistentBingos.getIdPersona();
            Personas idPersonaNew = bingos.getIdPersona();
            Programaciones idProgramacionOld = persistentBingos.getIdProgramacion();
            Programaciones idProgramacionNew = bingos.getIdProgramacion();
            Tablas idTablaOld = persistentBingos.getIdTabla();
            Tablas idTablaNew = bingos.getIdTabla();
            if (idPersonaNew != null) {
                idPersonaNew = em.getReference(idPersonaNew.getClass(), idPersonaNew.getId());
                bingos.setIdPersona(idPersonaNew);
            }
            if (idProgramacionNew != null) {
                idProgramacionNew = em.getReference(idProgramacionNew.getClass(), idProgramacionNew.getId());
                bingos.setIdProgramacion(idProgramacionNew);
            }
            if (idTablaNew != null) {
                idTablaNew = em.getReference(idTablaNew.getClass(), idTablaNew.getId());
                bingos.setIdTabla(idTablaNew);
            }
            bingos = em.merge(bingos);
            if (idPersonaOld != null && !idPersonaOld.equals(idPersonaNew)) {
                idPersonaOld.getBingosList().remove(bingos);
                idPersonaOld = em.merge(idPersonaOld);
            }
            if (idPersonaNew != null && !idPersonaNew.equals(idPersonaOld)) {
                idPersonaNew.getBingosList().add(bingos);
                idPersonaNew = em.merge(idPersonaNew);
            }
            if (idProgramacionOld != null && !idProgramacionOld.equals(idProgramacionNew)) {
                idProgramacionOld.getBingosList().remove(bingos);
                idProgramacionOld = em.merge(idProgramacionOld);
            }
            if (idProgramacionNew != null && !idProgramacionNew.equals(idProgramacionOld)) {
                idProgramacionNew.getBingosList().add(bingos);
                idProgramacionNew = em.merge(idProgramacionNew);
            }
            if (idTablaOld != null && !idTablaOld.equals(idTablaNew)) {
                idTablaOld.getBingosList().remove(bingos);
                idTablaOld = em.merge(idTablaOld);
            }
            if (idTablaNew != null && !idTablaNew.equals(idTablaOld)) {
                idTablaNew.getBingosList().add(bingos);
                idTablaNew = em.merge(idTablaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bingos.getId();
                if (findBingos(id) == null) {
                    throw new NonexistentEntityException("The bingos with id " + id + " no longer exists.");
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
            Bingos bingos;
            try {
                bingos = em.getReference(Bingos.class, id);
                bingos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bingos with id " + id + " no longer exists.", enfe);
            }
            Personas idPersona = bingos.getIdPersona();
            if (idPersona != null) {
                idPersona.getBingosList().remove(bingos);
                idPersona = em.merge(idPersona);
            }
            Programaciones idProgramacion = bingos.getIdProgramacion();
            if (idProgramacion != null) {
                idProgramacion.getBingosList().remove(bingos);
                idProgramacion = em.merge(idProgramacion);
            }
            Tablas idTabla = bingos.getIdTabla();
            if (idTabla != null) {
                idTabla.getBingosList().remove(bingos);
                idTabla = em.merge(idTabla);
            }
            em.remove(bingos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bingos> findBingosEntities() {
        return findBingosEntities(true, -1, -1);
    }

    public List<Bingos> findBingosEntities(int maxResults, int firstResult) {
        return findBingosEntities(false, maxResults, firstResult);
    }

    private List<Bingos> findBingosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bingos.class));
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

    public Bingos findBingos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bingos.class, id);
        } finally {
            em.close();
        }
    }

    public int getBingosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bingos> rt = cq.from(Bingos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
