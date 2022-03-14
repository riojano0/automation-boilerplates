package pageobject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import repository.RepositoryParser;
import repository.RepositoryParserFactory;
import util.WebElementUtil;

public class InventoryPage {

   private final RepositoryParser repositoryParser = RepositoryParserFactory.getRepository("inventory.page.repository.properties");
   private final WebDriver webDriver;

   public InventoryPage(WebDriver webDriver) {
      this.webDriver = webDriver;

      String currentUrl = this.webDriver.getCurrentUrl();
      String inventoryUrl = "https://www.saucedemo.com/inventory.html";
      if (!StringUtils.equalsAnyIgnoreCase(currentUrl, inventoryUrl)) {
         this.webDriver.get(inventoryUrl);
      }
   }

   public void openItemFromIMage(String itemName) {
      Optional<WebElement> inventoryItemOptional = getInventoryItemElement(itemName);
      inventoryItemOptional.ifPresentOrElse(item -> {
         By itemImage = repositoryParser.getByElement("by.classname.inventory.item.img");
         WebElement element = item.findElement(itemImage);
         element.click();
      }, () -> {
         throw new RuntimeException("Unable to found Item: " + itemName);
      });
   }

   public void addItemToCardFromInventoryItem() {
      By addToCartButton = repositoryParser.getByElement("by.cssSelector.add.to.cart.inventory.item");
      ExpectedCondition<WebElement> condition = ExpectedConditions.elementToBeClickable(addToCartButton);
      WebElement webElement = WebElementUtil.getWebElement(webDriver, condition);
      webElement.click();
   }

   public void addItemToCardFromMainInventory(String itemName) {
      Optional<WebElement> inventoryItemOptional = getInventoryItemElement(itemName);

      inventoryItemOptional.ifPresentOrElse(item -> {
         By addToCardButton = repositoryParser.getByElement("by.cssSelector.add.to.cart.inventory.item");
         WebElement button = item.findElement(addToCardButton);
         if (button != null) {
            button.click();
         }
      },
      () -> {
         throw new RuntimeException("Unable to found Item: " + itemName);
      });

   }

   public void addItemNumberToCardFromMainInventory(Integer itemNumber) {
      List<WebElement> inventoryItemElements = getInventoryItems();

      By addToCardButton = repositoryParser.getByElement("by.xpath.inventory.item.add.to.cart.button");
      WebElement webElement = inventoryItemElements.get(itemNumber);
      WebElement element = webElement.findElement(addToCardButton);
      if (element != null) {
         element.click();
      }
   }

   public void removeItemNumberToCardFromMainInventory(Integer itemNumber) {
      List<WebElement> inventoryItemElements = getInventoryItems();

      By addToCardButton = repositoryParser.getByElement("by.xpath.inventory.item.remove.from.cart.button");
      WebElement webElement = inventoryItemElements.get(itemNumber);
      WebElement element = webElement.findElement(addToCardButton);
      if (element != null) {
         element.click();
      }
   }

   public void clickGoBackToMainInventory() {
      By backToProductsButton = repositoryParser.getByElement("by.id.back.to.products");
      ExpectedCondition<WebElement> condition = ExpectedConditions.elementToBeClickable(backToProductsButton);
      WebElement webElement = WebElementUtil.getWebElement(webDriver, condition);
      webElement.click();
   }


   public WebElement getCartButton() {
      By cartButton = repositoryParser.getByElement("by.id.shopping.cart.container.button");
      ExpectedCondition<WebElement> webElementExpectedCondition = ExpectedConditions.elementToBeClickable(cartButton);
      return WebElementUtil.getWebElement(webDriver, webElementExpectedCondition);
   }

   public WebElement getCartButtonBadge() {
      WebElement cartButton = getCartButton();
      By badgeElement = repositoryParser.getByElement("by.classname.shopping.cart.badge");
      return cartButton.findElement(badgeElement);
   }

   private Optional<WebElement> getInventoryItemElement(String itemName) {
      List<WebElement> inventoryItems = getInventoryItems();

      By itemNameElement = repositoryParser.getByElement("by.classname.inventory.item.name");
      return inventoryItems
            .stream()
            .filter(element -> {
               WebElement nameElement = element.findElement(itemNameElement);
               return StringUtils.equalsAnyIgnoreCase(nameElement.getText(), itemName);
            })
            .findFirst();
   }

   public List<WebElement> getInventoryItems() {
      By element = repositoryParser.getByElement("by.classname.inventory.item");
      ExpectedCondition<List<WebElement>> condition = ExpectedConditions.presenceOfAllElementsLocatedBy(element);
      return WebElementUtil.getWebElements(webDriver, condition);
   }

   public List<BigDecimal> getInventoryItemsPrice() {
      List<WebElement> inventoryItems = getInventoryItems();
      By itemPriceElement = repositoryParser.getByElement("by.classname.inventory.item.price");

      return inventoryItems.stream()
                    .map(element -> element.findElement(itemPriceElement))
                    .map(WebElement::getText)
                    .map(priceString -> StringUtils.substringAfter(priceString, "$"))
                    .map(NumberUtils::createBigDecimal)
                    .collect(Collectors.toList());
   }

   public void changeSortType(String sortType) {

      By sortDropDown = repositoryParser.getByElement("by.classname.sort.dropdown");
      ExpectedCondition<WebElement> condition = ExpectedConditions.elementToBeClickable(sortDropDown);
      WebElement webElement = WebElementUtil.getWebElement(webDriver, condition);
      Select select = new Select(webElement);

      if (StringUtils.containsIgnoreCase(sortType, "low to high")) {
         select.selectByValue("lohi");
      } else if (StringUtils.containsIgnoreCase(sortType, "high to low")) {
         select.selectByValue("hilo");
      } else if (StringUtils.containsIgnoreCase(sortType, "A to Z")) {
         select.selectByValue("az");
      } else if (StringUtils.containsIgnoreCase(sortType, "Z to A")) {
         select.selectByValue("za");
      } else {
         throw new RuntimeException("Not found sort type");
      }

   }

}
