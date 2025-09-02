package app.daos;

import app.entities.Person;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

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
        return null;
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
        return false;
    }
}
