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
public class EditInvalidEmailTest {
	
	private WebDriver driver;
	EditProfile ep;
	private final String name, email, username, password;
	static ExtentReports reporter;
	static ExtentHtmlReporter htmlreporter;
	static ExtentTest test;
	
	@Parameterized.Parameters(name = "using a={0}")
	public static Collection<Object[]> data() throws EncryptedDocumentException, IOException {
		List<Object[]> args = new ArrayList<>();

		InputStream inp = new FileInputStream("excel/ValidEmailUsrReg.xlsx");
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(9);
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
	
	public EditInvalidEmailTest(String a, String b, String c, String d) {
		this.name = a;
		this.email = b;
		this.username = c;
		this.password = d;
	}

	@Before
	public void setUp() throws Exception {
		if (htmlreporter == null) {
			reporter = new ExtentReports();
			htmlreporter = new ExtentHtmlReporter("reportes/edit_profile_tests.html");
			htmlreporter.setAppendExisting(true);
			reporter.attachReporter(htmlreporter);
			test = reporter.createTest("Invalid Email", "Test de editar perfil con email no valido");
		}
		test.log(Status.INFO, "Username = " + username + ", Password = " + password + ", Nuevo email = " + email);
		ep = new EditProfile(driver);
		driver = ep.chromeDriverConnection();
		ep.visit("https://scrum-metrics.herokuapp.com/start/login");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() throws Exception {
		driver.close();
	}

	@Test
	public void test() throws InterruptedException {
		ep.EditEmail(name, email, username, password, test);
		String alert = ep.invalidEmailCriteria();
		if(alert.equals("Alert was not shown"))
			test.log(Status.FAIL, "Error en la edicion del Email, ninguna alerta mostrada");
		else if (alert.equals("email not valid"))
			test.log(Status.PASS, "Alerta de Email no valido mostrada");
		else
			test.log(Status.FAIL, "Error: Email no valido ha sido aceptado");
		assertEquals("email not valid", ep.invalidEmailCriteria());
	}
	
	@AfterClass
	public static void afterTests() {
		reporter.flush();
	}

}
