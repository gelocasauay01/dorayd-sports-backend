package com.dorayd.sports.features.user.models;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @EqualsAndHashCode.Exclude
    private long id;

    @NotNull(message = "First name must not be null")
    @Size(min = 3, max = 100, message = "First name must have 3-100 characters")
    private String firstName;

    @Size(min = 3, max = 100, message = "Middle name must have 3-100 characters")
    private String middleName;

    @NotNull
    @Size(min = 3, max = 100, message = "Last name must have 3-100 characters")
    private String lastName;

    @NotNull(message = "Birth date must not be null")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotNull(message = "Gender must not be null")
    private Gender gender;
}
