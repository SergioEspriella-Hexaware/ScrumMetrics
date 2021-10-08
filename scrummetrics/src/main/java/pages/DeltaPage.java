package pages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DeltaPage extends Base {

	By mexicoEng = By.xpath("/html/body/modal-container/div/div/ng-lang-select-confirmation/div/div/div/div[2]/button");
	By book = By.id("headPrimary1");
	By selectTrip = By.id("selectTripType-val");
	By oneWay = By.id("ui-list-selectTripType1");
	By miles = By
			.xpath("//*[@id=\"booking\"]/form/div[1]/div/div[1]/div[2]/ngc-search-options/fieldset/div/div[2]/label");
	By fromBtn = By.id("fromAirportName");
	By search = By.id("search_input");
	By searchList = By.xpath("//*[@id=\"airport-serach-panel\"]/div/div[2]");
	By toBtn = By.id("toAirportName");
	
	By depart = By.id("input_departureDate_1");
	By doneBtn = By.xpath(
			"//*[@id=\"booking\"]/form/div[1]/div/div[1]/div[1]/div[3]/date-selection-view/div/div/div/div/div[4]/div/div[3]/button[2]");
	By passengers = By.id("passengers-val");
	By passengerNo = By.id("ui-list-passengers2");
	By submit = By.id("btnSubmit");
	By departArrive = By.xpath("//*[@id=\"main_nav\"]/div[3]/a/span");

	public DeltaPage(WebDriver driver) {
		super(driver);
	}

	public boolean booking() throws InterruptedException {
		click(mexicoEng);
		click(book);
		click(selectTrip);
		click(oneWay);
		click(miles);

		String[] destinies = { "MTY", "ATL", "CUN", "NYC" };
		Random rnd = new Random();
		String from = destinies[rnd.nextInt(destinies.length)];
		String to = destinies[rnd.nextInt(destinies.length)];
		while (from.equals(to) || to.isEmpty())
			to = destinies[rnd.nextInt(destinies.length)];

		click(fromBtn);
		type(from, search);
		waitElement(searchList);
		type("\n", search);
		click(toBtn);
		type(to, search);
		waitElement(searchList);
		type("\n", search);

		click(depart);
		String newDate = dateIn5();

		By departureDate = By.xpath("//a[@aria-label='" + newDate + "']");
		click(departureDate);
		click(doneBtn);
		click(passengers);
		click(passengerNo);
		click(submit);

		if (getText(departArrive).equals(from + " - " + to))
			return true;
		return false;
	}

	private String dateIn5() {
		SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy, EEEE", Locale.US);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 5);
		return sdf.format(cal.getTime());
	}

}
