package com.example.task4.controller;

import com.example.task4.dto.StudentResponseDTO;
import com.example.task4.dto.StudentsByUniversityDTO;
import com.example.task4.service.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentServiceImpl studentServiceImpl;

    @GetMapping("/by-date")
    @ResponseStatus(OK)
    public List<StudentResponseDTO> readyForErasmus(@RequestParam("time") Integer timeRange) {
        return studentServiceImpl.readyForErasmus(timeRange);
    }

    @GetMapping("/by-university")
    @ResponseStatus(OK)
    public List<StudentResponseDTO> findAllStudentToVisitUniversity(@RequestParam ("university") String university) {
        return studentServiceImpl.findStudentsToVisitUniversity(university);
    }

    @GetMapping("/group-by-university")
    @ResponseStatus(OK)
    public StudentsByUniversityDTO mapAllStudentsByUniversity() {
        return studentServiceImpl.groupByUniversity();
    }
}
