package step;

import java.time.Instant;

import io.cucumber.java.Scenario;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Browser;

import driver.DriverFactory;

public class ContextStep {

   private static boolean webDriverOn = false;

   private static WebDriver webDriver;

   public static WebDriver getWebDriver() {

      if (webDriverOn) {
         return webDriver;
      }

      return getWebDriver(Browser.CHROME);
   }

   public static WebDriver getWebDriver(Browser browser) {

      if (webDriverOn) {
         return webDriver;
      }

      webDriver = DriverFactory.getDriver(browser);
      webDriverOn = true;
      return webDriver;
   }

   public static void webDriverQuit(Scenario scenario) {
      if (webDriverOn) {
         if (scenario != null && scenario.isFailed()) {
            WebDriver webDriver = ContextStep.getWebDriver();
            TakesScreenshot takesScreenshot = (TakesScreenshot)webDriver;
            byte[] screenshotAs = takesScreenshot.getScreenshotAs(OutputType.BYTES);
            String imageName = scenario.getName() + Instant.now().toString() + ".png";
            scenario.attach(screenshotAs, "image/png", imageName);
         }

         webDriverOn = false;
         webDriver.quit();
      }
   }

}
