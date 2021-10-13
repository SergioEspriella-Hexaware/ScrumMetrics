package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login extends Base{

	public Login(WebDriver driver) {
		super(driver);
	}
	
	By usernameLocator = By.id("mat-input-0");
	By passLocator = By.id("mat-input-1");
	By loginLocator = By.xpath("//button[@class='mat-raised-button']");
	
	public void fillLogin(String username, String password) {
		type(username, usernameLocator);
		type(password, passLocator);
		submit(loginLocator);
	}
	
	public String wrongUser() {
		waitAlert();
		return getMessage();
	}
	
}
