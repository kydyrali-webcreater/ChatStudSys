package org.example.model.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String userId;
    private String password;
}
