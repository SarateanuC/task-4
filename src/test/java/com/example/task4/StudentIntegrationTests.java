package com.example.task4;

import com.example.task4.dto.StudentResponseDTO;
import com.example.task4.dto.StudentsByUniversityDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@SqlGroup(
        @Sql(scripts = {"/sql/20221110_create-student.sql",
                "/sql/20221110_create-registration.sql",
                "/sql/20221110_insert-student.sql",
                "/sql/20221110_insert-registration.sql",
                "/sql/20221114_create-student-university.sql",
                "/sql/20221114_insert-student-university.sql"}))
class StudentIntegrationTests {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void readyForErasmusTest() {
        MvcResult mvcResult = mockMvc.perform(get("/api/student/by-date")
                        .contentType("application/json")
                        .param("time", "100"))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        StudentResponseDTO[] studentResponseDTOS = objectMapper.readValue(contentAsString, StudentResponseDTO[].class);
        assertThat(studentResponseDTOS.length).isEqualTo(6);
    }

    @Test
    @SneakyThrows
    void findByUniversitySuccessTest() {
        MvcResult mvcResult = mockMvc.perform(get("/api/student/by-university")
                        .contentType("application/json")
                        .param("university", "UTM"))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        StudentResponseDTO[] studentResponseDTOS = objectMapper.readValue(contentAsString, StudentResponseDTO[].class);
        assertThat(studentResponseDTOS.length).isEqualTo(10);
    }

    @Test
    @SneakyThrows
    void findByUniversityEmptyListTest() {
        MvcResult mvcResult = mockMvc.perform(get("/api/student/by-university")
                        .contentType("application/json")
                        .param("university", "ULIM"))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        StudentResponseDTO[] studentResponseDTOS = objectMapper.readValue(contentAsString, StudentResponseDTO[].class);
        assertThat(studentResponseDTOS).isEmpty();
    }

    @Test
    @SneakyThrows
    void groupByUniversityTest() {
        MvcResult mvcResult = mockMvc.perform(get("/api/student/group-by-university")
                        .contentType("application/json"))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        StudentsByUniversityDTO studentsByUniversityDTO = objectMapper.readValue(contentAsString, StudentsByUniversityDTO.class);
        assertThat(studentsByUniversityDTO.getGroupByUniversity().size()).isEqualTo(3);
        assertThat(studentsByUniversityDTO.getGroupByUniversity().keySet()).contains("USM", "USMF", "UTM");
    }
}
