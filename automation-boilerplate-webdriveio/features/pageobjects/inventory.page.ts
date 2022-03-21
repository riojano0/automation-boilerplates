import { ChainablePromiseElement } from 'webdriverio';
import Page from "./page";


class InventoryPage extends Page {

  public open () {
    return super.open('inventory.html');
  }
    
  public get inventoryItemElements() {
    return $$(".inventory_item");
  }

  public get shopingCartBadgeButton() {
    return $(".shopping_cart_badge");
  }

  public get sortTypeDropdown() {
    return $(".product_sort_container");
  }

  public get inventoryItemsPrices() {
    return $$(".inventory_item_price");
  }

  public get inventoryItemsNames() {
    return $$(".inventory_item_name");
  }

  public inventoryItemByName(itemName: String) {
    return $(`//div[@class='inventory_item' and descendant-or-self::div[@class='inventory_item_name' and text()='${itemName}' ]]`);
  }

  public async clickInventoryItemImageByName(itemName: String) {
    const item = await this.inventoryItemByName(itemName);
    await item.$('.inventory_item_img').click();
  }

  public async addInventoryItemByName(itemName: String) {
    const item = await this.inventoryItemByName(itemName);
    await item.$('button=Add to cart').click();
  }

  public async getCartItemCount() {
    return await this.shopingCartBadgeButton.getText();
  }

  public async userChangeSortType(sortType: String) {
    const sortTypeDropdown = await this.sortTypeDropdown;

    const types = {
      "low to high": 'lohi',
      "high to low": 'hilo',
      "a to z": 'az',
      "z to a": 'za',
    }

    const sortTypeLowerCase = sortType.toLocaleLowerCase()

    let wasSorted = false;
    Object.keys(types)
    .forEach(key => {
      if (sortTypeLowerCase.includes(key)) {
        console.info(`Selecting sort type: ${key}`);
        sortTypeDropdown.selectByAttribute('value', types[key]);
        wasSorted = true;
      }
    })

    return wasSorted;
  }

  public async getInventoryItemsPrices() {
    await this.sortTypeDropdown.waitForDisplayed();
    const itemPrices = await this.inventoryItemsPrices;
    const prices = [];

    for await(const price of itemPrices) {
      const text = await price.getText();
      const priceString = text.substring(text.indexOf('$') + 1)
      prices.push(Number(priceString));
    }

    await console.info(`Prices: ${prices}`);
    return prices;
  }

  public async getInventoryItemsNames() {

    await this.sortTypeDropdown.waitForDisplayed();
    const itemNames = await this.inventoryItemsNames;
    const names = [];

    for await(const name of itemNames) {
      const text = await name.getText();
      names.push(text);
    }

    await console.info(`Names: ${names}`);
    return names;
  }

  public async addInventoryItemByNumber(itemNumber) {
    await this.sortTypeDropdown.waitForDisplayed();

    const item = await this.inventoryItemElements[itemNumber];
    await item.$('button=Add to cart').click();
  }

  public async removeInventoryItemByNumber(itemNumber) {
    await this.sortTypeDropdown.waitForDisplayed();

    const item = await this.inventoryItemElements[itemNumber];
    await item.$('button=Remove').click();
  }

  public async clickAddToCartButtonFromInventoryItem() {
    await this.InventoryItemPageInstance.addToCartButton.click();
  }

  public async goBackToMainInventoryFromInventoryItem() {
    await this.InventoryItemPageInstance.backToInventoryButton.click();
  }

  InventoryItemPageInstance = new class InventoryItemPage {
    public get addToCartButton() {
      return $('button=Add to cart');
    }
    public get removeFromCartButton() {
      return $('button=Remove');
    }
    public get backToInventoryButton() {
      return $('#back-to-products');
    }
  }();

}

export default new InventoryPage();