package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.credentials.CredentialTest;
import com.udacity.jwdnd.course1.cloudstorage.files.FilesTest;
import com.udacity.jwdnd.course1.cloudstorage.notes.NotesTest;
import com.udacity.jwdnd.course1.cloudstorage.registration.RegistrationTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

/* IMPORTANT - We are using in memory database for this app.
   If this is changed to not use in memory the testing can be
   greatly improved. Also, thread.sleep is not preferred but
   I use it to see some parts of the application in action
   since it moves so fast through each process.
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void verifyUserCanAccessTheLoginPageTest() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void verifyUserCanOnlyAccessLoginPageTest() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(3)
	public void verifySignupLoginAndGetsHomePageLogoutTest() {
		driver.get("http://localhost:" + this.port + "/login");
		RegistrationTest registrationTest = new RegistrationTest(driver);
		registrationTest.clickRegisterLink();
		registrationTest.registerAccount();
		registrationTest.loginToAccount();
		Assertions.assertEquals("Home", driver.getTitle());
		registrationTest.logoutAccount();
		Assertions.assertNotEquals("Home", driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(4)
	public void createViewEditDeleteANoteTest() {
		driver.get("http://localhost:" + this.port + "/login");
		RegistrationTest registrationTest = new RegistrationTest(driver);
		registrationTest.loginToAccount();

		NotesTest notesTest = new NotesTest(driver);

		// Add a note
		notesTest.openNotesTab();
		notesTest.addANote();
		notesTest.openNotesTab();
		Assertions.assertEquals(true, notesTest.addNoteVisible());

		// Edit a note
		notesTest.openNotesTab();
		notesTest.editANote();
		notesTest.openNotesTab();
		Assertions.assertEquals(true, notesTest.editNoteVisible());

		// Delete a note
		notesTest.openNotesTab();
		notesTest.deleteNote();
		notesTest.openNotesTab();
		Assertions.assertEquals(true, notesTest.deleteNoteVisible());
	}

	@Test
	@Order(5)
	public void createViewEditDeleteCredentialsTest() {
		driver.get("http://localhost:" + this.port + "/login");
		RegistrationTest registrationTest = new RegistrationTest(driver);
		registrationTest.loginToAccount();

		CredentialTest credentialTest = new CredentialTest(driver);

		// Add three Credentials
		for(int index = 0; index < 3; index++) {
			credentialTest.openCredentialsTab();
			credentialTest.addCredential(index);
		}

		// Is the Admin Password Encrypted
		credentialTest.openCredentialsTab();
		Assertions.assertEquals(true, credentialTest.getCurrentAdminEncryptedCredentails());

		// Check if staff password can be unencrypted during editing
		Assertions.assertEquals(true, credentialTest.visibleStaffCredential());

		// Check if password can be changed and encryption updated
		Assertions.assertEquals(true, credentialTest.editStaffCredential());

		// Delete the repository entry
		credentialTest.openCredentialsTab();
		credentialTest.deleteRepositoryCredentials();

		// Check if repository entry still exists
		credentialTest.openCredentialsTab();
		Assertions.assertEquals(true, credentialTest.validateRepositoryCredentialsExist());
	}

	/*
		Not a requirement but created test out of practice.
		If download file already exists then this will fail.
		Also, if you run the below code you need to change to
		your downloads directory on line 94 in FilesTest.java.
	 */
//	@Test
//	@Order(6)
//	public void createViewDeleteFileTest() {
//		driver.get("http://localhost:" + this.port + "/login");
//		RegistrationTest registrationTest = new RegistrationTest(driver);
//
//		registrationTest.loginToAccount();
//
//		FilesTest filesTest = new FilesTest(driver);
//		filesTest.uploadFile();
//		Assertions.assertEquals(true, filesTest.fileUploadedExists());
//		filesTest.viewFile();
//		Assertions.assertEquals(true, filesTest.fileDownloaded());
//		filesTest.deleteFile();
//		Assertions.assertEquals(false, filesTest.fileUploadedExists());
//
//
//	}
}
