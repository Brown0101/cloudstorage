package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Objects;

@Controller
public class FileController {

    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private UserService userService;

    public FileController(FileService fileService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService, UserService userService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.userService = userService;
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

        String error = null;
        String success = null;

        // Need to get current userId value
        this.fileService.trackLoggedInUserId(authentication.getName());

        // Check if data exists in database already
        // We use this to update our data by searching
        // for our unique id or name is blank.
        if(fileForm.getFileName().isBlank() || fileForm.getFileName().isEmpty() || fileForm.getFileName().equals(null)) {
            error = "Sorry, you must attach a file prior to uploading.";
        } else if(this.fileService.doesFileExist(fileForm)) {
            error = "Sorry, files containing the same name can't up uploaded. Trying renaming your file then upload it again.";
        } else {
            this.fileService.createFile(fileForm);
            success = fileForm.getFileName() + " was uploaded successfully!";
        }

        model.addAttribute("files", this.fileService.getFiles());
        model.addAttribute("notes", this.noteService.getNotes());
        model.addAttribute("credentials", this.credentialService.getAllCredentials());
        model.addAttribute("encryption", this.encryptionService);
        model.addAttribute("encryption", this.encryptionService);
        model.addAttribute("currentid", this.userService.getUserId(authentication.getName()));
        model.addAttribute("error", error);
        model.addAttribute("success", success);

        return "home";
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource>  downloadFile(Authentication authentication, @RequestParam String fileName, FileForm fileForm, Model model) {

        this.fileService.trackLoggedInUserId(authentication.getName());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(this.fileService.getSingleFile(fileName).getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileService.getSingleFile(fileName).getFileName() + "\"")
                .body(new ByteArrayResource(fileService.getSingleFile(fileName).getFileData()));
    }

    @GetMapping("/files/delete/{fileid}")
    public String deleteNote(@PathVariable("fileid") Integer fileId, Authentication authentication, FileForm fileForm, NoteForm noteForm, CredentialForm credentialForm, Model model) {
        this.fileService.deleteFile(fileId);

        model.addAttribute("files", this.fileService.getFiles());
        model.addAttribute("notes", this.noteService.getNotes());
        model.addAttribute("credentials", this.credentialService.getAllCredentials());
        model.addAttribute("encryption", this.encryptionService);
        model.addAttribute("currentid", this.userService.getUserId(authentication.getName()));
        model.addAttribute("success", "File was deleted successfully!");

        return "home";
    }
}
