package com.example.task4.dbo;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDbo {
    private Integer id;
    private LocalDate registrationDate;
    private StudentDbo studentDbo;
}
