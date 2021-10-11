package pages;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

@RunWith(Parameterized.class)
public class UserDataTest {

	UserData usr = new UserData();
	private final String a, b, c, d, e;

	@Parameterized.Parameters(name = "using a={0}")
	public static Collection<Object[]> data() throws EncryptedDocumentException, IOException {
		List<Object[]> args = new ArrayList<>();

		InputStream inp = new FileInputStream("excel/Agua.xlsx");
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(0);
		DataFormatter formatter = new DataFormatter();

		int row = 1;
		while (true) {
			try {
				String a = formatter.formatCellValue(sheet.getRow(row).getCell(0));
				String b = formatter.formatCellValue(sheet.getRow(row).getCell(1));
				String c = formatter.formatCellValue(sheet.getRow(row).getCell(2));
				String d = formatter.formatCellValue(sheet.getRow(row).getCell(3));
				String e = formatter.formatCellValue(sheet.getRow(row).getCell(4));

				args.add(new Object[] { a, b, c, d, e });
				row++;
			} catch (Exception e) {
				break;
			}
		}

		return args;
	}

	public UserDataTest(String a, String b, String c, String d, String e) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		System.out.println(usr.fullData(a, b, c, d, e));
		assertTrue(true);
	}

}
