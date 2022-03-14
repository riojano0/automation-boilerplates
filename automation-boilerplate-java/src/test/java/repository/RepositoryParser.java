package repository;

import java.util.Optional;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

public class RepositoryParser {

   // by.test.example.input -> group 1: {by.test}
   private static final Pattern PATTERN_BY_ELEMENT = Pattern.compile("^(by\\.\\w+)(\\.)");

   private final String repositoryFileName;
   private final Properties propertyFile;

   RepositoryParser(String repositoryFileName, Properties propertyFile) {
      this.repositoryFileName = repositoryFileName;
      this.propertyFile = propertyFile;
   }

   public String getRepositoryFileName() {
      return repositoryFileName;
   }

   public String getProperty(String property) {
      return (String) propertyFile.get(property);
   }

   public By getByElement(String property) {

      String propertyValue = getProperty(property);

      if (propertyValue != null) {
         Matcher matcher = PATTERN_BY_ELEMENT.matcher(property);
         if (matcher.find()) {
            String identifier = matcher.group(1);
            Optional<By> byElementOptional = getByElementFromIdentifier(identifier, propertyValue);
            return byElementOptional.orElseThrow(() -> new RuntimeException("Not found By element for: " + identifier));
         }
      }

      return null;
   }

   private static Optional<By> getByElementFromIdentifier(String identifier, String propertyValue) {

      if (StringUtils.endsWithIgnoreCase(identifier, ".id")) {
         return Optional.of(By.id(propertyValue));
      }

      if (StringUtils.endsWithIgnoreCase(identifier, ".classname")) {
         return Optional.of(By.className(propertyValue));
      }

      if (StringUtils.endsWithIgnoreCase(identifier, ".cssSelector")) {
         return Optional.of(By.cssSelector(propertyValue));
      }

      if (StringUtils.endsWithIgnoreCase(identifier, ".name")) {
         return Optional.of(By.name(propertyValue));
      }

      if (StringUtils.endsWithIgnoreCase(identifier, ".partialLinkText")) {
         return Optional.of(By.partialLinkText(propertyValue));
      }

      if (StringUtils.endsWithIgnoreCase(identifier, ".linkText")) {
         return Optional.of(By.linkText(propertyValue));
      }

      if (StringUtils.endsWithIgnoreCase(identifier, ".tagName")) {
         return Optional.of(By.tagName(propertyValue));
      }

      if (StringUtils.endsWithIgnoreCase(identifier, ".xPath")) {
         return Optional.of(By.xpath(propertyValue));
      }

      return Optional.empty();
   }


}
