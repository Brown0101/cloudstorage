package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;
    private final UserService userService;
    private Integer id;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, UserService userService) {
        System.out.println("Creating Credential Service");
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.userService = userService;
        this.id = null;
    }

    public void createCredential(CredentialForm credentialForm) {
        // Setup encryption
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);

        // Assign values
        Credential credential = new Credential();
        credential.setUrl(credentialForm.getUrl());
        credential.setKey(encodedKey);
        credential.setUsername(credentialForm.getUsername());
        credential.setPassword(encryptedPassword);
        credential.setUserId(this.id);

        this.credentialMapper.insertCredential(credential);

        int count = 1;
        for(Credential cred : this.getAllCredentials()) {
            System.out.println("==============================================");
            System.out.println(count++ + ": " + cred.getUrl());
            System.out.println("==============================================");
        }
    }

    public void updateCredential(CredentialForm credentialForm) {
        // Setup encryption
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);

        // Assign values
        Credential credential = new Credential();
        credential.setCredentialId(credentialForm.getCredentialId());
        credential.setUrl(credentialForm.getUrl());
        credential.setKey(encodedKey);
        credential.setUsername(credentialForm.getUsername());
        credential.setPassword(encryptedPassword);
        System.out.println("Dude! Why am I trying to update LOL");
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

    public List<Credential> getAllCredentials() {
        return credentialMapper.getCredentials(this.id);
    }

    public void trackLoggedInUserId(String username) {
        this.id = userService.getUser(username).getUserId();
    }
}
