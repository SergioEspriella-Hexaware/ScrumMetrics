package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class UserRegistration extends Base {

	public UserRegistration(WebDriver driver) {
		super(driver);
	}

	By nameLocator = By.id("mat-input-0");
	By mailLocator = By.id("mat-input-1");
	By usernameLocator = By.id("mat-input-2");
	By passLocator = By.id("mat-input-3");
	By confirmLocator = By.id("mat-input-4");
	By checkboxLocator = By.xpath("//div[@class='mat-checkbox-inner-container']");
	By submitLocator = By.xpath("//button[@class='mat-raised-button']");
	By emptyNameLocator = By.id("mat-error-0");

	public void fillUserRegistration(String name, String email, String username, String password, ExtentTest currentTest) {
		type(name, nameLocator);
		type(email, mailLocator);
		type(username, usernameLocator);
		type(password, passLocator);
		type(password, confirmLocator);
		if (getValue(nameLocator).equals(name) && getValue(mailLocator).equals(email) && getValue(usernameLocator).equals(username))
			currentTest.log(Status.PASS, "Datos introducidos correctamente");
		else
			currentTest.log(Status.FAIL, "Error al introducir los datos de registro");
		click(checkboxLocator);
		submit(submitLocator);
	}

	public String wrongNameLength() {
		if (isDisplayed(emptyNameLocator)) {
			return getText(emptyNameLocator);
		} else {
			try {
				waitAlert();
				return getMessage();
			} catch (TimeoutException e) {
				return ("Alert was not shown");
			}
		}
	}
	
	public String wrongNameCriteria() {
		try {
			waitAlert();
			return getMessage();
		} catch (TimeoutException e) {
			return ("Alert was not shown");
		}
	}

	public String wrongPassLenght() {
		try {
			waitAlert();
			return getMessage();
		} catch (TimeoutException e) {
			return ("Alert was not shown");
		}
	}

	public String wrongPassCriteria() {
		try {
			waitAlert();
			return getMessage();
		} catch (TimeoutException e) {
			return ("Alert was not shown");
		}
	}

	public String userNotRegistered() {
		try {
			waitAlert();
			return getMessage();
		} catch (TimeoutException e) {
			return ("Alert was not shown");
		}
	}

	public String userRegistered() {
		try {
			waitAlert();
			return getMessage();
		} catch (TimeoutException e) {
			return ("Alert was not shown");
		}
	}
}
