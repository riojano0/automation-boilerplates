package step;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.WebDriver;

import pageobject.LoginPage;

public class LoginPageStep {

   private LoginPage loginPage;

   @Given("User navigate to login page")
   public void userLaunchChromeBrowser() {
      WebDriver webDriver = ContextStep.getWebDriver();
      loginPage = new LoginPage(webDriver);
   }

   @When("User enter username as {string} and password as {string}")
   public void userEnterUsernameAndPassword(String username, String password) {
      loginPage.setUsername(username);
      loginPage.setPassword(password);
   }

   @When("User click on login button")
   public void userClickLoginButton() {
      loginPage.clickLoginButton();
   }

   @Then("Show error message {string}")
   public void showMessageError(String errorMessage) {
      String errorMessageFromElement = loginPage.getErrorMessage();

      assertThat(errorMessageFromElement, is(errorMessage));
   }

}
