package uk.co.huntersix.spring.rest.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonDataService personDataService;

    @Test
    public void shouldReturnPersonFromService() throws Exception {
        when(personDataService.findPerson(any(), any())).thenReturn(new Person("Mary", "Smith"));
        this.mockMvc.perform(get("/person/smith/mary"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("firstName").value("Mary"))
            .andExpect(jsonPath("lastName").value("Smith"));

    }

    @Test
    public void requestedPersonDoesNotExist() throws  Exception{
        when(personDataService.findPerson(any(), any())).thenReturn(new Person("NO_DATA_FOUND", "NO_DATA_FOUND"));
        this.mockMvc.perform(get("/person/smith/mar"))
                .andDo(print())
                .andExpect(jsonPath("firstName").value("NO_DATA_FOUND"))
                .andExpect(jsonPath("lastName").value("NO_DATA_FOUND"));

    }

    @Test
    public void  getListOfAllPeopleWithSurname() throws Exception {
        when(personDataService.findPersons(any())).thenReturn(Arrays.asList(
                new Person("Mary", "Smith"),
                 new Person("Gold", "Smith")));
        this.mockMvc.perform(get("/person/smith"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json("[{}, {}]"));
    }

    @Test
    public void  addPerson() throws Exception {
      when(personDataService.addPerson(any(), any())).thenAnswer(x -> ResponseEntity.ok("Person Added Successfully"));
      this.mockMvc.perform(post("/person/smith/mary"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Person Added Successfully"));
    }
}