import { Given, When, Then } from '@wdio/cucumber-framework';
import InventoryPage from '../pageobjects/inventory.page';


When(/^User open inventory item "(.+)"$/, async (itemName) => {
    await InventoryPage.clickInventoryItemImageByName(itemName);
});

When(/^User add to cart from inventory item$/, async () => {
    await InventoryPage.clickAddToCartButtonFromInventoryItem();
});

Then(/^User press go back to main inventory from inventory item$/, async () => {
    await InventoryPage.goBackToMainInventoryFromInventoryItem();

    const currentUrl = await browser.getUrl();
    expect(currentUrl).toBe('https://www.saucedemo.com/inventory.html');
});

When(/^User add to cart "(.+)" from main inventory$/, async (itemName) => {
    await InventoryPage.addInventoryItemByName(itemName);
});

Then(/^User see (\d+) on the cart button$/, async (itemCount) => {
    const actualItemCount = await InventoryPage.getCartItemCount();
    
    expect(itemCount).toBe(Number(actualItemCount));
});

When(/^User change sort to "(.+)"$/, async(sortType) => {
    const sorted = await InventoryPage.userChangeSortType(sortType);

    expect(sorted).toBe(true);
});

Then(/^Inventory items on page are sorted by "(.+)"$/, async (sortType) => {

    const sortTypeLowerCase = sortType.toLocaleLowerCase();
    const prices = await InventoryPage.getInventoryItemsPrices();
    const originalPrices = [...prices];
    const names = await InventoryPage.getInventoryItemsNames();
    const originalNames = [...names];

    if (sortTypeLowerCase.includes('low to high')) {
        const pricesSorted = prices.sort((a,b) => a-b);

        expect(originalPrices).toEqual(pricesSorted);
    } else if (sortTypeLowerCase.includes('high to low')) {
        const pricesSorted = prices.sort((a,b) => a-b).reverse();

        expect(originalPrices).toEqual(pricesSorted);
    } else if (sortTypeLowerCase.includes('A to Z')) {
        const namesSorted = names.sort();

        expect(originalNames).toEqual(namesSorted);
    } else if (sortTypeLowerCase.includes('Z to A')) {
        const namesSorted = names.sort().reverse();

        expect(originalNames).toEqual(namesSorted);
    }

});

When(/^User add to cart item (\d+) from main inventory$/, async(itemNumber) => {
    await InventoryPage.addInventoryItemByNumber(itemNumber);
});

When(/^User remove from cart item (\d+) from main inventory$/, async(itemNumber) => {
    await InventoryPage.removeInventoryItemByNumber(itemNumber);
});