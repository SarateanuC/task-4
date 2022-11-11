package com.example.task4.service;

import com.example.task4.dbo.RegistrationDbo;
import com.example.task4.dbo.StudentDbo;
import com.example.task4.dto.StudentResponseDTO;
import com.example.task4.dto.StudentsByUniversityDTO;
import com.example.task4.mapper.RegistrationMapper;
import com.example.task4.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.LocalDate.now;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final RegistrationMapper registrationMapper;
    private final StudentMapper studentMapper;

    @Override
    public List<StudentResponseDTO> readyForErasmus(Integer rangeOfTime) {
        return registrationMapper.findByDate(now(), now().minusDays(rangeOfTime)).stream()
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
        List<String> universities = studentMapper.findAll()
                .stream()
                .map(StudentDbo::getUniversities)
                .flatMap(List::stream)
                .distinct()
                .collect(toList());

        Map<String, List<StudentResponseDTO>> mappedStudents = new HashMap<>();
        universities.forEach(u -> {
            List<StudentResponseDTO> studentResponseDTOS = studentMapper.findByUniversity(u).stream().map(this::convertToDTO).collect(toList());
            mappedStudents.put(u, studentResponseDTOS);
        });
        return StudentsByUniversityDTO.builder()
                .groupByUniversity(mappedStudents)
                .build();
    }

    private StudentResponseDTO convertToDTO(StudentDbo studentDbos) {
        return StudentResponseDTO.builder()
                .age(studentDbos.getAge())
                .id(studentDbos.getId())
                .lastname(studentDbos.getLastname())
                .firstname(studentDbos.getFirstname())
                .eligibleForErasmusScholarship(studentDbos.isEligibleForErasmusScholarship())
                .build();
    }

    private StudentResponseDTO getStudentForErasmus(RegistrationDbo registrationDbo) {
        StudentDbo studentDbo = registrationDbo.getStudentDbo();
        studentDbo.setEligibleForErasmusScholarship(true);
        return convertToDTO(studentDbo);
    }
}

