package com.udacity.jwdnd.course1.cloudstorage.credentials;

import org.h2.mvstore.FreeSpaceBitSet;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.aop.scope.ScopedProxyUtils;


public class CredentialTest {
    private JavascriptExecutor jse;
    private WebDriver driver;

    @FindBy(xpath="//*[@id=\"nav-credentials-tab\"]")
    private WebElement navCredentialsTab;

    @FindBy(css="#nav-credentials > button")
    private WebElement addCredentialButton;

    @FindBy(xpath="//*[@id=\"credential-url\"]")
    private WebElement credentialUrlText;

    @FindBy(xpath="//*[@id=\"credential-username\"]")
    private WebElement credentialUsernameText;

    @FindBy(xpath="//*[@id=\"credential-password\"]")
    private WebElement credentialPasswordText;

    @FindBy(xpath="//*[@id=\"credentialModal\"]/div/div/div[3]/button[2]")
    private WebElement credentialSaveButton;

    @FindBy(css="#userTable > tbody > tr > th")
    private WebElement isNoteTitleVisable;

    @FindBy(css="#credentialTable > tbody:nth-child(2) > tr > td:nth-child(4)")
    private WebElement adminCurrentCredentials;

    @FindBy(css="#credentialTable > tbody:nth-child(3) > tr > td:nth-child(4)")
    private WebElement staffCurrentCredentials;

    @FindBy(css="#credentialTable > tbody:nth-child(4) > tr > td:nth-child(3)")
    private WebElement repositoryCurrentStatus;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody[1]/tr/td[1]/button")
    private WebElement editAdminButton;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody[2]/tr/td[1]/button")
    private WebElement editStaffButton;

    @FindBy(xpath = "//*[@id=\"credentialModal\"]/div/div/div[3]/button[1]")
    private WebElement closeStaffButton;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody[3]/tr/td[1]/a")
    private WebElement deleteRepositoryButton;

    public CredentialTest(WebDriver driver) {
        this.driver = driver;
        this.jse = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    public void openCredentialsTab() {
        System.out.println("Opening the credentials tab");
        try {
            Thread.sleep(1000);
            jse.executeScript("arguments[0].click()", this.navCredentialsTab);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addCredential(int index) {
        System.out.println("Created test data and adding three new credentials");
        String[] urls = {"http://www.yahoo.com", "http://www.google.com", "http://www.amazon.com"};
        String[] usernames = {"admin", "staff", "repository"};
        String[] passwords = {"password123", "supersecrete", "onlyatest"};
        try {
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.elementToBeClickable(this.addCredentialButton))
                    .click();
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.credentialUrlText))
                    .sendKeys(urls[index]);
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.credentialUsernameText))
                    .sendKeys(usernames[index]);
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.credentialPasswordText))
                    .sendKeys(passwords[index]);
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.elementToBeClickable(this.credentialSaveButton))
                    .click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Boolean editStaffCredential() {
        System.out.println("Validating if we can edit using staff test credentials");
        try {
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.elementToBeClickable(this.editStaffButton))
                    .click();
            String staffEncryptedPassword = new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.elementToBeClickable(this.staffCurrentCredentials))
                    .getText();
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.credentialPasswordText))
                    .clear();
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.credentialPasswordText))
                    .sendKeys("superdupersecrete");
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.elementToBeClickable(this.credentialSaveButton))
                    .click();
            this.openCredentialsTab();
            String newStaffEncryptedPassword = new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.elementToBeClickable(this.staffCurrentCredentials))
                    .getText();
            if( !(staffEncryptedPassword.equals(newStaffEncryptedPassword)) ) {
                System.out.println("Staff ENCRYPTED password of \"" + staffEncryptedPassword + "\" CHANGED to \"" + newStaffEncryptedPassword);
                return true;
            } else {
                System.out.println("FAILED: \"" + staffEncryptedPassword + "\" EQUALS \"" + newStaffEncryptedPassword);
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Boolean visibleStaffCredential() {
        System.out.println("Validating if staff credentials are visible");
        try {
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.elementToBeClickable(this.editStaffButton))
                    .click();
            String staffUnencrytedPassword = new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.credentialPasswordText))
                    .getAttribute("value");
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.elementToBeClickable(this.closeStaffButton))
                    .click();

            if(staffUnencrytedPassword.equals("supersecrete")) {
                System.out.println("Staff UNENCRYPTED password of \"" + staffUnencrytedPassword + "\" EQUALS \"supersecrete\" meaning it is viewable.");
                return true;
            } else {
                System.out.println("FAILED:  \"" + staffUnencrytedPassword + "\" does NOT equal \"supersecrete\"");
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean getCurrentAdminEncryptedCredentails() {
        System.out.println("Validating if admin test account credentials are encrypted");
        try {
            Thread.sleep(2000);
            String adminPassword = new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.adminCurrentCredentials))
                    .getText();

            if(adminPassword != "password123") {
                System.out.println("Admin password of \"" + adminPassword + "\" does not match \"password123\"");
                return true;
            } else {
                System.out.println("FAILED: password \"" + adminPassword + " SHOULD be encrypted!");
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteRepositoryCredentials() {
        System.out.println("Deleting the repository test credentials");
        try {
            Thread.sleep(2000);
            if(this.repositoryCurrentStatus.getText().length() > 0) {
                System.out.println("Repository is an active entry");
            }

            System.out.println("Deleting repository entry.");
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.elementToBeClickable(this.deleteRepositoryButton))
                    .click();

            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Boolean validateRepositoryCredentialsExist() {
        try {
            Thread.sleep(2000);

            // If entry does not exist this will jump into the catch area as expected.
            Boolean repoCredentialsExist = this.repositoryCurrentStatus.getText().length() > 0;

            return repoCredentialsExist;
        } catch (Exception e) {
            System.out.println("Entry no longer exists!");
            return true;
        }
    }
}
