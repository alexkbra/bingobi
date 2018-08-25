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
import bingobi.persistence.entities.Bingos;
import bingobi.persistence.entities.Personas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kbra
 */
public class PersonasJpaController implements Serializable {

    public PersonasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Personas personas) {
        if (personas.getBingosList() == null) {
            personas.setBingosList(new ArrayList<Bingos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Bingos> attachedBingosList = new ArrayList<Bingos>();
            for (Bingos bingosListBingosToAttach : personas.getBingosList()) {
                bingosListBingosToAttach = em.getReference(bingosListBingosToAttach.getClass(), bingosListBingosToAttach.getId());
                attachedBingosList.add(bingosListBingosToAttach);
            }
            personas.setBingosList(attachedBingosList);
            em.persist(personas);
            for (Bingos bingosListBingos : personas.getBingosList()) {
                Personas oldIdPersonaOfBingosListBingos = bingosListBingos.getIdPersona();
                bingosListBingos.setIdPersona(personas);
                bingosListBingos = em.merge(bingosListBingos);
                if (oldIdPersonaOfBingosListBingos != null) {
                    oldIdPersonaOfBingosListBingos.getBingosList().remove(bingosListBingos);
                    oldIdPersonaOfBingosListBingos = em.merge(oldIdPersonaOfBingosListBingos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Personas personas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personas persistentPersonas = em.find(Personas.class, personas.getId());
            List<Bingos> bingosListOld = persistentPersonas.getBingosList();
            List<Bingos> bingosListNew = personas.getBingosList();
            List<Bingos> attachedBingosListNew = new ArrayList<Bingos>();
            for (Bingos bingosListNewBingosToAttach : bingosListNew) {
                bingosListNewBingosToAttach = em.getReference(bingosListNewBingosToAttach.getClass(), bingosListNewBingosToAttach.getId());
                attachedBingosListNew.add(bingosListNewBingosToAttach);
            }
            bingosListNew = attachedBingosListNew;
            personas.setBingosList(bingosListNew);
            personas = em.merge(personas);
            for (Bingos bingosListOldBingos : bingosListOld) {
                if (!bingosListNew.contains(bingosListOldBingos)) {
                    bingosListOldBingos.setIdPersona(null);
                    bingosListOldBingos = em.merge(bingosListOldBingos);
                }
            }
            for (Bingos bingosListNewBingos : bingosListNew) {
                if (!bingosListOld.contains(bingosListNewBingos)) {
                    Personas oldIdPersonaOfBingosListNewBingos = bingosListNewBingos.getIdPersona();
                    bingosListNewBingos.setIdPersona(personas);
                    bingosListNewBingos = em.merge(bingosListNewBingos);
                    if (oldIdPersonaOfBingosListNewBingos != null && !oldIdPersonaOfBingosListNewBingos.equals(personas)) {
                        oldIdPersonaOfBingosListNewBingos.getBingosList().remove(bingosListNewBingos);
                        oldIdPersonaOfBingosListNewBingos = em.merge(oldIdPersonaOfBingosListNewBingos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = personas.getId();
                if (findPersonas(id) == null) {
                    throw new NonexistentEntityException("The personas with id " + id + " no longer exists.");
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
            Personas personas;
            try {
                personas = em.getReference(Personas.class, id);
                personas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personas with id " + id + " no longer exists.", enfe);
            }
            List<Bingos> bingosList = personas.getBingosList();
            for (Bingos bingosListBingos : bingosList) {
                bingosListBingos.setIdPersona(null);
                bingosListBingos = em.merge(bingosListBingos);
            }
            em.remove(personas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Personas> findPersonasEntities() {
        return findPersonasEntities(true, -1, -1);
    }

    public List<Personas> findPersonasEntities(int maxResults, int firstResult) {
        return findPersonasEntities(false, maxResults, firstResult);
    }

    private List<Personas> findPersonasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Personas.class));
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

    public Personas findPersonas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Personas.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Personas> rt = cq.from(Personas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
