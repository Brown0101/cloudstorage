package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    NoteService noteService;

    public HomeController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String home(NoteForm noteForm, Model model) {
        model.addAttribute("notes", noteService.getNotes());
        return "home";
    }

    @PostMapping
    public String home(Authentication authentication, NoteForm noteForm, Model model) {
        noteService.trackLoggedInUserId(authentication.getName());
        noteService.createNote(noteForm, authentication.getName());
        List<Note> items = noteService.getNotes();
        for (Note item : items) {
            System.out.println("Title: " + item.getNoteTitle());
            System.out.println("Description: " + item.getNoteDescription());
        }
        model.addAttribute("notes", noteService.getNotes());
        return "home";
    }

}
