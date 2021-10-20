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
public class ProjectNameTest {

	private WebDriver driver;
	ProjectCreation pc;
	private final String username, password, name, description, startDate;
	static ExtentReports reporter;
	static ExtentHtmlReporter htmlreporter;
	static ExtentTest test;

	@Parameterized.Parameters(name = "using name={2} desc={3}")
	public static Collection<Object[]> data() throws EncryptedDocumentException, IOException {
		List<Object[]> args = new ArrayList<>();

		InputStream inp = new FileInputStream("excel/ProjectCreation.xlsx");
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(0);
		DataFormatter formatter = new DataFormatter();

		int rowNo = 1;

		while (true) {
			try {
				String username = formatter.formatCellValue(sheet.getRow(rowNo).getCell(0));
				String password = formatter.formatCellValue(sheet.getRow(rowNo).getCell(1));
				String name = formatter.formatCellValue(sheet.getRow(rowNo).getCell(2));
				String description = formatter.formatCellValue(sheet.getRow(rowNo).getCell(3));
				String startDate = formatter.formatCellValue(sheet.getRow(rowNo).getCell(4));

				if (username == "" && password == "")
					break;

				args.add(new Object[] { username, password, name, description, startDate });
				rowNo++;
			} catch (Exception e) {
				break;
			}
		}

		return args;
	}

	public ProjectNameTest(String a, String b, String c, String d, String e) {
		this.username = a;
		this.password = b;
		this.name = c;
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
			test = reporter.createTest("Name Mandatory", "Test de nombre de proyecto obligatorio");
		}
		test.log(Status.INFO, "Iniciando el test con name = " + name);
		pc = new ProjectCreation(driver);
		pc.setExtentTest(test);
		driver = pc.chromeDriverConnection();
		pc.visit("https://scrum-metrics.herokuapp.com/start/login");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() throws InterruptedException {
		pc.fillLogin(username, password);
		pc.newProjectDateFormatTest(name, description, startDate);
		if (name == "") {
			if (pc.isNameErrorDisplayed())
				test.log(Status.PASS, "Se muestra mensaje que falta el nombre");
			else
				test.log(Status.FAIL, "No se muestra mensaje que falta el nombre");
			assertTrue(pc.isNameErrorDisplayed());
		} else {
			if (pc.onProjectCreated().equals("Project created succesfully"))
				test.log(Status.PASS, "El proyecto se creó correctamente");
			else
				test.log(Status.FAIL, "El proyecto no se creó");
			assertEquals("Project created succesfully", pc.onProjectCreated());
		}
	}

}
