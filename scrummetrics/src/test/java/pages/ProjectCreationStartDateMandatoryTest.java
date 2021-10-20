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
public class ProjectCreationStartDateMandatoryTest {

	private WebDriver driver;
	ProjectCreation pc;
	private final String username, password, projectName, description, startDate;
	static ExtentReports reporter;
	static ExtentHtmlReporter htmlreporter;
	static ExtentTest test;

	@Parameterized.Parameters(name = "using a={0}")
	public static Collection<Object[]> data() throws EncryptedDocumentException, IOException {
		List<Object[]> args = new ArrayList<>();

		InputStream inp = new FileInputStream("excel/ValidEmailUsrReg.xlsx");
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(10);
		DataFormatter formatter = new DataFormatter();

		int row = 1;
		while (true) {
			try {
				String username = formatter.formatCellValue(sheet.getRow(row).getCell(0));
				String password = formatter.formatCellValue(sheet.getRow(row).getCell(1));
				String projectName = formatter.formatCellValue(sheet.getRow(row).getCell(2));
				String description = formatter.formatCellValue(sheet.getRow(row).getCell(3));
				String startDate = formatter.formatCellValue(sheet.getRow(row).getCell(4));
				
				if (username == "" && password == "")
					break;

				args.add(new Object[] { username, password, projectName, description, startDate });
				row++;
			} catch (Exception e) {
				break;
			}
		}

		return args;
	}

	public ProjectCreationStartDateMandatoryTest(String a, String b, String c, String d, String e) {
		this.username = a;
		this.password = b;
		this.projectName = c;
		this.description = d;
		this.startDate = e;
	}

	@Before
	public void setUp() throws Exception {
		if (htmlreporter == null) {
			reporter = new ExtentReports();
			htmlreporter = new ExtentHtmlReporter("reportes/project_creation_tests.html");
			htmlreporter.setAppendExisting(true);
			reporter.attachReporter(htmlreporter);
			test = reporter.createTest("Start Date Mandatory", "Test de fecha inicial mandatoria");
		}
		test.log(Status.INFO, "Iniciando el test con start_date = " + startDate);
		pc = new ProjectCreation(driver);
		pc.setExtentTest(test);
		driver = pc.firefoxDriverConnection();
		pc.visit("https://scrum-metrics.herokuapp.com/start/login");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() {
		pc.fillLogin(username, password);
		pc.newProjectDateTest(projectName, description, startDate);
		if (startDate.isEmpty()) {
			if (pc.noStartDateValidation().equals("You need to enter an initial date."))
				test.log(Status.PASS, "Se indicó que la fecha es necesaria");
			else
				test.log(Status.FAIL, "No se indicó que la fecha es necesaria");
			assertEquals("You need to enter an initial date.", pc.noStartDateValidation());
		} else {
			if (pc.startDateValidation().equals("Project created succesfully"))
				test.log(Status.PASS, "El proyecto se creó correctamente");
			else
				test.log(Status.FAIL, "El proyecto no se creó");
			assertEquals("Project created succesfully", pc.startDateValidation());
		}
	}

	@AfterClass
	public static void afterTests() {
		reporter.flush();
	}

}
