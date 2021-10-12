package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProjectCreation extends Base {

	public ProjectCreation(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	By usernameLocator = By.id("mat-input-0");
	By passLocator = By.id("mat-input-1");
	By loginLocator = By.xpath("//button[@class='mat-raised-button']");

	By newProjectLocator = By.cssSelector("a[ng-reflect-message=\"Create a new project!\"]");
	By nameLocator = By.id("mat-input-2");
	By descriptionLocator = By.id("mat-input-3");
	By roleSelectLocator = By.id("mat-input-4");
	By memberUsernameLocator = By.id("mat-input-5");
	By addMemberLocator = By.cssSelector("a[_ngcontent-xvp-c20]");
	By startDateButtonLocator = By.xpath(
			"//*[@id=\"fulldialog\"]/form/div[1]/div/div[2]/div/div[1]/mat-form-field/div/div[1]/div[2]/mat-datepicker-toggle/button");

	public void fillLogin(String username, String password) {
		type(username, usernameLocator);
		type(password, passLocator);
		submit(loginLocator);
	}

	public void newProject() {
		click(newProjectLocator);
		scrollElement(startDateButtonLocator);
		
		click(startDateButtonLocator);
	}

}
