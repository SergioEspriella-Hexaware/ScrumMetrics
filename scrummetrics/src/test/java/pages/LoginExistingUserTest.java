package pages;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

@RunWith(Parameterized.class)
public class LoginExistingUserTest {

	private WebDriver driver;
	Login login;
	private final String username, password;
	static ExtentReports reporter;
	static ExtentHtmlReporter htmlreporter;
	static ExtentTest test;

	@Parameterized.Parameters(name = "using user={0} pass={1}")
	public static Collection<Object[]> data() throws EncryptedDocumentException, IOException {
		List<Object[]> args = new ArrayList<>();

		InputStream inp = new FileInputStream("excel/Login.xlsx");
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(0);
		DataFormatter formatter = new DataFormatter();

		int row = 1;
		while (true) {
			try {
				String username = formatter.formatCellValue(sheet.getRow(row).getCell(0));
				String password = formatter.formatCellValue(sheet.getRow(row).getCell(1));

				args.add(new Object[] { username, password });
				row++;
			} catch (Exception e) {
				break;
			}
		}

		return args;
	}

	public LoginExistingUserTest(String a, String b) {
		this.username = a;
		this.password = b;
	}

	@Before
	public void setUp() throws Exception {
		if (htmlreporter == null) {
			reporter = new ExtentReports();
			htmlreporter = new ExtentHtmlReporter("reportes/login_tests.html");
			htmlreporter.setAppendExisting(true);
			reporter.attachReporter(htmlreporter);
			test = reporter.createTest("Existing User", "Test de login con usuario existente");
		}
		test.log(Status.INFO, "iniciando el test con username = " + username + " y password = " + password + "");
		login = new Login(driver);
		driver = login.chromeDriverConnection();
		login.visit("https://scrum-metrics.herokuapp.com/start/login");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() throws InterruptedException {
		login.fillLogin(username, password, test);
		Thread.sleep(1000);
		if (login.getURL().equals("https://scrum-metrics.herokuapp.com/app/project"))
			test.log(Status.PASS, "Se ingresó al sistema con éxito");
		else if (username.equals("") || password.equals(""))
			test.log(Status.INFO, "usuario y contraseña vacios");
		else
			test.log(Status.FAIL, "No se pudo ingresar al sistema");
		assertEquals("https://scrum-metrics.herokuapp.com/app/project", login.getURL());
	}

	@AfterClass
	public static void afterTests() {
		reporter.flush();
	}

}
