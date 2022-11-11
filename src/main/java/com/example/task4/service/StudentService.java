package com.example.task4.service;

import com.example.task4.dto.StudentResponseDTO;
import com.example.task4.dto.StudentsByUniversityDTO;

import java.util.List;

public interface StudentService {
    List<StudentResponseDTO> readyForErasmus(Integer rangeOfTime);
    List<StudentResponseDTO> findStudentsToVisitUniversity(String university);
    StudentsByUniversityDTO groupByUniversity();
}
