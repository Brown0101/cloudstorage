package com.udacity.jwdnd.course1.cloudstorage;

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
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void getHomePageNotLoggedIn() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(3)
	public void registerLoginAndLogout() {
		driver.get("http://localhost:" + this.port + "/login");
		RegistrationTest registrationTest = new RegistrationTest(driver);
		registrationTest.clickRegisterLink();
		registrationTest.registerAccount();
		registrationTest.getLoginPage();
		registrationTest.loginToAccount();
		Assertions.assertEquals("Home", driver.getTitle());
		registrationTest.logoutAccount();
		Assertions.assertNotEquals("Home", driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(4)
	public void createANote() {
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

}
