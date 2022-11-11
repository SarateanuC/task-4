package com.example.task4.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentsByUniversityDTO {
    private Map<String, List<StudentResponseDTO>> groupByUniversity;
}
