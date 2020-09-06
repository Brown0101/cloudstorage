package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CredentialsController {

    private CredentialService credentialService;

    public CredentialsController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }


    @GetMapping("/credential")
    public String addCredentials(Authentication authentication, CredentialForm credentialForm, Model model) {
        model.addAttribute("credentials", this.credentialService.getCredentails());
        return "home";
    }

    @PostMapping("/credential")
    public String editCredential(Authentication authentication, CredentialForm credentialForm, Model model) {
        // Check if data exists in database already
        // We use this to update our data by searching
        // for our unique id.
        if(this.credentialService.doesCredentialExist(credentialForm)) {
            this.credentialService.updateCredential(credentialForm);
        } else {
            this.credentialService.trackLoggedInUserId(authentication.getName());
            this.credentialService.createCredential(credentialForm, authentication.getName());
        }

        model.addAttribute("credentials", this.credentialService.getCredentails());
        return "home";
    }

    @GetMapping("/notes/delete/{credentialid}")
    public String deleteCredential(@PathVariable("credentialid") Integer noteId, CredentialForm credentialForm, Model model) {
        this.credentialService.deleteCredential(noteId);
        model.addAttribute("credentials", this.credentialService.getCredentails());
        return "home";
    }
}