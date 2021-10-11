package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserRegistration extends Base{

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
	
	public void validEmail() {
		type("nombre completo", nameLocator);
		type("validmail7@outlook.com", mailLocator);
		type("newuser007", usernameLocator);
		type("P4ssword.", passLocator);
		type("P4ssword.", confirmLocator);
		click(checkboxLocator);
		submit(submitLocator);
	}
	
	public void invalidEmail() {
		type("nombre completo", nameLocator);
		type("validmail7", mailLocator);
		type("newuser007", usernameLocator);
		type("P4ssword.", passLocator);
		type("P4ssword.", confirmLocator);
		click(checkboxLocator);
		submit(submitLocator);
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
