package cucumberTestRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/java/features",
		glue ="stepDefinitions",
		tags="@RegressionTest",
		plugin= {"pretty","html:target/cucumber"})

public class TestRunner {

}
