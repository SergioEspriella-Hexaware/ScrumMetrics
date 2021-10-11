package pages;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class UserRegistrationInvalidEmailTest {
	
	private WebDriver driver;
	UserRegistration ur;

	@Before
	public void setUp() throws Exception {
		ur = new UserRegistration(driver);
		driver = ur.firefoxDriverConnection();
		ur.visit("https://scrum-metrics.herokuapp.com/start/register");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		ur.invalidEmail();
		assertEquals("User created", ur.userNotRegistered());
	}

}
