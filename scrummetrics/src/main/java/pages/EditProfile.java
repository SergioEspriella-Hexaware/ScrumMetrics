package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class EditProfile extends Base {

	public EditProfile(WebDriver driver) {
		super(driver);
	}

	By usernameLocator = By.xpath("//input[@name='inUser']");
	By passwordLocator = By.xpath("//input[@name='inPass']");
	By loginLocator = By.xpath("//button[@class='mat-raised-button']");
	By profilePhotoLocator = By.xpath("//img[@alt='user-photo']");
	By unlockNameLocator = By.xpath("//button[@mattooltip='Click to change your name']");
	By unlockEmailLocator = By.xpath("//button[@mattooltip='Click to change your e-mail']");
	By editNameLocator = By.xpath("//input[@name='inName']");
	By editEmailLocator = By.xpath("//input[@name='inMail']");
	By saveLocator = By.xpath("//button[@type='submit']");
	By emptyNameLocator = By.xpath("//mat-error[@role='alert']");

	public void EditName(String name, String email, String username, String password, ExtentTest currentTest) {
		type(username, usernameLocator);
		type(password, passwordLocator);
		submit(loginLocator);
		if (isDisplayed(profilePhotoLocator))
			currentTest.log(Status.PASS, "Se ingresó al sistema con éxito");
		else
			currentTest.log(Status.FAIL, "No se pudo ingresar al sistema");
		click(profilePhotoLocator);
		if (isDisplayed(unlockNameLocator) || isDisplayed(unlockEmailLocator))
			currentTest.log(Status.PASS, "Se logró acceder a la ventana para editar el perfil");
		else
			currentTest.log(Status.FAIL, "No se pudo acceder a la ventana para editar el perfil");
		click(unlockNameLocator);
		type(name, editNameLocator);
		submit(saveLocator);
	}

	public void EditEmail(String name, String email, String username, String password, ExtentTest currentTest) throws InterruptedException {
		type(username, usernameLocator);
		type(password, passwordLocator);
		submit(loginLocator);
		if (isDisplayed(profilePhotoLocator))
			currentTest.log(Status.PASS, "Se ingresó al sistema con éxito");
		else
			currentTest.log(Status.FAIL, "No se pudo ingresar al sistema");
		click(profilePhotoLocator);
		if (isDisplayed(unlockEmailLocator) || isDisplayed(unlockNameLocator))
			currentTest.log(Status.PASS, "Se logró acceder a la ventana para editar el perfil");
		else
			currentTest.log(Status.FAIL, "No se pudo acceder a la ventana para editar el perfil");
		click(unlockEmailLocator);
		Thread.sleep(300);
		type(email, editEmailLocator);
		Thread.sleep(300);
		submit(saveLocator);
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

	public String validEmailCriteria() {
		try {
			waitAlert();
			return getMessage();
		} catch (TimeoutException e) {
			return ("Alert was not shown");
		}
	}

	public String invalidEmailCriteria() {
		try {
			waitAlert();
			return getMessage();
		} catch (TimeoutException e) {
			return ("Alert was not shown");
		}
	}

}
