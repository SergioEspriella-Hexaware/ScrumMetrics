package pages;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ProjectCreationStartDateFormatTest.class, ProjectCreationStartDateMandatoryTest.class,
		ProjectDescriptionLengthTest.class, ProjectDescriptionMandatoryTest.class, ProjectEndDateFormatTest.class,
		ProjectEndDateGreaterTest.class, ProjectExistingUserTest.class, ProjectNameLengthTest.class,
		ProjectNameTest.class, ProjectNonExistingUserTest.class })
public class ProjectCreationTests {

}
