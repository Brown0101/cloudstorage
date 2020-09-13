package com.udacity.jwdnd.course1.cloudstorage.registration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationTest {
    private WebDriver driver;

    @FindBy(id = "signup-link")
    private WebElement signupLink;

    @FindBy(name = "firstName")
    private WebElement firstName;

    @FindBy(name = "lastName")
    private WebElement lastName;

    @FindBy(name= "username")
    private WebElement username;

    @FindBy(name = "password")
    private WebElement password;

    @FindBy(css = "body > div > form > button")
    private WebElement submitButton;

    @FindBy(id = "login-link")
    private WebElement loginLink;

    @FindBy(css = "#logoutDiv > form > button")
    private WebElement logoutButton;

    public RegistrationTest(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickRegisterLink() {
        System.out.println("Clicking the registration link");
        try {
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.elementToBeClickable(this.signupLink))
                    .click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void registerAccount() {
        System.out.println("Registering new test account to H2 database");
        try{
            Thread.sleep(2000); // Without this thread jumps over the below conditions.
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.firstName))
                    .sendKeys("Frank");
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.lastName))
                    .sendKeys("Dukes");
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.username))
                    .sendKeys("fdukes");
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.password))
                    .sendKeys("superSecret");
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.elementToBeClickable(this.submitButton))
                    .click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void getLoginPage() {
        System.out.println("Opening the login page");
        try {
            Thread.sleep(2000); // Without this thread jumps over the below conditions.
            new WebDriverWait(this.driver, 1000)
                    .until(ExpectedConditions.elementToBeClickable(this.loginLink))
                    .click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loginToAccount() {
        System.out.println("Logging into the account with the demo user");
        try{
            Thread.sleep(2000); // Without this thread jumps over the below conditions.
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.username))
                    .sendKeys("fdukes");
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.password))
                    .sendKeys("superSecret");
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.elementToBeClickable(this.submitButton))
                    .click();
            Thread.sleep(2000); // Pause so the validation is not skipped for home page
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void logoutAccount() {
        System.out.println("Logging out of the account");
        try{
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.elementToBeClickable(this.logoutButton))
                    .click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}