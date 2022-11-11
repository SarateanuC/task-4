package com.example.task4.dto;

import lombok.*;

import java.util.List;
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDTO {
    private Integer id;
    private String firstname;
    private String lastname;
    private Integer age;
    private boolean eligibleForErasmusScholarship;
    private List<String> universities;
}
