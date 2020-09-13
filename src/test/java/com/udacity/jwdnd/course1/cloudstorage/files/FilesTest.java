package com.udacity.jwdnd.course1.cloudstorage.files;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

public class FilesTest {

    private WebDriver driver;

    @FindBy(xpath="//*[@id=\"fileUpload\"]")
    private WebElement chooseFileButton;

    @FindBy(css="#nav-files > form > div > div > div.col-sm-4 > button")
    private WebElement uploadFileButton;

    @FindBy(xpath="//*[@id=\"fileTable\"]/tbody/tr/td/a[1]")
    private WebElement viewFileButton;

    @FindBy(xpath="//*[@id=\"fileTable\"]/tbody/tr/td/a[2]")
    private WebElement deleteFileButton;

    @FindBy(xpath="//*[@id=\"fileTable\"]/tbody/tr/th")
    private WebElement validateTestFileExists;

    public FilesTest(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void uploadFile() {
        System.out.println("Uploading file from directory.");
        try {
            String filePath = "C:\\Users\\dwayn\\OneDrive\\Pictures\\battery.png";
            Thread.sleep(1000);
            new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.visibilityOf(this.chooseFileButton))
                    .sendKeys(filePath);
            new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.elementToBeClickable(this.uploadFileButton))
                    .click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewFile() {
        System.out.println("Downloading file to the downloads directory.");
        try {
            Thread.sleep(1000);
            new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.elementToBeClickable(this.viewFileButton))
                    .click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteFile() {
        System.out.println("Starting test to delete the uploaded file on the site.");
        try {
            Thread.sleep(1000);
            new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.elementToBeClickable(this.deleteFileButton))
                    .click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Boolean fileUploadedExists() {
        System.out.println("Validate uploaded file exists.");
        try {
            Thread.sleep(1000);
            new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.visibilityOf(this.validateTestFileExists))
                    .getText();

            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public Boolean fileDownloaded() {
        System.out.println("Starting test to validate file downloaded exists in the downloads directory.");
        try {
            File file = new File("C:\\Users\\dwayn\\Downloads\\battery.png");
            if(!file.exists() && !file.isDirectory()) {
                System.out.println("File Found.");
                return true;
            } else {
                System.out.println("Could not find test file.");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
