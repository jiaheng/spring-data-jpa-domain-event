package com.example.demo.controller;

import com.example.demo.entity.MyClass;
import com.example.demo.service.MyClassService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class TestController {

    private final MyClassService myClassService;

    public TestController(MyClassService myClassService) {
        this.myClassService = myClassService;
    }

    @PostMapping("/my-classes/created/update")
    public void triggerUpdate() {
        myClassService.updateAllCreated();
    }

    @PostMapping("/my-classes")
    public void addMyClass() {
        myClassService.createRandomMyClass();
    }

    @GetMapping("/my-classes")
    public List<MyClass> getAll() {
        return myClassService.getAll();
    }
}
