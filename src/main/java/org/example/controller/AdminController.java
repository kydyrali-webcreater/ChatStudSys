package org.example.controller;

import org.example.model.Subject;
import org.example.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    private SubjectRepository subjectsRepository;

    @PostMapping("/subject/create")
    public void create(@RequestBody Subject subject){
        System.out.println(subject.toString());
        subjectsRepository.save(subject);
    }


}
