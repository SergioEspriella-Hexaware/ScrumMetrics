package pages;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UserRegistrationInvalidEmailTest.class, UserRegistrationNameCriteriaTest.class,
		UserRegistrationNameLengthTest.class, UserRegistrationPasswordCriteriaTest.class,
		UserRegistrationPasswordLengthTest.class, UserRegistrationValidEmailTest.class })
public class UserRegistrationTests {

}
