package org.example.practice_exam2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/test")
    public String test(Model model) {
        String greeting = "Hello World";
        model.addAttribute("hello", greeting);
        return "test";
    }
}
