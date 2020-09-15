package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NotesController {
    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private FileService fileService;

    public NotesController(NoteService noteService, CredentialService credentialService, EncryptionService encryptionService, FileService fileService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
    }

    @GetMapping("/notes")
    public String getNotes(NoteForm noteForm, CredentialForm credentialForm, FileForm fileForm, Model model) {
        model.addAttribute("notes", this.noteService.getNotes());
        model.addAttribute("credentials", this.credentialService.getAllCredentials());
        model.addAttribute("encryption", this.encryptionService);
        model.addAttribute("files", this.fileService.getFiles());

        return "home";
    }

    @PostMapping("/notes")
    public String addUpdateNotes(Authentication authentication, NoteForm noteForm, CredentialForm credentialForm, FileForm fileForm, Model model) {
        // Check if data exists in database already
        // We use this to update our data by searching
        // for our unique id.
        String success = null;

        if(this.noteService.doesNoteExist(noteForm)) {
            this.noteService.updateNote(noteForm);
            success = "Note " + noteForm.getNoteTitle() + " was modified successfully!";
        } else {
            this.noteService.trackLoggedInUserId(authentication.getName());
            this.noteService.createNote(noteForm);
            success = "Note: " + noteForm.getNoteTitle() + " was created successfully!";
        }

        model.addAttribute("notes", this.noteService.getNotes());
        model.addAttribute("credentials", this.credentialService.getAllCredentials());
        model.addAttribute("encryption", this.encryptionService);
        model.addAttribute("files", this.fileService.getFiles());
        model.addAttribute("success", success);

        return "home";
    }

    @GetMapping("/notes/delete/{noteid}")
    public String deleteNotes(@PathVariable("noteid") Integer noteId, NoteForm noteForm, CredentialForm credentialForm, FileForm fileForm, Model model) {
        this.noteService.deleteNote(noteId);
        model.addAttribute("notes", this.noteService.getNotes());
        model.addAttribute("credentials", this.credentialService.getAllCredentials());
        model.addAttribute("encryption", this.encryptionService);
        model.addAttribute("files", this.fileService.getFiles());
        model.addAttribute("success", "Note was deleted successfully.");

        return "home";
    }
}
