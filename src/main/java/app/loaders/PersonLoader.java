package app.loaders;

import app.daos.DolphinDAO;
import app.entities.Fee;
import app.entities.Note;
import app.entities.Person;
import app.entities.PersonDetail;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonLoader {

    private final DolphinDAO dolphinDAO;

    public PersonLoader(EntityManagerFactory emf) {
        this.dolphinDAO = new DolphinDAO(emf);
    }

    // Helper method reduce repetition when creating person
    private Person newPerson(String name, String address, int zipCode, String city, int age,
                             int feeAmount1, LocalDate feeDate1,
                             int feeAmount2, LocalDate feeDate2,
                             String note1, String note2, String createdBy) {

        Person person = Person.builder().name(name).build();

        PersonDetail detail = new PersonDetail(address, zipCode, city, age);
        person.addPersonDetail(detail);

        Fee fee1 = Fee.builder().amount(feeAmount1).payDate(feeDate1).build();
        Fee fee2 = Fee.builder().amount(feeAmount2).payDate(feeDate2).build();
        person.addFee(fee1);
        person.addFee(fee2);

        Note n1 = Note.builder().note(note1).createdBy(createdBy).build();
        Note n2 = Note.builder().note(note2).createdBy(createdBy).build();
        person.addNote(n1);
        person.addNote(n2);

        return person;
    }


    public List<Person> loadData() {
        List<Person> people = new ArrayList<>();

        people.add(dolphinDAO.create(newPerson(
                "Hanzi", "Algade 3", 4300, "Holbæk", 45,
                125, LocalDate.of(2023, 8, 25),
                150, LocalDate.of(2023, 7, 19),
                "Første note", "Anden note", "Mikkel"
        )));

        people.add(dolphinDAO.create(newPerson(
                "Anna", "Bredgade 5", 4000, "Roskilde", 30,
                200, LocalDate.of(2023, 9, 1),
                180, LocalDate.of(2023, 8, 15),
                "Note 1", "Note 2", "Lars"
        )));

        people.add(dolphinDAO.create(newPerson(
                "Peter", "Strandvej 12", 3900, "Frederiksværk", 38,
                90, LocalDate.of(2023, 7, 20),
                110, LocalDate.of(2023, 6, 18),
                "Note A", "Note B", "Mette"
        )));

        people.add(dolphinDAO.create(newPerson(
                "Sofie", "Torvegade 8", 5000, "Odense", 28,
                150, LocalDate.of(2023, 8, 30),
                160, LocalDate.of(2023, 7, 22),
                "First note", "Second note", "Jens"
        )));

        people.add(dolphinDAO.create(newPerson(
                "Lars", "Hovedgade 1", 2100, "København", 50,
                300, LocalDate.of(2023, 9, 2),
                320, LocalDate.of(2023, 8, 10),
                "Initial note", "Follow-up note", "Karin"
        )));

        return people;
    }
}
