package driver;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;

public class DriverFactory {

   public static WebDriver getDriver(Browser browser) {

      if (Browser.CHROME == browser) {
         ChromeOptions options = new ChromeOptions();
         options.addArguments("disable-infobars");
         options.addArguments("--disable-extensions");
         //Configure System properties
         WebDriverManager.chromedriver().setup();
         return new ChromeDriver(options);
      }

      if (Browser.FIREFOX == browser) {
         WebDriverManager.firefoxdriver().setup();
         return new FirefoxDriver();
      }

      throw new RuntimeException("Use a browser not implemented: " + browser);
   }

}
