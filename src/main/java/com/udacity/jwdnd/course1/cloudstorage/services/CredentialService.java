package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;
    private final UserService userService;
    private Integer userId;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, UserService userService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.userService = userService;
        this.userId = null;
    }

    public void createCredential(CredentialForm credentialForm) {
        // Setup encryption
        String encodedKey = this.generateEncodedKey();
        String encryptedPassword = this.createEncryptedPassword(credentialForm.getPassword(), encodedKey);

        // Assign values
        Credential credential = new Credential();
        credential.setUrl(credentialForm.getUrl());
        credential.setKey(encodedKey);
        credential.setUsername(credentialForm.getUsername());
        credential.setPassword(encryptedPassword);
        credential.setUserId(this.userId);

        this.credentialMapper.insertCredential(credential);
    }

    public void updateCredential(CredentialForm credentialForm) {
        // Setup encryption
        String encodedKey = this.generateEncodedKey();
        String encryptedPassword = this.createEncryptedPassword(credentialForm.getPassword(), encodedKey);

        // Assign values
        Credential credential = new Credential();
        credential.setCredentialId(credentialForm.getCredentialId());
        credential.setUrl(credentialForm.getUrl());
        credential.setKey(encodedKey);
        credential.setUsername(credentialForm.getUsername());
        credential.setPassword(encryptedPassword);

        this.credentialMapper.updateCredential(credential);
    }

    public void deleteCredential(Integer credentialId) {
        this.credentialMapper.deleteCredential(credentialId);
    }

    public Boolean doesCredentialExist(CredentialForm credentialForm) {
        Credential credential = new Credential();
        credential.setCredentialId(credentialForm.getCredentialId());

        for(Credential c : this.getAllCredentials()) {
            if(c.getUsername().equals(c.getUsername())) {
                return true;
            }
        }

        return false;
    }

    private String generateEncodedKey() {
        // Setup key
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);

        return Base64.getEncoder().encodeToString(key);
    }

    private String createEncryptedPassword(String password, String encodedKey) {
        // Setup password
        return encryptionService.encryptValue(password, encodedKey);
    }

    public List<Credential> getAllCredentials() {
        return credentialMapper.getCredentials(this.userId);
    }

    public void trackLoggedInUserId(String username) {
        this.userId = userService.getUser(username).getUserId();
    }
}
