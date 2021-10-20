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
public class ProjectExistingUserTest {

	private WebDriver driver;
	ProjectCreation pc;
	private final String username, password, projectName, description, startDate, role, member;
	static ExtentReports reporter;
	static ExtentHtmlReporter htmlreporter;
	static ExtentTest test;

	@Parameterized.Parameters(name = "using a={0}")
	public static Collection<Object[]> data() throws EncryptedDocumentException, IOException {
		List<Object[]> args = new ArrayList<>();

		InputStream inp = new FileInputStream("excel/ValidEmailUsrReg.xlsx");
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(15);
		DataFormatter formatter = new DataFormatter();

		int row = 1;
		while (true) {
			try {
				String username = formatter.formatCellValue(sheet.getRow(row).getCell(0));
				String password = formatter.formatCellValue(sheet.getRow(row).getCell(1));
				String projectName = formatter.formatCellValue(sheet.getRow(row).getCell(2));
				String description = formatter.formatCellValue(sheet.getRow(row).getCell(3));
				String startDate = formatter.formatCellValue(sheet.getRow(row).getCell(4));
				String role = formatter.formatCellValue(sheet.getRow(row).getCell(5));
				String member = formatter.formatCellValue(sheet.getRow(row).getCell(6));
				
				if (username == "" && password == "")
					break;

				args.add(new Object[] { username, password, projectName, description, startDate, role, member });
				row++;
			} catch (Exception e) {
				break;
			}
		}

		return args;
	}

	public ProjectExistingUserTest(String a, String b, String c, String d, String e, String f, String g) {
		this.username = a;
		this.password = b;
		this.projectName = c;
		this.description = d;
		this.startDate = e;
		this.role = f;
		this.member = g;
	}

	@Before
	public void setUp() throws Exception {
		if (htmlreporter == null) {
			reporter = new ExtentReports();
			htmlreporter = new ExtentHtmlReporter("reportes/project_creation_tests.html");
			htmlreporter.setAppendExisting(true);
			reporter.attachReporter(htmlreporter);
			test = reporter.createTest("Existing User", "Test para agregar usuarios existentes como miembros");
		}
		test.log(Status.INFO, "Iniciando el test con miembro = " + member);
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
	public void test() {
		pc.fillLogin(username, password);
		pc.memberUserTest(projectName, description, startDate, role, member);
		if (pc.memberValidation().equals(member))
			test.log(Status.PASS, "El miembro se agregó correctamente");
		else
			test.log(Status.FAIL, "El miembro no se agregó");
		assertEquals(member, pc.memberValidation());
	}

	@AfterClass
	public static void afterTests() {
		reporter.flush();
	}

}
