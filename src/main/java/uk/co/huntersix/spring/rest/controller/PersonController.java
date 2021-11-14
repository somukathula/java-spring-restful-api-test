package uk.co.huntersix.spring.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

import java.util.List;

@RestController
public class PersonController {
    private PersonDataService personDataService;

    public PersonController(@Autowired PersonDataService personDataService) {
        this.personDataService = personDataService;
    }

    @GetMapping(value = "/person/{lastName}/{firstName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findPerson(@PathVariable(value = "lastName") String lastName,
                             @PathVariable(value = "firstName") String firstName) {

        return personDataService.findPerson(lastName, firstName);
    }

    @GetMapping(value = "/person/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findPersons(@PathVariable(value = "lastName") String lastName) {

        return personDataService.findPersons(lastName);

    }

    @PostMapping(value = "/person/{lastName}/{firstName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addPerson(@PathVariable(value = "lastName") String lastName,
                                            @PathVariable(value = "firstName") String firstName) {

        return personDataService.addPerson(lastName, firstName);
    }

}