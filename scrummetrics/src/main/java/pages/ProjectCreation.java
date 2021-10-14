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
	By roleSelectLocator;
	By memberUsernameLocator = By.xpath("//input[@class='mat-input-element mat-form-field-autofill-control cdk-text-field-autofill-monitored ng-untouched ng-pristine ng-valid']");
	//By addMemberLocator = By.cssSelector("a[_ngcontent-xvp-c20]");
	By addMemberLocator = By.xpath("//span[contains(text(),'Add')]");
	// TODO improve start date xpath
	By startDateButtonLocator = By.xpath(
			"//*[@id=\"fulldialog\"]/form/div[1]/div/div[2]/div/div[1]/mat-form-field/div/div[1]/div[2]/mat-datepicker-toggle/button");

	By startDate;
	// By startDate = By.cssSelector("td[aria-label=\"13 October 2021\"]");
	By endDateCheck = By.cssSelector("label[for=\"endbutt-input\"]");
	// TODO improve end date xpath
	By endDateButton = By.xpath("//*[@id=\"picker2\"]/div/div[1]/div[2]/mat-datepicker-toggle/button");
	By endDate;
	// By endDate =
	// By.xpath("//*[@id=\"mat-datepicker-1\"]/div/mat-month-view/table/tbody/tr[3]/td[5]");
	By createButtonLocator = By.id("createbutt");
	By noNameError = By.id("mat-error-3");
	//By dateRequiredLocator = By.id("mat-error-11");
	By missingFieldLocator = By.xpath("//mat-error[@class='mat-error ng-star-inserted']");
	//By createButtonLocator = By.id("createbutt");
	By startDateInputLocator = By.xpath("//input[@name='inDate1']");
	By memberNameListLocator = By.xpath("//mat-panel-title[@class='mat-expansion-panel-header-title']");
	By memberListLocator = By.xpath("//mat-expansion-panel-header");

	public void fillLogin(String username, String password) {
		type(username, usernameLocator);
		type(password, passLocator);
		submit(loginLocator);
	}
	
	//TODO
	public void nonUserTest(String name, String description, String SDate, String role, String member) {
		click(newProjectLocator);
		type(name, nameLocator);
		type(description, descriptionLocator);
		
		roleSelectLocator = By.xpath("//option[@value='"+ role + "']");
		click(roleSelectLocator);
		type(member, memberUsernameLocator);
		click(addMemberLocator);
//		if (!SDate.isEmpty()) {
//			scrollElement(startDateButtonLocator);
//
//			click(startDateButtonLocator);
//			startDate = By.cssSelector("td[aria-label=\""+ SDate + "\"]");
//			click(startDate);
//		}
		//submit(createButtonLocator);
	}
	
	public void memberUserTest(String name, String description, String SDate, String role, String member) {
		click(newProjectLocator);
		type(name, nameLocator);
		type(description, descriptionLocator);
		
		roleSelectLocator = By.xpath("//option[@value='" + role + "']");
		click(roleSelectLocator);
		type(member, memberUsernameLocator);
		click(addMemberLocator);
		scrollElement(memberListLocator);
		click(memberListLocator);
//		if (!SDate.isEmpty()) {
//			scrollElement(startDateButtonLocator);
//
//			click(startDateButtonLocator);
//			startDate = By.cssSelector("td[aria-label=\""+ SDate + "\"]");
//			click(startDate);
//		}
		//submit(createButtonLocator);
	}

	public void newProjectDateTest(String name, String description, String SDate) {
		click(newProjectLocator);
		type(name, nameLocator);
		type(description, descriptionLocator);
		
		if (!SDate.isEmpty()) {
			scrollElement(startDateButtonLocator);

			click(startDateButtonLocator);
			startDate = By.cssSelector("td[aria-label=\""+ SDate + "\"]");
			click(startDate);
		}
		submit(createButtonLocator);
	}
	
	public void newProjectDateFormatTest(String name, String description, String SDate) {
		click(newProjectLocator);
		type(name, nameLocator);
		type(description, descriptionLocator);
		//type(name, nameLocator);
		//type(description, descriptionLocator);
		
		if (!SDate.isEmpty()) {
			scrollElement(startDateButtonLocator);
		click(startDateButtonLocator);
		startDate = By.cssSelector("td[aria-label=\"" + SDate + "\"]");
		click(startDate);
		}
		submit(createButtonLocator);
	}

	public void newProject(String name, String description, String SDate, String EDate) {
		click(newProjectLocator);
		type(name, nameLocator);
		type(description, descriptionLocator);
		scrollElement(startDateButtonLocator);
		click(startDateButtonLocator);
		startDate = By.cssSelector("td[aria-label=\"" + SDate + "\"]");
		endDate = By.cssSelector("td[aria-label=\"" + EDate + "\"]");
		click(startDate);
		click(endDateCheck);
		click(endDateButton);
		click(endDate);
		submit(createButtonLocator);
	}

	public boolean isNameErrorDisplayed() {
		return isDisplayed(noNameError);
	}

	public String onProjectCreated() {
		try {
			waitAlert();
		} catch (Exception e) {
			return "Project not created";
		}
		return getMessage();
	}
	
	public String memberValidation() {
		if (isDisplayed(memberNameListLocator)) {
			return getText(memberNameListLocator);
		} else {
			return "";
		}
	}
	
	public String noStartDateValidation() {
		scrollElement(missingFieldLocator);
		if (isDisplayed(missingFieldLocator)) {
			return getText(missingFieldLocator);
		} else {
			waitAlert();
			return getMessage();
		}
	}
	
	public String noDescriptionValidation() {
		scrollElement(missingFieldLocator);
		if (isDisplayed(missingFieldLocator)) {
			return getText(missingFieldLocator);
		} else {
			waitAlert();
			return getMessage();
		}
	}
	
	public String descriptionValidation() {		
		waitAlert();
		return getMessage();
	}
	
	public String startDateValidation() {		
		waitAlert();
		return getMessage();
	}
	
	public String startDateFormatValidation() {		
		return getValue(startDateInputLocator);
	}

}
