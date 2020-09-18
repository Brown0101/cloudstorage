package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping()
    public String loginView() {
        return "login";
    }
    @RequestMapping()
    public String loginView(@ModelAttribute("signupSuccess") Boolean signupSuccess) {
        return "login";
    }

}
