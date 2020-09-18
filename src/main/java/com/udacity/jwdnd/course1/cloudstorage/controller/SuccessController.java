package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SuccessController {

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String successView(@ModelAttribute("signupSuccess") Boolean signupSuccess, Model model) {
        if(signupSuccess == null) {
            signupSuccess = false;
        }
        System.out.println("Login controller called!!!");
        System.out.println("Data we got is: " + signupSuccess);
        return "login";
    }
}
