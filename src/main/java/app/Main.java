package app;

import app.config.HibernateConfig;
import app.daos.DolphinDAO;
import app.entities.Person;
import app.loaders.PersonLoader;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Dolphin Demo...");

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        DolphinDAO dolphinDAO = new DolphinDAO(emf);

        // Load sample data
        PersonLoader loader = new PersonLoader(emf);
        List<Person> people = loader.loadData();
        System.out.println("Loaded People:");
        people.forEach(System.out::println);

        // GET a Person by ID
        Person firstPerson = people.get(0);
        Person fetchedPerson = dolphinDAO.getById(firstPerson.getId());
        System.out.println("\nFetched Person by ID:");
        System.out.println(fetchedPerson);

        // GET ALL Persons
        List<Person> allPeople = dolphinDAO.getAll();
        System.out.println("\nAll People in DB:");
        allPeople.forEach(System.out::println);

        // UPDATE a Person // TODO: Consider refactoring.
        fetchedPerson.setName(fetchedPerson.getName() + " Updated");
        fetchedPerson.setFees(fetchedPerson.getFees());
        Person updatedPerson = dolphinDAO.update(fetchedPerson);
        System.out.println("\nUpdated Person:");
        System.out.println(updatedPerson);

        // US-2 Get total BEFORE deletion
        double totalPaid = dolphinDAO.getAmountPaid(fetchedPerson.getId());
        System.out.println("\nTotal amount paid by " + fetchedPerson.getName() + ": " + totalPaid);

        // DELETE a Person
        boolean deleted = dolphinDAO.delete(updatedPerson.getId());
        System.out.println("\nDeleted Person? " + deleted);

        // VERIFY deletion
        Person checkDeleted = dolphinDAO.getById(updatedPerson.getId());
        System.out.println("\nCheck Deleted Person (should be null): " + checkDeleted);

        System.out.println("\nAll Notes and Person Info with dto");
        dolphinDAO.getNotesAndPersonInfo().forEach(System.out::println);

        System.out.println("\nAll Notes and Person Info with record");
        dolphinDAO.getAllNotesAndPersonInfo().forEach(System.out::println);

        emf.close();
    }
}
