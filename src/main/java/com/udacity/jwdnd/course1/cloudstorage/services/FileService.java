package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.FileForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;
    private final UserService userService;
    private Integer userId;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
        this.userId = null;
    }

    public void createFile(FileForm fileForm) {
        File file = new File();
//        note.setNoteTitle(noteForm.getNoteTitle());
//        note.setNoteDescription(noteForm.getNoteDescription());
//        note.setUserId(this.userId);
//
//        this.noteMapper.insertNote(note);
    }

    public void updateFile(FileForm fileForm) {
        File file = new File();
//        note.setNoteId(noteForm.getNoteId());
//        note.setNoteTitle(noteForm.getNoteTitle());
//        note.setNoteDescription(noteForm.getNoteDescription());
//
//        this.noteMapper.updateNote(note);
    }

    public void deleteFile(Integer fileId) {
        this.fileMapper.deleteFile(fileId);
    }

    public Boolean doesFileExist(FileForm fileForm) {
        File file = new File();
        file.setFileId(fileForm.getFileId());
        Integer idReturned = this.fileMapper.getFile(file);

        if(idReturned != null) {
            return true;
        } else {
            return false;
        }
    }

    public List<File> getFiles() {
        return fileMapper.getFiles(this.userId);
    }

    public void trackLoggedInUserId(String username) {
        this.userId = userService.getUser(username).getUserId();
    }
}
