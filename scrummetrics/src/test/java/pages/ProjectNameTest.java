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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
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
public class ProjectNameTest {

	private WebDriver driver;
	ProjectCreation pc;
	private final String username, password;

	@Parameterized.Parameters(name = "using user={0} pass={1}")
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

				if (username == "" && password == "")
					break;

				args.add(new Object[] { username, password });
				rowNo++;
			} catch (Exception e) {
				break;
			}
		}

		return args;
	}

	public ProjectNameTest(String a, String b) {
		this.username = a;
		this.password = b;
	}

	@Before
	public void setUp() throws Exception {
		pc = new ProjectCreation(driver);
		driver = pc.edgeDriverConnection();
		pc.visit("https://scrum-metrics.herokuapp.com/start/login");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() throws Exception {
//		driver.close();
	}

	@Test
	public void test() throws InterruptedException {
		pc.fillLogin(username, password);
		pc.newProject();
		Thread.sleep(10000);
	}

}
