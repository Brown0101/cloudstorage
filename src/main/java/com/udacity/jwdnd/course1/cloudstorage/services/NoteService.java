package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    private final UserService userService;
    private Integer id;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
        this.id = null;
    }

    public void createNote(NoteForm noteForm) {
        Note note = new Note();
        note.setNoteTitle(noteForm.getNoteTitle());
        note.setNoteDescription(noteForm.getNoteDescription());
        note.setUserId(this.id);
        this.noteMapper.insertNote(note);
    }

    public void updateNote(NoteForm noteForm) {
        Note note = new Note();
        note.setNoteId(noteForm.getNoteId());
        note.setNoteTitle(noteForm.getNoteTitle());
        note.setNoteDescription(noteForm.getNoteDescription());

        this.noteMapper.updateNote(note);
    }

    public void deleteNote(Integer noteId) {
        this.noteMapper.deleteNote(noteId);
    }

    public Boolean doesNoteExist(NoteForm noteForm) {
        Note note = new Note();
        note.setNoteId(noteForm.getNoteId());
        Integer idReturned = this.noteMapper.getNote(note);

        if(idReturned != null) {
            return true;
        } else {
            return false;
        }
    }

    public List<Note> getNotes() {
        return noteMapper.getNotes(this.id);
    }

    public void trackLoggedInUserId(String username) {
        this.id = userService.getUser(username).getUserId();
    }

}
