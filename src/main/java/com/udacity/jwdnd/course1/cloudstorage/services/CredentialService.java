package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;
    private final UserService userService;
    private Integer id;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, UserService userService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.userService = userService;
        this.id = null;
    }

    public void createCredential(CredentialForm credentialForm, String username) {
        Credential credential = new Credential();
        credential.setCredentialId(credentialForm.getCredentialId());
        credential.setUrl(credentialForm.getUrl());
        credential.setKey(credentialForm.getKey());
        credential.setUsername(credentialForm.getUsername());
        credential.setPassword(credentialForm.getPassword());
        credential.setUserId(this.id);
        this.credentialMapper.insertCredential(credential);
    }

    public void updateCredential(CredentialForm credentialForm) {
        Credential credential = new Credential();
        credential.setCredentialId(credentialForm.getCredentialId());
        credential.setUrl(credentialForm.getUrl());
        credential.setKey(credentialForm.getKey());
        credential.setUsername(credentialForm.getUsername());
        credential.setPassword(credentialForm.getPassword());


        this.credentialMapper.updateCredential(credential);
    }

    public void deleteCredential(Integer credentialId) {
        this.credentialMapper.deleteCredential(credentialId);
    }

    public Boolean doesCredentialExist(CredentialForm credentialForm) {
        Credential credential = new Credential();
        credential.setCredentialId(credentialForm.getCredentialId());
        Integer idReturned = this.credentialMapper.getCredential(credential);

        if(idReturned != null) {
            return true;
        } else {
            return false;
        }
    }

    public List<Credential> getCredentails() {
        return credentialMapper.getCredentials(this.id);
    }

    public void trackLoggedInUserId(String username) {
        this.id = userService.getUser(username).getUserId();
    }
}
