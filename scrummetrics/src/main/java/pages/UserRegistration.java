package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserRegistration extends Base {

	public UserRegistration(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	By nameLocator = By.id("mat-input-0");
	By mailLocator = By.id("mat-input-1");
	By usernameLocator = By.id("mat-input-2");
	By passLocator = By.id("mat-input-3");
	By confirmLocator = By.id("mat-input-4");
	By checkboxLocator = By.xpath("//div[@class='mat-checkbox-inner-container']");
	By submitLocator = By.xpath("//button[@class='mat-raised-button']");
	By emptyNameLocator = By.id("mat-error-0");

	public void fillUserRegistration(String name, String email, String username, String password) {
		type(name, nameLocator);
		type(email, mailLocator);
		type(username, usernameLocator);
		type(password, passLocator);
		type(password, confirmLocator);
		click(checkboxLocator);
		submit(submitLocator);
	}

	public String wrongNameLength() {
		if (isDisplayed(emptyNameLocator)) {
			return getText(emptyNameLocator);
		} else {
			waitAlert();
			return getMessage();
		}
	}
	
	public String wrongNameCriteria() {
		waitAlert();
		return getMessage();
	}

	public String wrongPassLenght() {
		waitAlert();
		return getMessage();
	}

	public String wrongPassCriteria() {
		waitAlert();
		return getMessage();
	}

	public String userNotRegistered() {
		waitAlert();
		return getMessage();
	}

	public String userRegistered() {
		waitAlert();
		return getMessage();
	}
}
