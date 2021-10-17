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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

@RunWith(Parameterized.class)
public class ProjectEndDateGreaterTest {

	private WebDriver driver;
	ProjectCreation pc;
	private final String username, password, projectName, description, startDate, endDate;

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
		pc = new ProjectCreation(driver);
		driver = pc.chromeDriverConnection();
		pc.visit("https://scrum-metrics.herokuapp.com/start/login");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() throws Exception {
		driver.close();
	}

	@Test
	public void test() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy", Locale.US);
		boolean isDateBefore = sdf.parse(startDate).before(sdf.parse(endDate));

		pc.fillLogin(username, password);
		pc.newProject(projectName, description, startDate, endDate);

		if (isDateBefore) {
			assertEquals("Project created succesfully", pc.onProjectCreated());
		} else {
			assertEquals("End date must be lower than start date", pc.onProjectCreated());
		}

	}

}
