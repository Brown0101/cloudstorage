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
        file.setUserId(this.userId);
        file.setFileName(fileForm.getFileName());
        file.setContentType(fileForm.getContentType());
        file.setFileSize(fileForm.getFileSize());
        file.setFileData(fileForm.getFileData());

        this.fileMapper.insertFile(file);
    }

    public File getSingleFile(String fileName) {
        File file = new File();

        return fileMapper.getFileData(fileName);
    }

    public void deleteFile(Integer fileId) {
        this.fileMapper.deleteFile(fileId);
    }

    public Boolean doesFileExist(FileForm fileForm) {
        File file = new File();
        file.setFileName(fileForm.getFileName());
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
