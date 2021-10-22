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
public class UserRegistrationPasswordLengthTest {

	private WebDriver driver;
	UserRegistration ur;
	private final String name, email, username, password;
	static ExtentReports reporter;
	static ExtentHtmlReporter htmlreporter;
	static ExtentTest test;

	@Parameterized.Parameters(name = "using a={0}")
	public static Collection<Object[]> data() throws EncryptedDocumentException, IOException {
		List<Object[]> args = new ArrayList<>();

		InputStream inp = new FileInputStream("excel/ValidEmailUsrReg.xlsx");
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(2);
		DataFormatter formatter = new DataFormatter();

		int row = 1;
		while (true) {
			try {
				String name = formatter.formatCellValue(sheet.getRow(row).getCell(0));
				String email = formatter.formatCellValue(sheet.getRow(row).getCell(1));
				String username = formatter.formatCellValue(sheet.getRow(row).getCell(2));
				String password = formatter.formatCellValue(sheet.getRow(row).getCell(3));

				args.add(new Object[] { name, email, username, password });
				row++;
			} catch (Exception e) {
				break;
			}
		}

		return args;
	}
	
	public UserRegistrationPasswordLengthTest(String a, String b, String c, String d) {
		this.name = a;
		this.email = b;
		this.username = c;
		this.password = d;
	}
	
	@Before
	public void setUp() throws Exception {
		if (htmlreporter == null) {
			reporter = new ExtentReports();
			htmlreporter = new ExtentHtmlReporter("reportes/user_registration_tests.html");
			htmlreporter.setAppendExisting(true);
			reporter.attachReporter(htmlreporter);
			test = reporter.createTest("Password length", "Test para verificar longitud del password");
		}
		test.log(Status.INFO, "username = " + username + ", password = " + password + ", nombre = " + name + ", email = " + email);
		ur = new UserRegistration(driver);
		driver = ur.chromeDriverConnection();
		ur.visit("https://scrum-metrics.herokuapp.com/start/register");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() throws Exception {
		driver.close();
	}
	
	@AfterClass
	public static void afterTests() {
		reporter.flush();
	}

	@Test
	public void test() {
		ur.fillUserRegistration(name, email, username, password, test);
		String alert = ur.wrongPassLenght();
		if (password.isEmpty() && alert.equals("You need to enter a password.")) {
			test.log(Status.PASS, "Alerta de contraseña vacia mostrada");
			assertTrue(true);
		}
		else if (alert.equals("password must be 8 - 12 characters")) {
			test.log(Status.PASS, "Alerta de contraseña con longitud incorrecta mostrada");
			assertTrue(true);
		}
		else if(alert.equals("Alert was not shown")) {
			test.log(Status.FAIL, "Error en la edicion de la contraseña, ninguna alerta mostrada");
			assertTrue(false);
		}
		else {
			test.log(Status.FAIL, "Error: Contraseña no valida ha sido aceptada");
			assertTrue(false);
		}
		//assertEquals("password must be 8 - 12 characters", ur.wrongPassLenght());
	}

}
