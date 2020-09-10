package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialsController {

    private CredentialService credentialService;
    private NoteService noteService;
    private EncryptionService encryptionService;

    public CredentialsController(CredentialService credentialService, NoteService noteService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/credentials")
    public String getCredentials(CredentialForm credentialForm, NoteForm noteForm, Model model) {
        model.addAttribute("credentials", this.credentialService.getAllCredentials());
        model.addAttribute("notes", this.noteService.getNotes());
        model.addAttribute("encryption", this.encryptionService);
        return "home";
    }

    @PostMapping("/credentials")
    public String addUpdateCredential(Authentication authentication, CredentialForm credentialForm, NoteForm noteForm,  Model model) {
        // Check if data exists in database already
        // We use this to update our data by searching
        // for our unique id.

        if(this.credentialService.doesCredentialExist(credentialForm)) {
            this.credentialService.updateCredential(credentialForm);
        } else {
            this.credentialService.trackLoggedInUserId(authentication.getName());
            this.credentialService.createCredential(credentialForm);
        }

        model.addAttribute("credentials", this.credentialService.getAllCredentials());
        model.addAttribute("notes", this.noteService.getNotes());
        model.addAttribute("encryption", this.encryptionService);

        return "home";
    }

    @GetMapping("/credentials/delete/{credentialid}")
    public String deleteCredential(@PathVariable("credentialid") Integer credentialId, CredentialForm credentialForm, NoteForm noteForm,  Model model) {
        this.credentialService.deleteCredential(credentialId);
        model.addAttribute("credentials", this.credentialService.getAllCredentials());
        model.addAttribute("notes", this.noteService.getNotes());
        model.addAttribute("encryption", this.encryptionService);
        return "home";
    }
}
