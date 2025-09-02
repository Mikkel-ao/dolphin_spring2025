package app.populators;

import app.daos.DolphinDAO;
import app.entities.Fee;
import app.entities.Note;
import app.entities.Person;
import app.entities.PersonDetail;

import java.time.LocalDate;

public class PersonPopulator {

    public static Person[] populate(DolphinDAO dolphinDAO) {
        Person p1 = createPerson("Hanzi", "Algade 3", 4300, "Holbæk", 45,
                125, LocalDate.of(2023, 8, 25),
                150, LocalDate.of(2023, 7, 19),
                "Første note", "Anden note", "Mikkel");
        p1 = dolphinDAO.create(p1);

        Person p2 = createPerson("Anna", "Bredgade 5", 4000, "Roskilde", 30,
                200, LocalDate.of(2023, 9, 1),
                180, LocalDate.of(2023, 8, 15),
                "Note 1", "Note 2", "Lars");
        p2 = dolphinDAO.create(p2);

        return new Person[]{p1, p2};
    }

    public static Person createPerson(String name, String address, int zip, String city, int age,
                                      int fee1Amount, LocalDate fee1Date,
                                      int fee2Amount, LocalDate fee2Date,
                                      String note1, String note2, String createdBy) {
        Person person = Person.builder().name(name).build();

        PersonDetail detail = new PersonDetail(address, zip, city, age);
        person.addPersonDetail(detail);

        Fee f1 = Fee.builder().amount(fee1Amount).payDate(fee1Date).build();
        Fee f2 = Fee.builder().amount(fee2Amount).payDate(fee2Date).build();
        person.addFee(f1);
        person.addFee(f2);

        Note n1 = Note.builder().note(note1).createdBy(createdBy).build();
        Note n2 = Note.builder().note(note2).createdBy(createdBy).build();
        person.addNote(n1);
        person.addNote(n2);

        return person;
    }
}
