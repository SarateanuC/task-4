package com.example.task4;

import com.example.task4.dbo.RegistrationDbo;
import com.example.task4.dbo.StudentDbo;
import com.example.task4.dto.StudentResponseDTO;
import com.example.task4.mapper.RegistrationMapper;
import com.example.task4.mapper.StudentMapper;
import com.example.task4.service.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static java.time.LocalDate.of;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class StudentServiceImplMockTests {
    @Mock
    private StudentMapper studentMapper;
    @Mock
    private RegistrationMapper registrationMapper;
    @InjectMocks
    private StudentServiceImpl studentServiceImpl;

    @Test
    public void readyForErasmusSuccessTest() {
        StudentDbo studentDbo1 = StudentDbo.builder()
                .age(19)
                .firstname("firstname1")
                .lastname("lastname1")
                .build();
        StudentDbo studentDbo2 = StudentDbo.builder()
                .age(21)
                .firstname("firstname1")
                .lastname("lastname1")
                .build();
        List<RegistrationDbo> registrationDbos = List.of(RegistrationDbo.builder()
                        .registrationDate(of(2022, 12, 3))
                        .studentDbo(studentDbo1)
                        .build(),
                RegistrationDbo.builder()
                        .studentDbo(studentDbo2)
                        .registrationDate(of(2022, 10, 12))
                        .build());

        when(registrationMapper.findByDate(any(), any())).thenReturn(registrationDbos);
        List<StudentResponseDTO> studentResponseDTOS = studentServiceImpl.readyForErasmus(10);
        assertThat(studentResponseDTOS.size()).isEqualTo(2);
    }

    @Test
    public void readyForErasmusEmptyListTest() {
        when(registrationMapper.findByDate(any(), any())).thenReturn(emptyList());
        assertThat(studentServiceImpl.readyForErasmus(10)).isEmpty();
    }

    @Test
    public void findByUniversitySuccessTest() {
        StudentDbo studentDbo1 = StudentDbo.builder()
                .age(19)
                .firstname("firstname1")
                .lastname("lastname1")
                .build();
        StudentDbo studentDbo2 = StudentDbo.builder()
                .age(21)
                .firstname("firstname1")
                .lastname("lastname1")
                .build();

        when(studentMapper.findByUniversity(any())).thenReturn(List.of(studentDbo1, studentDbo2));
        assertThat(studentServiceImpl.findStudentsToVisitUniversity("UTM")).isNotEmpty();
        assertThat(studentServiceImpl.findStudentsToVisitUniversity("UTM").size()).isEqualTo(2);
        assertThat(studentServiceImpl.findStudentsToVisitUniversity("UTM").get(0).getFirstname()).isEqualTo(studentDbo1.getFirstname());
    }

    @Test
    public void findByUniversityEmptyListTest() {
        when(registrationMapper.findByDate(any(), any())).thenReturn(emptyList());
        assertThat(studentServiceImpl.findStudentsToVisitUniversity("UTM")).isEmpty();
    }

    @Test
    public void groupByUniversitySuccessTest() {
        StudentDbo studentDbo1 = StudentDbo.builder()
                .age(19)
                .firstname("firstname1")
                .lastname("lastname1")
                .universities(List.of("USM"))
                .build();
        StudentDbo studentDbo2 = StudentDbo.builder()
                .age(21)
                .firstname("firstname1")
                .lastname("lastname1")
                .universities(List.of("UTM"))
                .build();
        StudentDbo studentDbo3 = StudentDbo.builder()
                .age(21)
                .firstname("firstname1")
                .lastname("lastname1")
                .universities(List.of("USMF"))
                .build();

        when(studentMapper.findAll()).thenReturn(List.of(studentDbo2, studentDbo1, studentDbo3));

        assertThat(studentServiceImpl.groupByUniversity()).isNotNull();
        assertThat(studentServiceImpl.groupByUniversity().getGroupByUniversity().size()).isEqualTo(3);
        assertThat(studentServiceImpl.groupByUniversity().getGroupByUniversity()).containsOnlyKeys("USM", "UTM", "USMF");
        assertThat(studentServiceImpl.groupByUniversity().getGroupByUniversity().values()).isNotEmpty();
    }

    @Test
    public void groupByUniversityEmptyListTest() {
        when(studentMapper.findAll()).thenReturn(emptyList());
        assertThat(studentServiceImpl.groupByUniversity().getGroupByUniversity().isEmpty()).isTrue();
    }

    }

