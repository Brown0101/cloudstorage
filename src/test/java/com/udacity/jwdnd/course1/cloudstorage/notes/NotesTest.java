package com.udacity.jwdnd.course1.cloudstorage.notes;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class NotesTest {
    private JavascriptExecutor jse;
    private WebDriver driver;

    @FindBy(xpath="/html/body/div/div[2]/nav/div/a[2]")
    private WebElement navNotestab;

    @FindBy(css="#nav-notes > button")
    private WebElement addNoteButton;

    @FindBy(xpath="//*[@id=\"note-title\"]")
    private WebElement noteTitleText;

    @FindBy(xpath="//*[@id=\"note-description\"]")
    private WebElement noteDescriptionText;

    @FindBy(xpath="//*[@id=\"noteModal\"]/div/div/div[3]/button[2]")
    private WebElement noteSaveButton;

    @FindBy(css="#userTable > tbody > tr > th")
    private WebElement isNoteTitleVisable;

    @FindBy(css="#userTable > tbody > tr > td:nth-child(3)")
    private WebElement isNoteDescriptionVisable;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/td[1]/button")
    private WebElement editNoteButton;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/td[1]/a")
    private WebElement deleteNoteButton;

    public NotesTest(WebDriver driver) {
        this.driver = driver;
        this.jse = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    public void openNotesTab() {
        System.out.println("Opening the Notes tab");
        try {
            Thread.sleep(1000);
            jse.executeScript("arguments[0].click()", this.navNotestab);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addANote() {
        System.out.println("Adding a note");
        try {
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.elementToBeClickable(this.addNoteButton))
                    .click();
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.noteTitleText))
                    .sendKeys("Note Title Test");
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.noteDescriptionText))
                    .sendKeys("Here is my example description for my test note!");
            new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.elementToBeClickable(this.noteSaveButton))
                    .click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void editANote() {
        System.out.println("Editing the existing test note");
        try {
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.elementToBeClickable(this.editNoteButton))
                    .click();
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.noteTitleText))
                    .clear();
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.noteDescriptionText))
                    .clear();
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.noteTitleText))
                    .sendKeys("Changed Title");
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.visibilityOf(this.noteDescriptionText))
                    .sendKeys("Changed my description!");
            new WebDriverWait(this.driver, 5)
                    .until(ExpectedConditions.elementToBeClickable(this.noteSaveButton))
                    .click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteNote() {
        System.out.println("Deleting our existing note");
        try {
            new WebDriverWait(this.driver, 15)
                    .until(ExpectedConditions.elementToBeClickable(this.deleteNoteButton))
                    .click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Boolean editNoteVisible() {
        System.out.println("Validating note can be edited");
        try {
            if(this.isNoteTitleVisable.getText().contains("Changed Title") &&
                    this.isNoteDescriptionVisable.getText().contains("Changed my description!")){
                return true;
            }
            return false;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Boolean addNoteVisible() {
        System.out.println("Validate we can add a new note");
        try {
            if(this.isNoteTitleVisable.getText().contains("Note Title Test") &&
                    this.isNoteDescriptionVisable.getText()
                            .contains("Here is my example description for my test note!")) {
                return true;
            }
            return false;
        } catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public Boolean deleteNoteVisible() {
        System.out.println("Validate we can delete a note");
        try {
            // This will fail to find elements if they do nto exist.
            System.out.println(this.isNoteTitleVisable.getText());
            System.out.println(this.isNoteDescriptionVisable.getText());
            return false;
        } catch (Exception e){
            return true;
        }
    }
}