package org.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Id
    private String id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String password;


    public User(RegistrationRequest registrationRequest) {
        this.id = registrationRequest.getUserId();
        this.firstname = registrationRequest.getFirstName();
        this.lastname = registrationRequest.getLastName();
        this.password = registrationRequest.getPassword();
        this.userRole = UserRole.valueOf(registrationRequest.getRole().toUpperCase());
    }

    public User() {
    }

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public enum UserRole{
        STUDENT,
        ADMIN,
        TEACHER;
    }
}


