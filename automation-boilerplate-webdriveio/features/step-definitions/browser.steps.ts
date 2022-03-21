import { Given, When, Then } from "@wdio/cucumber-framework";
import LoginPage from '../pageobjects/login.page';
import InventoryPage from '../pageobjects/inventory.page';

const pages = {
    login: LoginPage,
    inventory: InventoryPage
}

Given(/User launch browser/, async () => {
    //Check how to select another Drive
    await browser.url('/');
});

Given(/^User navigate to (\w+) page$/, async (page) => {
    await pages[page].open()
});

Then(/^Browser page must be "(.+)"$/, async (page) => {
    const currentUrl = await browser.getUrl();
    expect(page).toBe(currentUrl);
});