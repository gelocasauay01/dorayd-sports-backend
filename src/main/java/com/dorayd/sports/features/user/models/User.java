package com.dorayd.sports.features.user.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;

    @Override
    public String toString() {
        return String.format("""
            user:
                id: %d
                firstName: %s
                middleName: %s
                lastName: %s
                birthDate: %s
                gender: %s
        """, id, firstName, middleName, lastName, birthDate, gender);
    }
}
