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
import persistence.entity.Personas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistence.controllers.exceptions.IllegalOrphanException;
import persistence.controllers.exceptions.NonexistentEntityException;
import persistence.entity.TipoDocumento;

/**
 *
 * @author kbra
 */
public class TipoDocumentoJpaController implements Serializable {

    public TipoDocumentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoDocumento tipoDocumento) {
        if (tipoDocumento.getPersonasList() == null) {
            tipoDocumento.setPersonasList(new ArrayList<Personas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Personas> attachedPersonasList = new ArrayList<Personas>();
            for (Personas personasListPersonasToAttach : tipoDocumento.getPersonasList()) {
                personasListPersonasToAttach = em.getReference(personasListPersonasToAttach.getClass(), personasListPersonasToAttach.getId());
                attachedPersonasList.add(personasListPersonasToAttach);
            }
            tipoDocumento.setPersonasList(attachedPersonasList);
            em.persist(tipoDocumento);
            for (Personas personasListPersonas : tipoDocumento.getPersonasList()) {
                TipoDocumento oldIdTipodocumentoOfPersonasListPersonas = personasListPersonas.getIdTipodocumento();
                personasListPersonas.setIdTipodocumento(tipoDocumento);
                personasListPersonas = em.merge(personasListPersonas);
                if (oldIdTipodocumentoOfPersonasListPersonas != null) {
                    oldIdTipodocumentoOfPersonasListPersonas.getPersonasList().remove(personasListPersonas);
                    oldIdTipodocumentoOfPersonasListPersonas = em.merge(oldIdTipodocumentoOfPersonasListPersonas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoDocumento tipoDocumento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoDocumento persistentTipoDocumento = em.find(TipoDocumento.class, tipoDocumento.getId());
            List<Personas> personasListOld = persistentTipoDocumento.getPersonasList();
            List<Personas> personasListNew = tipoDocumento.getPersonasList();
            List<String> illegalOrphanMessages = null;
            for (Personas personasListOldPersonas : personasListOld) {
                if (!personasListNew.contains(personasListOldPersonas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Personas " + personasListOldPersonas + " since its idTipodocumento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Personas> attachedPersonasListNew = new ArrayList<Personas>();
            for (Personas personasListNewPersonasToAttach : personasListNew) {
                personasListNewPersonasToAttach = em.getReference(personasListNewPersonasToAttach.getClass(), personasListNewPersonasToAttach.getId());
                attachedPersonasListNew.add(personasListNewPersonasToAttach);
            }
            personasListNew = attachedPersonasListNew;
            tipoDocumento.setPersonasList(personasListNew);
            tipoDocumento = em.merge(tipoDocumento);
            for (Personas personasListNewPersonas : personasListNew) {
                if (!personasListOld.contains(personasListNewPersonas)) {
                    TipoDocumento oldIdTipodocumentoOfPersonasListNewPersonas = personasListNewPersonas.getIdTipodocumento();
                    personasListNewPersonas.setIdTipodocumento(tipoDocumento);
                    personasListNewPersonas = em.merge(personasListNewPersonas);
                    if (oldIdTipodocumentoOfPersonasListNewPersonas != null && !oldIdTipodocumentoOfPersonasListNewPersonas.equals(tipoDocumento)) {
                        oldIdTipodocumentoOfPersonasListNewPersonas.getPersonasList().remove(personasListNewPersonas);
                        oldIdTipodocumentoOfPersonasListNewPersonas = em.merge(oldIdTipodocumentoOfPersonasListNewPersonas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoDocumento.getId();
                if (findTipoDocumento(id) == null) {
                    throw new NonexistentEntityException("The tipoDocumento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoDocumento tipoDocumento;
            try {
                tipoDocumento = em.getReference(TipoDocumento.class, id);
                tipoDocumento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoDocumento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Personas> personasListOrphanCheck = tipoDocumento.getPersonasList();
            for (Personas personasListOrphanCheckPersonas : personasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoDocumento (" + tipoDocumento + ") cannot be destroyed since the Personas " + personasListOrphanCheckPersonas + " in its personasList field has a non-nullable idTipodocumento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoDocumento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoDocumento> findTipoDocumentoEntities() {
        return findTipoDocumentoEntities(true, -1, -1);
    }

    public List<TipoDocumento> findTipoDocumentoEntities(int maxResults, int firstResult) {
        return findTipoDocumentoEntities(false, maxResults, firstResult);
    }

    private List<TipoDocumento> findTipoDocumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoDocumento.class));
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

    public TipoDocumento findTipoDocumento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoDocumento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoDocumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoDocumento> rt = cq.from(TipoDocumento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
