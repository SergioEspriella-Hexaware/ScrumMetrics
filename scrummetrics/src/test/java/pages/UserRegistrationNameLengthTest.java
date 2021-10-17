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

@RunWith(Parameterized.class)
public class UserRegistrationNameLengthTest {
	
	private WebDriver driver;
	UserRegistration ur;
	private final String name, email, username, password;

	@Parameterized.Parameters(name = "using a={0}")
	public static Collection<Object[]> data() throws EncryptedDocumentException, IOException {
		List<Object[]> args = new ArrayList<>();

		InputStream inp = new FileInputStream("excel/ValidEmailUsrReg.xlsx");
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(4);
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
	
	public UserRegistrationNameLengthTest(String a, String b, String c, String d) {
		this.name = a;
		this.email = b;
		this.username = c;
		this.password = d;
	}

	@Before
	public void setUp() throws Exception {
		ur = new UserRegistration(driver);
		driver = ur.chromeDriverConnection();
		ur.visit("https://scrum-metrics.herokuapp.com/start/register");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() throws Exception {
		driver.close();
	}

	@Test
	public void test() {
		ur.fillUserRegistration(name, email, username, password);
		if (name.isEmpty()) {
			assertEquals("You need to enter a name.", ur.wrongNameLength());
		}
		else
			assertEquals("name must be less than 256 characters", ur.wrongNameLength());
	}

}
