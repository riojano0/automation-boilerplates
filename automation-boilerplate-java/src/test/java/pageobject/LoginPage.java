package pageobject;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import repository.RepositoryParser;
import repository.RepositoryParserFactory;
import util.WebElementUtil;

public class LoginPage {

   private final RepositoryParser repositoryParser = RepositoryParserFactory.getRepository("login.page.repository.properties");
   private final WebDriver webDriver;

   public LoginPage(WebDriver webDriver) {
      this.webDriver = webDriver;
      this.webDriver.get("https://www.saucedemo.com/");

      String currentUrl = this.webDriver.getCurrentUrl();
      String loginPageUrl = "https://www.saucedemo.com/";
      if (!StringUtils.equalsAnyIgnoreCase(currentUrl, loginPageUrl)) {
         this.webDriver.get(loginPageUrl);
      }
   }

   public void setUsername(String username) {
      By userNameElement = repositoryParser.getByElement("by.id.username.input");
      ExpectedCondition<WebElement> webElementExpectedCondition = ExpectedConditions.presenceOfElementLocated(userNameElement);
      WebElement webElement = WebElementUtil.getWebElement(webDriver, webElementExpectedCondition);
      webElement.clear();
      webElement.sendKeys(username);
   }


   public void setPassword(String password) {
      By passwordElement = repositoryParser.getByElement("by.id.password.input");
      ExpectedCondition<WebElement> condition = ExpectedConditions.presenceOfElementLocated(passwordElement);
      WebElement webElement = WebElementUtil.getWebElement(webDriver, condition);
      webElement.clear();
      webElement.sendKeys(password);
   }


   public void clickLoginButton() {
      By loginButtonElement = repositoryParser.getByElement("by.id.login.button");
      WebElement buttonLogin =  webDriver.findElement(loginButtonElement);
      buttonLogin.click();
   }

   public String getErrorMessage() {
      By errorBoxElement = repositoryParser.getByElement("by.classname.error.message.box");
      ExpectedCondition<WebElement> condition = ExpectedConditions.presenceOfElementLocated(errorBoxElement);
      WebElement webElement = WebElementUtil.getWebElement(webDriver, condition);
      return webElement.getText();
   }
}
