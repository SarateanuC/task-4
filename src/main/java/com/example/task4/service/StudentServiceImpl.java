package com.example.task4.service;

import com.example.task4.dbo.StudentDbo;
import com.example.task4.dbo.StudentUniversityDbo;
import com.example.task4.dto.StudentResponseDTO;
import com.example.task4.dto.StudentsByUniversityDTO;
import com.example.task4.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.example.task4.dto.StudentResponseDTO.builder;
import static java.time.LocalDate.now;
import static java.util.Map.Entry;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentMapper studentMapper;

    @Override
    public List<StudentResponseDTO> readyForErasmus(Integer rangeOfTime) {
        return studentMapper.findByDate(now(), now().minusDays(rangeOfTime))
                .stream()
                .map(this::getStudentForErasmus)
                .collect(toList());

    }

    @Override
    public List<StudentResponseDTO> findStudentsToVisitUniversity(String university) {
        return studentMapper.findByUniversity(university)
                .stream()
                .map(this::convertToDTO)
                .collect(toList());
    }

    @Override
    public StudentsByUniversityDTO groupByUniversity() {
        Map<String, List<StudentResponseDTO>> studentsByUniversity = studentMapper.groupByUniversity()
                .stream()
                .collect(groupingBy(StudentUniversityDbo::getUniversity))
                .entrySet().stream().collect(toMap(Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(e -> builder()
                                        .firstname(e.getFirstname())
                                        .student_id(e.getStudent_id())
                                        .lastname(e.getLastname())
                                        .build())
                                .collect(toList())));
        return StudentsByUniversityDTO.builder()
                .groupByUniversity(studentsByUniversity)
                .build();
    }

    private StudentResponseDTO convertToDTO(StudentDbo studentDbos) {
        return builder()
                .age(studentDbos.getAge())
                .student_id(studentDbos.getStudent_id())
                .lastname(studentDbos.getLastname())
                .firstname(studentDbos.getFirstname())
                .eligibleForErasmusScholarship(studentDbos.isEligibleForErasmusScholarship())
                .build();
    }

    private StudentResponseDTO getStudentForErasmus(StudentDbo studentDbo) {
        studentDbo.setEligibleForErasmusScholarship(true);
        return convertToDTO(studentDbo);
    }
}

