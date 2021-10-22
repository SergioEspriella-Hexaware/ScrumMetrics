package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ProjectCreation extends Base {

	public ProjectCreation(WebDriver driver) {
		super(driver);
	}

	static ExtentTest currentTest;

	public void setExtentTest(ExtentTest test) {
		currentTest = test;
	}

	By newProjectWindowLocator = By.xpath("//h1[contains(text(),'New Project')]");
	By usernameLocator = By.id("mat-input-0");
	By passLocator = By.id("mat-input-1");
	By loginLocator = By.xpath("//button[@class='mat-raised-button']");

	By newProjectLocator = By.cssSelector("a[ng-reflect-message=\"Create a new project!\"]");
	By nameLocator = By.xpath(
			"//body[1]/div[2]/div[2]/div[1]/mat-dialog-container[1]/app-newproject[1]/div[1]/form[1]/div[1]/div[1]/div[2]/mat-form-field[1]/div[1]/div[1]/div[1]/input[1]");
	By descriptionLocator = By.id("mat-input-3");
	By roleSelectLocator;
	By memberUsernameLocator = By.xpath(
			"/html[1]/body[1]/div[2]/div[2]/div[1]/mat-dialog-container[1]/app-newproject[1]/div[1]/form[1]/div[1]/div[1]/div[2]/mat-form-field[4]/div[1]/div[1]/div[1]/div[1]/input[1]");
	By addMemberLocator = By.xpath("//span[contains(text(),'Add')]");

	// TODO improve start date xpath
	By startDateButtonLocator = By.xpath(
			"//*[@id=\"fulldialog\"]/form/div[1]/div/div[2]/div/div[1]/mat-form-field/div/div[1]/div[2]/mat-datepicker-toggle/button");

	By startDate;
	By endDateCheck = By.cssSelector("label[for=\"endbutt-input\"]");
	// TODO improve end date xpath
	By endDateButton = By.xpath("//*[@id=\"picker2\"]/div/div[1]/div[2]/mat-datepicker-toggle/button");

	By endDate;
	By createButtonLocator = By.id("createbutt");
	By noNameError = By.id("mat-error-3");
	By missingFieldLocator = By.xpath("//mat-error[@class='mat-error ng-star-inserted']");
	By startDateInputLocator = By.xpath("//input[@name='inDate1']");
	By endDateInputLocator = By.xpath("//input[@name='inDate2']");

	By memberNameListLocator = By.xpath("//mat-panel-title[@class='mat-expansion-panel-header-title']");
	By memberListLocator = By.xpath("//mat-expansion-panel-header");

	public void fillLogin(String username, String password) {
		type(username, usernameLocator);
		if (getValue(usernameLocator).equals(username))
			currentTest.log(Status.PASS, "Username introducido correctamente");
		else
			currentTest.log(Status.FAIL, "Username no introducido");
		type(password, passLocator);
		if (getValue(passLocator).equals(password))
			currentTest.log(Status.PASS, "Password introducido correctamente");
		else
			currentTest.log(Status.FAIL, "Password no introducido");
		submit(loginLocator);
		currentTest.log(Status.PASS, "Botón de login presionado");
	}

	public void nonUserTest(String name, String description, String SDate, String role, String member) {
		click(newProjectLocator);
		if (isDisplayed(newProjectWindowLocator))
			currentTest.log(Status.PASS, "La ventana de New Project abrió");
		else
			currentTest.log(Status.FAIL, "La ventana de New Project no se abrió");

		type(name, nameLocator);
		if (getValue(nameLocator).equals(name))
			currentTest.log(Status.PASS, "Nombre introducido correctamente");
		else
			currentTest.log(Status.FAIL, "Nombre no introducido");

		type(description, descriptionLocator);
		if (getValue(descriptionLocator).equals(description))
			currentTest.log(Status.PASS, "Descripción introducida correctamente");
		else
			currentTest.log(Status.FAIL, "Descripción no introducida");

		roleSelectLocator = By.xpath("//option[@value='" + role + "']");
		click(roleSelectLocator);
		type(member, memberUsernameLocator);
		if (getValue(memberUsernameLocator).equals(member))
			currentTest.log(Status.PASS, "Nombre del miembro introducido correctamente");
		else
			currentTest.log(Status.FAIL, "Nombre del miembro no introducido");
		click(addMemberLocator);
		currentTest.log(Status.PASS, "Botón de añadir miembro presionado");
	}

	public void memberUserTest(String name, String description, String SDate, String role, String member) {
		click(newProjectLocator);
		if (isDisplayed(newProjectWindowLocator))
			currentTest.log(Status.PASS, "La ventana de New Project abrió");
		else
			currentTest.log(Status.FAIL, "La ventana de New Project no se abrió");

		type(name, nameLocator);
		if (getValue(nameLocator).equals(name))
			currentTest.log(Status.PASS, "Nombre introducido correctamente");
		else
			currentTest.log(Status.FAIL, "Nombre no introducido");

		type(description, descriptionLocator);
		if (getValue(descriptionLocator).equals(description))
			currentTest.log(Status.PASS, "Descripción introducida correctamente");
		else
			currentTest.log(Status.FAIL, "Descripción no introducida");

		roleSelectLocator = By.xpath("//option[@value='" + role + "']");
		click(roleSelectLocator);
		type(member, memberUsernameLocator);
		if (getValue(memberUsernameLocator).equals(member))
			currentTest.log(Status.PASS, "Nombre del miembro introducido correctamente");
		else
			currentTest.log(Status.FAIL, "Nombre del miembro no introducido");
		click(addMemberLocator);
		currentTest.log(Status.PASS, "Botón de añadir miembro presionado");
		scrollElement(memberListLocator);
		click(memberListLocator);
	}

	public void newProjectDateTest(String name, String description, String sDate) {
		click(newProjectLocator);
		if (isDisplayed(newProjectWindowLocator))
			currentTest.log(Status.PASS, "La ventana de New Project abrió");
		else
			currentTest.log(Status.FAIL, "La ventana de New Project no se abrió");
		type(name, nameLocator);
		if (getValue(nameLocator).equals(name))
			currentTest.log(Status.PASS, "Nombre introducido correctamente");
		else
			currentTest.log(Status.FAIL, "Nombre no introducido");
		type(description, descriptionLocator);
		if (getValue(descriptionLocator).equals(description))
			currentTest.log(Status.PASS, "Descripción introducida correctamente");
		else
			currentTest.log(Status.FAIL, "Descripción no introducida");

		if (!sDate.isEmpty()) {
			scrollElement(startDateButtonLocator);

			click(startDateButtonLocator);
			startDate = By.cssSelector("td[aria-label=\"" + sDate + "\"]");
			click(startDate);
			currentTest.log(Status.PASS, "Fecha de inicio seleccionada");
		}
		submit(createButtonLocator);
		currentTest.log(Status.PASS, "Botón de crear proyecto presionado");
	}

	public void newProjectDateFormatTest(String name, String description, String sDate) {
		click(newProjectLocator);
		if (isDisplayed(newProjectWindowLocator))
			currentTest.log(Status.PASS, "La ventana de New Project abrió");
		else
			currentTest.log(Status.FAIL, "La ventana de New Project no se abrió");
		type(name, nameLocator);
		if (getValue(nameLocator).equals(name))
			currentTest.log(Status.PASS, "Nombre introducido correctamente");
		else
			currentTest.log(Status.FAIL, "Nombre no introducido");
		type(description, descriptionLocator);
		if (getValue(descriptionLocator).equals(description))
			currentTest.log(Status.PASS, "Descripción introducida correctamente");
		else
			currentTest.log(Status.FAIL, "Descripción no introducida");

		if (!sDate.isEmpty()) {
			scrollElement(startDateButtonLocator);
			click(startDateButtonLocator);
			startDate = By.cssSelector("td[aria-label=\"" + sDate + "\"]");
			click(startDate);
			currentTest.log(Status.PASS, "Fecha de inicio seleccionada");
		}
	}

	public void newProject(String name, String description, String sDate, String eDate) {
		click(newProjectLocator);
		if (isDisplayed(newProjectWindowLocator))
			currentTest.log(Status.PASS, "La ventana de New Project abrió");
		else
			currentTest.log(Status.FAIL, "La ventana de New Project no se abrió");
		type(name, nameLocator);
		if (getValue(nameLocator).equals(name))
			currentTest.log(Status.PASS, "Nombre introducido correctamente");
		else
			currentTest.log(Status.FAIL, "Nombre no introducido");
		type(description, descriptionLocator);
		if (getValue(descriptionLocator).equals(description))
			currentTest.log(Status.PASS, "Descripción introducida correctamente");
		else
			currentTest.log(Status.FAIL, "Descripción no introducida");
		scrollElement(startDateButtonLocator);
		click(startDateButtonLocator);
		startDate = By.cssSelector("td[aria-label=\"" + sDate + "\"]");
		endDate = By.xpath(
				"//*[@id=\"mat-datepicker-1\"]/div/mat-month-view/table/tbody/tr/td[@aria-label='" + eDate + "']");
		click(startDate);
		currentTest.log(Status.PASS, "Fecha de inicio seleccionada");
		click(endDateCheck);
		currentTest.log(Status.PASS, "Checkbox de fecha de final marcado");
		click(endDateButton);
		click(endDate);
		currentTest.log(Status.PASS, "Fecha de final seleccionada");
		submit(createButtonLocator);
		currentTest.log(Status.PASS, "Botón de crear proyecto presionado");
	}

	public void fillNewProject(String name, String description, String sDate, String eDate) {
		click(newProjectLocator);
		if (isDisplayed(newProjectWindowLocator))
			currentTest.log(Status.PASS, "La ventana de New Project abrió");
		else
			currentTest.log(Status.FAIL, "La ventana de New Project no se abrió");
		type(name, nameLocator);
		if (getValue(nameLocator).equals(name))
			currentTest.log(Status.PASS, "Nombre introducido correctamente");
		else
			currentTest.log(Status.FAIL, "Nombre no introducido");
		type(description, descriptionLocator);
		if (getValue(descriptionLocator).equals(description))
			currentTest.log(Status.PASS, "Descripción introducida correctamente");
		else
			currentTest.log(Status.FAIL, "Descripción no introducida");
		scrollElement(startDateButtonLocator);
		click(startDateButtonLocator);
		startDate = By.cssSelector("td[aria-label=\"" + sDate + "\"]");
		endDate = By.xpath(
				"//*[@id=\"mat-datepicker-1\"]/div/mat-month-view/table/tbody/tr/td[@aria-label='" + eDate + "']");
		click(startDate);
		currentTest.log(Status.PASS, "Fecha de inicio seleccionada");
		click(endDateCheck);
		currentTest.log(Status.PASS, "Checkbox de fecha de final marcado");
		click(endDateButton);
		click(endDate);
		currentTest.log(Status.PASS, "Fecha de final seleccionada");
	}

	public boolean isNameErrorDisplayed() {
		return isDisplayed(noNameError);
	}

	public String onProjectCreated() {
		try {
			waitAlert();
		} catch (Exception e) {
			return "No message shown";
		}
		return getMessage();
	}

	public String memberValidation() {
		try {
			Thread.sleep(1000);
			return getText(memberNameListLocator);
		} catch (Exception e) {
			return "";
		}
	}

	public String noStartDateValidation() {
		scrollElement(missingFieldLocator);
		try {
			return getText(missingFieldLocator);
		} catch (Exception e) {
			return "Line 273";
		}
	}

	public String noDescriptionValidation() {
		scrollElement(missingFieldLocator);
		try {
			return getText(missingFieldLocator);
		} catch (Exception e) {
			return "Line 282";
		}
	}

	public String descriptionValidation() {
		try {
			waitAlert();
			return getMessage();
		} catch (Exception e) {
			return "";
		}
	}

	public String startDateValidation() {
		try {
			waitAlert();
			return getMessage();
		} catch (Exception e) {
			return "";
		}
	}

	public String startDateFormatValidation() {
		return getValue(startDateInputLocator);
	}

	public String endDateFormatValidation() {
		return getValue(endDateInputLocator);
	}

}
