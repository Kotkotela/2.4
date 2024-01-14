package com.example.restdemo20;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    public void getAllPersonsTest() throws Exception {
        Person person1 = new Person(1, "John", "Doe", LocalDate.of(1990, 1, 1), Collections.emptyList());
        Person person2 = new Person(2, "Jane", "Smith", LocalDate.of(1995, 2, 3), Collections.emptyList());
        when(personRepository.findAll()).thenReturn(Arrays.asList(person1, person2));
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));

        verify(personRepository).findAll();
    }

    @Test
    public void getPersonByIdTest() throws Exception {
        int personId = 1;
        Person person = new Person(personId, "John", "Doe", LocalDate.of(1990, 1, 1), Collections.emptyList());
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        mockMvc.perform(get("/persons/{id}", personId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(personId))
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(personRepository).findById(personId);
    }

    @Test
    public void createPersonTest() throws Exception {
        Person person = new Person(1, "John", "Doe", LocalDate.of(1990, 1, 1), Collections.emptyList());
        when(personRepository.save(any(Person.class))).thenReturn(person);
        mockMvc.perform(post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthday\":\"1990-01-01\",\"addresses\":[]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(personRepository).save(any(Person.class));
    }

    @Test
    public void updatePersonTest() throws Exception {
        int personId = 1;
        Person person = new Person(personId, "John", "Doe", LocalDate.of(1990, 1, 1), Collections.emptyList());
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenReturn(person);
        mockMvc.perform(put("/persons/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthday\":\"1990-01-01\",\"addresses\":[]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(personId))
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(personRepository).findById(personId);
        verify(personRepository).save(any(Person.class));
    }

    @Test
    public void deletePersonTest() throws Exception {
        int personId = 1;
        Person person = new Person(personId, "John", "Doe", LocalDate.of(1990, 1, 1), Collections.emptyList());
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        mockMvc.perform(delete("/persons/{id}", personId))
                .andExpect(status().isOk());

        verify(personRepository).findById(personId);
        verify(personRepository).delete(person);
    }
}


