package uk.co.huntersix.spring.rest.referencedata;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.co.huntersix.spring.rest.model.Person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonDataService {
    public static final List<Person> PERSON_DATA = Arrays.asList(
            new Person("Mary", "Smith"),
            new Person("Brian", "Archer"),
            new Person("Collin", "Brown"),
            new Person("Gold", "Smith")
    );

    public Person findPerson(String lastName, String firstName) {

        List<Person> personList = PERSON_DATA.stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                        && p.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());

        if(!personList.isEmpty())
               return personList.get(0);

        else
                  return new Person("NO_DATA_FOUND", "NO_DATA_FOUND");


    }

    public List<Person> findPersons(String lastName) {
        List<Person> personList = PERSON_DATA.stream()
                .filter(p ->  p.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
        return personList;
    }

    public ResponseEntity<String> addPerson(String lastName, String firstName) {
        List<Person> personList = PERSON_DATA.stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                        && p.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
        if(personList.isEmpty()){
            personList.add(new Person(firstName, lastName));
            return new ResponseEntity<>("Person Added Successfully", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Person already exists", HttpStatus.FORBIDDEN);
        }

    }
}