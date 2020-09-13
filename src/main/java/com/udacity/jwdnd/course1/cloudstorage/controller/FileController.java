package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Controller
public class FileController {

    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public FileController(FileService fileService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/files")
    public String getFile(FileForm fileForm, NoteForm noteForm, CredentialForm credentialForm, Model model) {
        model.addAttribute("files", this.fileService.getFiles());
        model.addAttribute("notes", this.noteService.getNotes());
        model.addAttribute("credentials", this.credentialService.getAllCredentials());
        model.addAttribute("encryption", this.encryptionService);

        return "home";
    }

    @PostMapping("/files")
    public String addUpdateFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile file, FileForm fileForm, NoteForm noteForm, CredentialForm credentialForm, Model model) {
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            fileForm.setFileName(fileName);
            fileForm.setContentType(file.getContentType());
            fileForm.setFileSize("" + file.getSize());
            fileForm.setFileData(file.getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Check if data exists in database already
        // We use this to update our data by searching
        // for our unique id.
        System.out.println("========================================================");
        System.out.println("========================================================");
        System.out.println("========================================================");
        System.out.println("               " + fileForm.getFileName() + "               ");
        System.out.println("               " + fileForm.getContentType() + "               ");
        System.out.println("               " + fileForm.getFileData() + "               ");
        System.out.println("               " + fileForm.getFileSize() + "               ");
        System.out.println("========================================================");
        System.out.println("========================================================");
        System.out.println("========================================================");

        if(this.fileService.doesFileExist(fileForm)) {
            this.fileService.updateFile(fileForm);
        } else {
            this.fileService.trackLoggedInUserId(authentication.getName());
            this.fileService.createFile(fileForm);
        }

        model.addAttribute("files", this.fileService.getFiles());
        model.addAttribute("notes", this.noteService.getNotes());
        model.addAttribute("credentials", this.credentialService.getAllCredentials());
        model.addAttribute("encryption", this.encryptionService);

        return "home";
    }

    @GetMapping("/files/delete/{fileid}")
    public String deleteNote(@PathVariable("fileid") Integer fileId, FileForm fileForm, NoteForm noteForm, CredentialForm credentialForm, Model model) {
        this.fileService.deleteFile(fileId);

        model.addAttribute("files", this.fileService.getFiles());
        model.addAttribute("notes", this.noteService.getNotes());
        model.addAttribute("credentials", this.credentialService.getAllCredentials());
        model.addAttribute("encryption", this.encryptionService);

        return "home";
    }
}
