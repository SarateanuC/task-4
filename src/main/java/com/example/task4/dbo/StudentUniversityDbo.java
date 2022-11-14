package com.example.task4.dbo;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentUniversityDbo {
    private UUID student_id;
    private String firstname;
    private String lastname;
    private String gender;
    private Integer age;
    private String university;
}
