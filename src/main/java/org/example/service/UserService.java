package org.example.service;

import org.example.model.Dto.RegistrationRequest;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(RegistrationRequest registrationRequest) {
        User newUser = new User(registrationRequest);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

}
