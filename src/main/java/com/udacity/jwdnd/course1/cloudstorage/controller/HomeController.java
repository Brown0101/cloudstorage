package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private FileService fileService;
    private UserService userService;

    public HomeController(NoteService noteService, CredentialService credentialService, EncryptionService encryptionService, FileService fileService, UserService userService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping
    public String home(Authentication authentication, NoteForm noteForm, CredentialForm credentialForm, FileForm fileForm, Model model) {
        String username = authentication.getName();
        this.noteService.trackLoggedInUserId(username);
        this.credentialService.trackLoggedInUserId(username);
        this.fileService.trackLoggedInUserId(username);

        getHomeDetails(authentication, model, this.noteService, this.credentialService, this.encryptionService, this.fileService, this.userService);

        return "home";
    }

    static void getHomeDetails(Authentication authentication, Model model, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService, FileService fileService, UserService userService) {
        model.addAttribute("notes", noteService.getNotes());
        model.addAttribute("credentials", credentialService.getAllCredentials());
        model.addAttribute("encryption", encryptionService);
        model.addAttribute("files", fileService.getFiles());
        model.addAttribute("currentid", userService.getUserId(authentication.getName()));
    }
}
