package util;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;

public class WebElementUtil {

   public static FluentWait<WebDriver> getFluentDefaultConfig(WebDriver webDriver) {
      return new FluentWait<>(webDriver)
            .withTimeout(Duration.ofSeconds(30))
            .pollingEvery(Duration.ofSeconds(2))
            .ignoring(NoSuchElementException.class);
   }

   public static WebElement getWebElement(WebDriver webDriver, ExpectedCondition<WebElement> expectedCondition) {
      return getFluentDefaultConfig(webDriver)
            .until(expectedCondition);
   }

   public static List<WebElement> getWebElements(WebDriver webDriver, ExpectedCondition<List<WebElement>> expectedCondition) {
      return getFluentDefaultConfig(webDriver)
            .until(expectedCondition);
   }

}
