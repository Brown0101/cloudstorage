package com.udacity.jwdnd.course1.cloudstorage.registration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistrationTest {

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
        PageFactory.initElements(driver, this);
    }

    public void clickRegisterLink() {
        signupLink.click();
    }

    public void registerAccount() {
        try{
            Thread.sleep(1000);
            System.out.println("Sending Keys to input fields!");
            firstName.sendKeys("Frank");
            lastName.sendKeys("Dukes");
            username.sendKeys("fdukes");
            password.sendKeys("superSecret");
            submitButton.click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void getLoginPage() {
        try {
            Thread.sleep(1000);
            loginLink.click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loginToAccount() {
        try{
            Thread.sleep(1000);
            System.out.println("Sending Keys to input fields!");
            username.sendKeys("fdukes");
            Thread.sleep(1000);
            password.sendKeys("superSecret");
            Thread.sleep(1000);
            submitButton.click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void logoutAccount() {
        try{
            Thread.sleep(1000);
            System.out.println("Sending Keys to input fields!");
            logoutButton.click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}