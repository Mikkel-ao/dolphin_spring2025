package app.daos;

import app.entities.Person;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DolphinDAO implements IDAO<Person, Integer> {

    private final EntityManagerFactory emf;

    public DolphinDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Person create(Person person) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            return person;
        } catch (Exception e) {
            throw new ApiException(500, "Error creating person " + e.getMessage());
        }
    }

    @Override
    public Person getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Person.class, id);
        } catch (Exception e) {
            throw new ApiException(500, "Error getting person " + e.getMessage());
        }
    }

    @Override
    public List<Person> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT p FROM Person p", Person.class)
                    .getResultList();
        } catch (Exception e) {
            throw new ApiException(500, "Error finding people: " + e.getMessage());
        }
    }

    @Override
    public Person update(Person person) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
            return person;
        } catch (Exception e) {
            throw new ApiException(500, "Error updating person: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(em.find(Person.class, id));
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            throw new ApiException(500, "Error deleting person: " + e.getMessage());
        }
    }

    @Override
    public int getAmountPaid(Integer personId) {
        EntityManager em = emf.createEntityManager();
        try {
            Long total = em.createQuery(
            "SELECT COALESCE(SUM(f.amount), 0) FROM Fee f WHERE f.person.id = :personId", Long.class)
                    .setParameter("personId", personId)
                    .getSingleResult();
            return total.intValue();
        } catch (Exception e) {
        throw new ApiException(500, "Error getting amount paid for person " + e.getMessage());}
    }
}
