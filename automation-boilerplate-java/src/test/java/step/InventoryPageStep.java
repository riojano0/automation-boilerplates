package step;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pageobject.InventoryPage;

public class InventoryPageStep {

   private InventoryPage inventoryPage;

   @Given("User navigate to inventory page")
   public void userNavigateToInventoryPage() {
      WebDriver webDriver = ContextStep.getWebDriver();
      inventoryPage = new InventoryPage(webDriver);
   }

   @When("User open inventory item {string}")
   public void userOpenInventoryItem(String itemName) {
      inventoryPage.openItemFromIMage(itemName);
   }

   @And("User add to cart from inventory item")
   public void userAddToCartFromInventoryItem() {
      inventoryPage.addItemToCardFromInventoryItem();
   }

   @And("User press go back to main inventory from inventory item")
   public void userPressGoBackFromInventoryItem() {
      inventoryPage.clickGoBackToMainInventory();
   }

   @And("user add to cart {string} from main inventory")
   public void userAddToCartFromMainInventory(String itemName) {
      inventoryPage.addItemToCardFromMainInventory(itemName);
   }

   @Then("User see {int} on the cart button")
   public void userSeeItemOnTheCartIcon(Integer numberOnPopup) {
      WebElement cartButtonBadge = inventoryPage.getCartButtonBadge();
      String text = cartButtonBadge.getText();
      int badgeCounts = NumberUtils.toInt(text);
      assertThat(badgeCounts, is(numberOnPopup));
   }

   @When("User change sort to {string}")
   public void userChangeSortTo(String sortType) {
      inventoryPage.changeSortType(sortType);
   }

   @Then("Inventory items on page are sorted by {string}")
   public void inventoryItemsOnPageAreSortedBy(String sortType) {

      if (StringUtils.startsWithIgnoreCase(sortType, "Price")) {
         List<BigDecimal> inventoryItemsPrice = inventoryPage.getInventoryItemsPrice();

         List<BigDecimal> newListToCompare = new ArrayList<>(inventoryItemsPrice);
         Comparator<BigDecimal> naturalOrder = Comparator.naturalOrder();
         if (StringUtils.containsIgnoreCase(sortType, "low to high")) {
            newListToCompare.sort(naturalOrder);
         } else {
            newListToCompare.sort(naturalOrder.reversed());
         }

         assertThat(inventoryItemsPrice, is(newListToCompare));
      }
   }

   @And("User add to cart item {int} from main inventory")
   public void userAddToCartItemNumberFromMainInventory(Integer itemNumber) {
      inventoryPage.addItemNumberToCardFromMainInventory(itemNumber);
   }

   @And("User remove from cart item {int} from main inventory")
   public void userRemoveFromCartItemNumberFromMainInventory(Integer itemNumber) {
      inventoryPage.removeItemNumberToCardFromMainInventory(itemNumber);
   }

}
