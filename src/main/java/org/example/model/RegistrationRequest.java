package org.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegistrationRequest {
    private String userId;

    private String firstName;

    private String lastName;

    private String role;
    private String password;
}