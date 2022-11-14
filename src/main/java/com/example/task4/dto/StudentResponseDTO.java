package com.example.task4.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDTO {
    private UUID student_id;
    private String firstname;
    private String lastname;
    private Integer age;
    private boolean eligibleForErasmusScholarship;
}
