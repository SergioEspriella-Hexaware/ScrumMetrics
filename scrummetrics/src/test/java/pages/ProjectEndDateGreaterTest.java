package pages;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
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
public class ProjectEndDateGreaterTest {

	private WebDriver driver;
	ProjectCreation pc;
	private final String username, password, projectName, description, startDate, endDate;
	static ExtentReports reporter;
	static ExtentHtmlReporter htmlreporter;
	static ExtentTest test;

	@Parameterized.Parameters(name = "using endDate={5}")
	public static Collection<Object[]> data() throws EncryptedDocumentException, IOException {
		List<Object[]> args = new ArrayList<>();

		InputStream inp = new FileInputStream("excel/ProjectCreation.xlsx");
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(3);
		DataFormatter formatter = new DataFormatter();

		int row = 1;
		while (true) {
			try {
				String username = formatter.formatCellValue(sheet.getRow(row).getCell(0));
				String password = formatter.formatCellValue(sheet.getRow(row).getCell(1));
				String projectName = formatter.formatCellValue(sheet.getRow(row).getCell(2));
				String description = formatter.formatCellValue(sheet.getRow(row).getCell(3));
				String startDate = formatter.formatCellValue(sheet.getRow(row).getCell(4));
				String endDate = formatter.formatCellValue(sheet.getRow(row).getCell(5));
				
				if (username == "" && password == "")
					break;

				args.add(new Object[] { username, password, projectName, description, startDate, endDate });
				row++;
			} catch (Exception e) {
				break;
			}
		}

		return args;
	}

	public ProjectEndDateGreaterTest(String a, String b, String c, String d, String e, String f) {
		this.username = a;
		this.password = b;
		this.projectName = c;
		this.description = d;
		this.startDate = e;
		this.endDate = f;
	}

	@Before
	public void setUp() throws Exception {
		if (htmlreporter == null) {
			reporter = new ExtentReports();
			htmlreporter = new ExtentHtmlReporter("reportes/project_creation_tests.html");
			htmlreporter.setAppendExisting(true);
			reporter.attachReporter(htmlreporter);
			test = reporter.createTest("End Date Greater",
					"Test que verifique que la fecha final sea mayor que la inicial");
		}
		test.log(Status.INFO, "Iniciando el test con end_date = " + endDate);
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
	public void test() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy", Locale.US);
		boolean isDateBefore = sdf.parse(startDate).before(sdf.parse(endDate));

		pc.fillLogin(username, password);
		pc.newProject(projectName, description, startDate, endDate);

		if (isDateBefore) {
			if (pc.onProjectCreated().equals("Project created succesfully"))
				test.log(Status.PASS, "El proyecto se creó correctamente");
			else
				test.log(Status.FAIL, "El proyecto no se creó correctamente");
			assertEquals("Project created succesfully", pc.onProjectCreated());
		} else {
			if (pc.onProjectCreated().equals("End date must be lower than start date"))
				test.log(Status.PASS, "Se requirió que la fecha final sea menor a la inicial");
			else
				test.log(Status.FAIL, "No e requirió que la fecha final sea menor a la inicial");
			assertEquals("End date must be lower than start date", pc.onProjectCreated());
		}
	}

	@AfterClass
	public static void afterTests() {
		reporter.flush();
	}

}
