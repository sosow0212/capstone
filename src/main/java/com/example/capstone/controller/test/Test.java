package com.example.capstone.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/java")
public class Test {
    @GetMapping("/spring")
    public String test() {
        return "hello";
    }
}
