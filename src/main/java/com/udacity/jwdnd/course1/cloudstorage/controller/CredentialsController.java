package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
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
    private FileService fileService;
    private UserService userService;

    public CredentialsController(CredentialService credentialService, NoteService noteService, EncryptionService encryptionService, FileService fileService, UserService userService) {
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/credentials")
    public String addUpdateCredential(Authentication authentication, CredentialForm credentialForm, NoteForm noteForm, FileForm fileForm, Model model) {
        // Check if data exists in database already
        // We use this to update our data by searching
        // for our unique id.
        String success = null;

        // Get the current logged in user details
        this.credentialService.trackLoggedInUserId(authentication.getName());

        if(this.credentialService.doesCredentialExist(credentialForm)) {
            this.credentialService.updateCredential(credentialForm);
            success = "Credential " + credentialForm.getUsername() + " was updated successfully!";
        } else {
            this.credentialService.createCredential(credentialForm);
            success = "Credential " + credentialForm.getUsername() + " was created!";
        }

        getCredentialDetails(authentication, model);
        model.addAttribute("success", success);

        return "home";
    }

    @GetMapping("/credentials/delete/{credentialid}")
    public String deleteCredential(@PathVariable("credentialid") Integer credentialId, Authentication authentication, CredentialForm credentialForm, NoteForm noteForm, FileForm fileForm, Model model) {
        this.credentialService.deleteCredential(credentialId);
        getCredentialDetails(authentication, model);
        model.addAttribute("success", "Credential was deleted successfully");

        return "home";
    }

    private void getCredentialDetails(Authentication authentication, Model model) {
        model.addAttribute("credentials", this.credentialService.getAllCredentials());
        model.addAttribute("notes", this.noteService.getNotes());
        model.addAttribute("encryption", this.encryptionService);
        model.addAttribute("files", this.fileService.getFiles());
        model.addAttribute("currentid", this.userService.getUserId(authentication.getName()));
    }
}
