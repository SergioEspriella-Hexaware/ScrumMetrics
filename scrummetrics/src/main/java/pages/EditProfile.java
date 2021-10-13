package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EditProfile extends Base {

	public EditProfile(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
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
	
	public void EditName (String name, String email, String username, String password) {
		type(username, usernameLocator);
		type(password, passwordLocator);
		submit(loginLocator);
		click(profilePhotoLocator);
		click(unlockNameLocator);
		type(name, editNameLocator);
		submit(saveLocator);
	}
	
	public void EditEmail (String name, String email, String username, String password) {
		type(username, usernameLocator);
		type(password, passwordLocator);
		submit(loginLocator);
		click(profilePhotoLocator);
		click(unlockEmailLocator);
		type(email, editEmailLocator);
		submit(saveLocator);
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
	
	public String validEmailCriteria() {
		waitAlert();
		return getMessage();
	}
	
	public String invalidEmailCriteria() {
		waitAlert();
		return getMessage();
	}

}
