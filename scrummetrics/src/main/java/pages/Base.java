package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Base {

	private WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor js;

	public Base(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver driverConnection(String browser) {
		if (browser == "edge") {
			edgeDriverConnection();
		}
		if (browser == "ie") {
			ieDriverConnection();
		}
		wait = new WebDriverWait(driver, 50);
		return driver;
	}

	public WebDriver edgeDriverConnection() {
		System.setProperty("webdriver.edge.driver", "./drivers/msedgedriver.exe");
		driver = new EdgeDriver();
		wait = new WebDriverWait(driver, 50);
		js = (JavascriptExecutor) driver;
		return driver;
	}

	public WebDriver chromeDriverConnection() {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 50);
		js = (JavascriptExecutor) driver;
		return driver;
	}

	public WebDriver firefoxDriverConnection() {
		System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 50);
		js = (JavascriptExecutor) driver;
		return driver;
	}

	public WebDriver ieDriverConnection() {
		System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
		driver = new InternetExplorerDriver();
		wait = new WebDriverWait(driver, 50);
		js = (JavascriptExecutor) driver;
		return driver;
	}

	public WebElement findElement(By locator) {
		return driver.findElement(locator);
	}

	public List<WebElement> findElements(By locator) {
		return driver.findElements(locator);
	}

	public String getText(WebElement element) {
		return element.getText();
	}

	public String getText(By locator) {
		return driver.findElement(locator).getText();
	}

	public void type(String inputText, By locator) {
		driver.findElement(locator).clear();
		driver.findElement(locator).sendKeys(inputText);
	}

	public void click(By locator) {
		driver.findElement(locator).click();
	}

	public void submit(By locator) {
		driver.findElement(locator).submit();
	}

	public Boolean isDisplayed(By locator) {
		try {
			return driver.findElement(locator).isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public void visit(String url) {
		driver.get(url);
	}

	public void selectVisibleText(By locator, String visibleText) {
		Select drop = new Select(driver.findElement(locator));
		drop.selectByVisibleText(visibleText);
	}

	public void selectValue(By locator, String value) {
		Select drop = new Select(driver.findElement(locator));
		drop.selectByValue(value);
	}

	public void hover(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
	}

	public void hover(By locator) {
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(locator)).perform();
	}

	public void waitElement(By locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void waitElementClickable(By locator) {
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void waitAlert() {
		WebDriverWait waits = new WebDriverWait(driver, 1);
		waits.until(ExpectedConditions.alertIsPresent());
	}

	public String getMessage() {
		return driver.switchTo().alert().getText();
	}

	public String getURL() {
		return driver.getCurrentUrl();
	}

	public void scroll(int horizontal, int vertical) {
		js.executeScript("window.scrollBy(" + horizontal + "," + vertical + ")");
	}

	public void scrollElement(By locator) {
		js.executeScript("arguments[0].scrollIntoView();", findElement(locator));
	}

	public String getValue(By locator) {
		return driver.findElement(locator).getAttribute("value");
	}

}
