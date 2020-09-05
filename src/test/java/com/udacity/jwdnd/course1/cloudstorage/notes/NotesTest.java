package com.udacity.jwdnd.course1.cloudstorage.notes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class NotesTest {
    private WebDriverWait wait;

    @FindBy(id="nav-notes-tab")
    private WebElement navNotestab;

    @FindBy(css="#nav-notes > button")
    private WebElement addNoteButton;

    @FindBy(css="note-title")
    private WebElement noteTitleText;

    @FindBy(css="note-description")
    private WebElement noteDescriptionText;

    @FindBy(css="#noteModal > div > div > div.modal-footer > button.btn.btn-primary")
    private WebElement noteSaveButton;

    @FindBy(css="#userTable > tbody > tr > th")
    private WebElement isNoteVisable;

    public NotesTest(WebDriver driver) {
        wait = new WebDriverWait (driver, 500);
        PageFactory.initElements(driver, this);
    }

    public void openNotesTab() {
        try {
            navNotestab.click();
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("Failed to find Note tab...");
        }
    }

    public void addANote() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(addNoteButton)).click();
            noteTitleText.sendKeys("Note Title Test");
            noteDescriptionText.sendKeys("Here is my example description for my test note!");
            Thread.sleep(2000);
            noteSaveButton.click();
        } catch (Exception e) {
            System.out.println("Failed to find Note tab...");
        }
    }

    public Boolean visibleNote() {
        try {
            isNoteVisable.getText();
            return true;
        } catch (Exception e){
            System.out.println("Unable to find element on page.");
            return false;
        }
    }
}
