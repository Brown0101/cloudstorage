package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NotesController {
    NoteService noteService;

    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/notes")
    public String getNotes(NoteForm noteForm, Model model) {
        model.addAttribute("notes", this.noteService.getNotes());
        return "home";
    }

    @PostMapping("/notes")
    public String addUpdateNotes(Authentication authentication, NoteForm noteForm, Model model) {
        // Check if data exists in database already
        // We use this to update our data by searching
        // for our unique id.
        if(this.noteService.doesNoteExist(noteForm)) {
            this.noteService.updateNote(noteForm);
        } else {
            this.noteService.trackLoggedInUserId(authentication.getName());
            this.noteService.createNote(noteForm, authentication.getName());
        }

        model.addAttribute("notes", this.noteService.getNotes());
        return "home";
    }

    @GetMapping("/notes/delete/{noteid}")
    public String deleteNotes(@PathVariable("noteid") Integer noteId, NoteForm noteForm, Model model) {
        this.noteService.deleteNote(noteId);
        model.addAttribute("notes", this.noteService.getNotes());
        return "home";
    }
}
