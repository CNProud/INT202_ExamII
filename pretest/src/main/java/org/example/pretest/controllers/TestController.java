package org.example.pretest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    public String test(Model model) {
        String welcome = "Welcome to web app";
        model.addAttribute("welcomeBox",welcome);
        return "test";
    }
}
