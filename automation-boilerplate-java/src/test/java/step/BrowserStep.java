package step;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Browser;

public class BrowserStep {

   @Given("User launch Chrome browser")
   public void userLaunchChromeBrowser() {
      ContextStep.getWebDriver(Browser.CHROME);
   }

   @Given("User launch Firefox browser")
   public void userLaunchFirefoxBrowser() {
      ContextStep.getWebDriver(Browser.FIREFOX);
   }


   @Then("Browser page must be {string}")
   public void browserPageMustBe(String url) {

      WebDriver webDriver = ContextStep.getWebDriver();
      String currentUrl = webDriver.getCurrentUrl();
      assertThat(currentUrl, is(url));
   }

   @After
   public void tearDown(Scenario scenario) {
      ContextStep.webDriverQuit(scenario);
   }

}
