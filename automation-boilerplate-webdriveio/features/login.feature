@Login
Feature: Login

  Background:
    Given User launch browser
    And User navigate to login page

  Scenario: Successful login to inventory page
    When User enter username as "standard_user" and password as "secret_sauce"
    And User click on login button
    Then Browser page must be "https://www.saucedemo.com/inventory.html"


  Scenario Outline: Fail to Login with invalid credentials
    When User enter username as "<username>" and password as "<password>"
    And User click on login button
    Then Show error message "Epic sadface: Username and password do not match any user in this service"
  Examples:
    | username      | password |
    | a             | b        |
    | standard_user | invalid  |

