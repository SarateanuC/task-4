package com.example.task4;

import com.example.task4.dbo.StudentDbo;
import com.example.task4.dbo.StudentUniversityDbo;
import com.example.task4.dto.StudentResponseDTO;
import com.example.task4.mapper.StudentMapper;
import com.example.task4.service.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class StudentServiceImplMockTests {
    @Mock
    private StudentMapper studentMapper;
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

        when(studentMapper.findByDate(any(), any())).thenReturn(List.of(studentDbo1, studentDbo2));
        List<StudentResponseDTO> studentResponseDTOS = studentServiceImpl.readyForErasmus(10);
        assertThat(studentResponseDTOS.size()).isEqualTo(2);
    }

    @Test
    public void readyForErasmusEmptyListTest() {
        when(studentMapper.findByDate(any(), any())).thenReturn(emptyList());
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
        when(studentMapper.findByDate(any(), any())).thenReturn(emptyList());
        assertThat(studentServiceImpl.findStudentsToVisitUniversity("UTM")).isEmpty();
    }

    @Test
    public void groupByUniversitySuccessTest() {
        StudentUniversityDbo studentDbo1UTM = StudentUniversityDbo.builder()
                .age(19)
                .firstname("firstname1")
                .lastname("lastname1")
                .university("UTM")
                .build();
        StudentUniversityDbo studentDbo1USM = StudentUniversityDbo.builder()
                .age(19)
                .firstname("firstname1")
                .lastname("lastname1")
                .university("USM")
                .build();
        StudentUniversityDbo studentDbo2 = StudentUniversityDbo.builder()
                .age(21)
                .firstname("firstname2")
                .lastname("lastname2")
                .university("USMF")
                .build();
        StudentUniversityDbo studentDbo3 = StudentUniversityDbo.builder()
                .age(19)
                .firstname("firstname3")
                .lastname("lastname3")
                .university("UTM")
                .build();

        when(studentMapper.groupByUniversity()).thenReturn(List.of(studentDbo2, studentDbo1USM,studentDbo1UTM, studentDbo3));

        assertThat(studentServiceImpl.groupByUniversity()).isNotNull();
        assertThat(studentServiceImpl.groupByUniversity().getGroupByUniversity().size()).isEqualTo(3);
        assertThat(studentServiceImpl.groupByUniversity().getGroupByUniversity()).containsOnlyKeys("USM", "UTM", "USMF");
        assertThat(studentServiceImpl.groupByUniversity().getGroupByUniversity().values()).isNotEmpty();
    }

    @Test
    public void groupByUniversityEmptyListTest() {
        when(studentMapper.groupByUniversity()).thenReturn(emptyList());
        assertThat(studentServiceImpl.groupByUniversity().getGroupByUniversity().isEmpty()).isTrue();
    }

}

