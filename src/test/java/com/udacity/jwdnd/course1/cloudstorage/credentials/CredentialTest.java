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
        try {
            Thread.sleep(1000);
            jse.executeScript("arguments[0].click()", this.navCredentialsTab);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Failed to find Note tab...");
        }
    }

    public void addCredential(int index) {
        String[] urls = {"http://www.yahoo.com", "http://www.google.com", "http://www.amazon.com"};
        String[] usernames = {"admin", "staff", "repository"};
        String[] passwords = {"password123", "supersecrete", "onlyatest"};
        try {
            Thread.sleep(1000);
            new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.elementToBeClickable(this.addCredentialButton))
                    .click();
            new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.visibilityOf(this.credentialUrlText))
                    .sendKeys(urls[index]);
            new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.visibilityOf(this.credentialUsernameText))
                    .sendKeys(usernames[index]);
            new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.visibilityOf(this.credentialPasswordText))
                    .sendKeys(passwords[index]);
            this.credentialSaveButton.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Boolean editStaffCredential() {
        try {
            Thread.sleep(1000);
            new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.elementToBeClickable(this.editStaffButton))
                    .click();
            Thread.sleep(1000);
            String staffEncryptedPassword = new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.elementToBeClickable(this.staffCurrentCredentials))
                    .getText();
            new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.visibilityOf(this.credentialPasswordText))
                    .clear();
            new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.visibilityOf(this.credentialPasswordText))
                    .sendKeys("superdupersecrete");
            this.credentialSaveButton.click();
            Thread.sleep(1000);
            this.openCredentialsTab();
            Thread.sleep(1000);
            String newStaffEncryptedPassword = new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.elementToBeClickable(this.staffCurrentCredentials))
                    .getText();
            Thread.sleep(1000);
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
        try {
            Thread.sleep(1000);
            new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.elementToBeClickable(this.editStaffButton))
                    .click();
            Thread.sleep(1000);
            String staffUnencrytedPassword = new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.visibilityOf(this.credentialPasswordText))
                    .getAttribute("value");
            this.closeStaffButton.click();
            Thread.sleep(1000);

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
        try {
            Thread.sleep(2000);
            String adminPassword = new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.elementToBeClickable(this.adminCurrentCredentials))
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

    public Boolean deleteRepositoryCredentials() {
        try {
            Thread.sleep(1000);
            if(this.repositoryCurrentStatus.getText().length() > 0) {
                System.out.println("Repository is an active entry");
            }

            System.out.println("Deleting repository entry.");
            this.deleteRepositoryButton.click();

            // If entry does not exist this will jump into the catch area as expected.
            Boolean isActive = this.repositoryCurrentStatus.getText().length() > 0;
            System.out.println("FAILED: Entry still exists!");
            return false;
        } catch (Exception e) {
            System.out.println("Entry no longer exists!");
            return true;
        }
    }
}
