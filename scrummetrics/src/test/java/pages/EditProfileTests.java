package pages;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EditInvalidEmailTest.class, EditNameCriteriaTest.class, EditNameLengthTest.class,
		EditValidEmailTest.class })
public class EditProfileTests {

}
