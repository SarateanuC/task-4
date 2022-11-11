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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@SqlGroup(
        @Sql(scripts = {"/sql/20221110_create-student.sql",
                "/sql/20221110_create-registration.sql",
                "/sql/20221110_insert-student.sql",
                "/sql/20221110_insert-registration.sql"}))
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
                        .content(objectMapper.writeValueAsString(10)))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<StudentResponseDTO> studentResponseDTOS = List.of(objectMapper.readValue(contentAsString, StudentResponseDTO.class));
        assertThat(studentResponseDTOS.size()).isEqualTo(7);
    }

    @Test
    @SneakyThrows
    void findByUniversitySuccessTest() {
        MvcResult mvcResult = mockMvc.perform(get("/api/student/by-univeristy")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString("USFM")))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<StudentResponseDTO> studentResponseDTOS = List.of(objectMapper.readValue(contentAsString, StudentResponseDTO.class));
        assertThat(studentResponseDTOS.size()).isEqualTo(10);
    }

    @Test
    @SneakyThrows
    void findByUniversityEmptyListTest() {
        MvcResult mvcResult = mockMvc.perform(get("/api/student/by-univeristy")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString("ULIM")))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<StudentResponseDTO> studentResponseDTOS = List.of(objectMapper.readValue(contentAsString, StudentResponseDTO.class));
        assertThat(studentResponseDTOS.size()).isEqualTo(0);
    }

    @Test
    @SneakyThrows
    void groupByUniversityTest() {
        MvcResult mvcResult = mockMvc.perform(get("/api/student/group-by-university")
                        .contentType("application/json"))
                         .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        StudentsByUniversityDTO studentsByUniversityDTO = objectMapper.readValue(contentAsString, StudentsByUniversityDTO.class);
        assertThat(studentsByUniversityDTO.getGroupByUniversity().size()).isEqualTo(4);
    }
}
