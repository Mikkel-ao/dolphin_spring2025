package app.daos;

import app.config.HibernateConfig;
import app.entities.Person;
import app.populators.PersonPopulator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DolphinDAOTest {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final DolphinDAO dolphinDAO = new DolphinDAO(emf);

    private Person p1;
    private Person p2;

    @BeforeEach
    void setUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Note").executeUpdate();
            em.createQuery("DELETE FROM Fee").executeUpdate();
            em.createQuery("DELETE FROM PersonDetail").executeUpdate();
            em.createQuery("DELETE FROM Person").executeUpdate();
            em.getTransaction().commit();
        }

        Person[] people = PersonPopulator.populate(dolphinDAO);
        p1 = people[0];
        p2 = people[1];
    }

    @AfterAll
    void tearDown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Test
    void testCreate() {
        Person p3 = PersonPopulator.createPerson(
                "Peter", "Strandvej 12", 3900, "Frederiksværk", 38,
                90, LocalDate.of(2023, 7, 20),
                110, LocalDate.of(2023, 6, 18),
                "Note A", "Note B", "Mette"
        );
        Person created = dolphinDAO.create(p3);
        assertNotNull(created.getId());

        List<Person> people = dolphinDAO.getAll();
        assertEquals(3, people.size());
    }

    @Test
    void testGetById() {
        Person found = dolphinDAO.getById(p1.getId());
        assertNotNull(found);
        assertEquals(p1.getName(), found.getName());
    }

    @Test
    void testGetAll() {
        List<Person> people = dolphinDAO.getAll();
        assertEquals(2, people.size());
    }

    @Test
    void testUpdate() {
        p1.setName("Hanzi Updated");
        Person updated = dolphinDAO.update(p1);
        assertEquals("Hanzi Updated", updated.getName());
    }

    @Test
    void testDelete() {
        boolean deleted = dolphinDAO.delete(p2.getId());
        assertTrue(deleted);

        Person checkDeleted = dolphinDAO.getById(p2.getId());
        assertNull(checkDeleted);
    }
}
