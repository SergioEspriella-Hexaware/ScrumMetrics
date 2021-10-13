package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProjectCreation extends Base {

	public ProjectCreation(WebDriver driver) {
		super(driver);
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
	// TODO improve start date xpath
	By startDateButtonLocator = By.xpath(
			"//*[@id=\"fulldialog\"]/form/div[1]/div/div[2]/div/div[1]/mat-form-field/div/div[1]/div[2]/mat-datepicker-toggle/button");

	By startDate = By.cssSelector("td[aria-label=\"13 October 2021\"]");
	By endDateCheck = By.cssSelector("label[for=\"endbutt-input\"]");
	// TODO improve end date xpath
	By endDateButton = By.xpath("//*[@id=\"picker2\"]/div/div[1]/div[2]/mat-datepicker-toggle/button");
	By endDate = By.xpath("//*[@id=\"mat-datepicker-1\"]/div/mat-month-view/table/tbody/tr[3]/td[5]");

	public void fillLogin(String username, String password) {
		type(username, usernameLocator);
		type(password, passLocator);
		submit(loginLocator);
	}

	public void newProject() {
		click(newProjectLocator);
		scrollElement(startDateButtonLocator);

		click(startDateButtonLocator);
		click(startDate);
		click(endDateCheck);
		click(endDateButton);
		click(endDate);
	}

}
