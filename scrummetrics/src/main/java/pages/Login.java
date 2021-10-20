package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Login extends Base{

	public Login(WebDriver driver) {
		super(driver);
	}
	
	By usernameLocator = By.id("mat-input-0");
	By passLocator = By.id("mat-input-1");
	By loginLocator = By.xpath("//button[@class='mat-raised-button']");
	
	public void fillLogin(String username, String password, ExtentTest currentTest) {
		type(username, usernameLocator);
		if(getValue(usernameLocator).equals(username))
			currentTest.log(Status.PASS, "Username introducido correctamente");
		else
			currentTest.log(Status.FAIL, "Username no introducido");
		type(password, passLocator);
		if(getValue(passLocator).equals(password))
			currentTest.log(Status.PASS, "Password introducido correctamente");
		else
			currentTest.log(Status.FAIL, "Password no introducido");
		submit(loginLocator);
	}
	
	public String wrongUser() {
		try {
		waitAlert();
		return getMessage();
		}catch(TimeoutException e) {
		return("Alert was not shown");	
		}
	}
	
}
