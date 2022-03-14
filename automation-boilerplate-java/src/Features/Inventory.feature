@Inventory
Feature: Inventory
  Background:
    Given User launch Chrome browser
    And User navigate to login page
    And User enter username as "standard_user" and password as "secret_sauce"
    And User click on login button
    And User navigate to inventory page


  @HappyPath
  Scenario: User add two items to the cart one from item inventory other from main inventory
    When User open inventory item "Sauce Labs Backpack"
    And User add to cart from inventory item
    Then User see 1 on the cart button
    When User press go back to main inventory from inventory item
    And user add to cart "Sauce Labs Onesie" from main inventory
    Then User see 2 on the cart button

  @HappyPath
  Scenario: User sort by price add to cart two items and then remove one
    When User change sort to "Price (low to high)"
    Then Inventory items on page are sorted by "Price (low to high)"
    And User add to cart item 1 from main inventory
    And User add to cart item 2 from main inventory
    Then User see 2 on the cart button
    When User remove from cart item 1 from main inventory
    Then User see 1 on the cart button