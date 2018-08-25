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
import java.util.ArrayList;
import java.util.List;
import bingobi.persistence.entities.Cartones;
import bingobi.persistence.entities.Tablas;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kbra
 */
public class TablasJpaController implements Serializable {

    public TablasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tablas tablas) {
        if (tablas.getBingosList() == null) {
            tablas.setBingosList(new ArrayList<Bingos>());
        }
        if (tablas.getCartonesList() == null) {
            tablas.setCartonesList(new ArrayList<Cartones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Bingos> attachedBingosList = new ArrayList<Bingos>();
            for (Bingos bingosListBingosToAttach : tablas.getBingosList()) {
                bingosListBingosToAttach = em.getReference(bingosListBingosToAttach.getClass(), bingosListBingosToAttach.getId());
                attachedBingosList.add(bingosListBingosToAttach);
            }
            tablas.setBingosList(attachedBingosList);
            List<Cartones> attachedCartonesList = new ArrayList<Cartones>();
            for (Cartones cartonesListCartonesToAttach : tablas.getCartonesList()) {
                cartonesListCartonesToAttach = em.getReference(cartonesListCartonesToAttach.getClass(), cartonesListCartonesToAttach.getId());
                attachedCartonesList.add(cartonesListCartonesToAttach);
            }
            tablas.setCartonesList(attachedCartonesList);
            em.persist(tablas);
            for (Bingos bingosListBingos : tablas.getBingosList()) {
                Tablas oldIdTablaOfBingosListBingos = bingosListBingos.getIdTabla();
                bingosListBingos.setIdTabla(tablas);
                bingosListBingos = em.merge(bingosListBingos);
                if (oldIdTablaOfBingosListBingos != null) {
                    oldIdTablaOfBingosListBingos.getBingosList().remove(bingosListBingos);
                    oldIdTablaOfBingosListBingos = em.merge(oldIdTablaOfBingosListBingos);
                }
            }
            for (Cartones cartonesListCartones : tablas.getCartonesList()) {
                Tablas oldIdTablaOfCartonesListCartones = cartonesListCartones.getIdTabla();
                cartonesListCartones.setIdTabla(tablas);
                cartonesListCartones = em.merge(cartonesListCartones);
                if (oldIdTablaOfCartonesListCartones != null) {
                    oldIdTablaOfCartonesListCartones.getCartonesList().remove(cartonesListCartones);
                    oldIdTablaOfCartonesListCartones = em.merge(oldIdTablaOfCartonesListCartones);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tablas tablas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tablas persistentTablas = em.find(Tablas.class, tablas.getId());
            List<Bingos> bingosListOld = persistentTablas.getBingosList();
            List<Bingos> bingosListNew = tablas.getBingosList();
            List<Cartones> cartonesListOld = persistentTablas.getCartonesList();
            List<Cartones> cartonesListNew = tablas.getCartonesList();
            List<Bingos> attachedBingosListNew = new ArrayList<Bingos>();
            for (Bingos bingosListNewBingosToAttach : bingosListNew) {
                bingosListNewBingosToAttach = em.getReference(bingosListNewBingosToAttach.getClass(), bingosListNewBingosToAttach.getId());
                attachedBingosListNew.add(bingosListNewBingosToAttach);
            }
            bingosListNew = attachedBingosListNew;
            tablas.setBingosList(bingosListNew);
            List<Cartones> attachedCartonesListNew = new ArrayList<Cartones>();
            for (Cartones cartonesListNewCartonesToAttach : cartonesListNew) {
                cartonesListNewCartonesToAttach = em.getReference(cartonesListNewCartonesToAttach.getClass(), cartonesListNewCartonesToAttach.getId());
                attachedCartonesListNew.add(cartonesListNewCartonesToAttach);
            }
            cartonesListNew = attachedCartonesListNew;
            tablas.setCartonesList(cartonesListNew);
            tablas = em.merge(tablas);
            for (Bingos bingosListOldBingos : bingosListOld) {
                if (!bingosListNew.contains(bingosListOldBingos)) {
                    bingosListOldBingos.setIdTabla(null);
                    bingosListOldBingos = em.merge(bingosListOldBingos);
                }
            }
            for (Bingos bingosListNewBingos : bingosListNew) {
                if (!bingosListOld.contains(bingosListNewBingos)) {
                    Tablas oldIdTablaOfBingosListNewBingos = bingosListNewBingos.getIdTabla();
                    bingosListNewBingos.setIdTabla(tablas);
                    bingosListNewBingos = em.merge(bingosListNewBingos);
                    if (oldIdTablaOfBingosListNewBingos != null && !oldIdTablaOfBingosListNewBingos.equals(tablas)) {
                        oldIdTablaOfBingosListNewBingos.getBingosList().remove(bingosListNewBingos);
                        oldIdTablaOfBingosListNewBingos = em.merge(oldIdTablaOfBingosListNewBingos);
                    }
                }
            }
            for (Cartones cartonesListOldCartones : cartonesListOld) {
                if (!cartonesListNew.contains(cartonesListOldCartones)) {
                    cartonesListOldCartones.setIdTabla(null);
                    cartonesListOldCartones = em.merge(cartonesListOldCartones);
                }
            }
            for (Cartones cartonesListNewCartones : cartonesListNew) {
                if (!cartonesListOld.contains(cartonesListNewCartones)) {
                    Tablas oldIdTablaOfCartonesListNewCartones = cartonesListNewCartones.getIdTabla();
                    cartonesListNewCartones.setIdTabla(tablas);
                    cartonesListNewCartones = em.merge(cartonesListNewCartones);
                    if (oldIdTablaOfCartonesListNewCartones != null && !oldIdTablaOfCartonesListNewCartones.equals(tablas)) {
                        oldIdTablaOfCartonesListNewCartones.getCartonesList().remove(cartonesListNewCartones);
                        oldIdTablaOfCartonesListNewCartones = em.merge(oldIdTablaOfCartonesListNewCartones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tablas.getId();
                if (findTablas(id) == null) {
                    throw new NonexistentEntityException("The tablas with id " + id + " no longer exists.");
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
            Tablas tablas;
            try {
                tablas = em.getReference(Tablas.class, id);
                tablas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tablas with id " + id + " no longer exists.", enfe);
            }
            List<Bingos> bingosList = tablas.getBingosList();
            for (Bingos bingosListBingos : bingosList) {
                bingosListBingos.setIdTabla(null);
                bingosListBingos = em.merge(bingosListBingos);
            }
            List<Cartones> cartonesList = tablas.getCartonesList();
            for (Cartones cartonesListCartones : cartonesList) {
                cartonesListCartones.setIdTabla(null);
                cartonesListCartones = em.merge(cartonesListCartones);
            }
            em.remove(tablas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tablas> findTablasEntities() {
        return findTablasEntities(true, -1, -1);
    }

    public List<Tablas> findTablasEntities(int maxResults, int firstResult) {
        return findTablasEntities(false, maxResults, firstResult);
    }

    private List<Tablas> findTablasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tablas.class));
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

    public Tablas findTablas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tablas.class, id);
        } finally {
            em.close();
        }
    }

    public int getTablasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tablas> rt = cq.from(Tablas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
