package com.example.task4.dbo;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDbo {
    private UUID id;
    private LocalDate registrationDate;
    private StudentDbo studentDbo;
}
