import { Given, When, Then } from '@wdio/cucumber-framework';

import LoginPage from '../pageobjects/login.page';


When(/^User enter username as "(\w+)" and password as "(.+)"$/, async (username, password) => {
    await LoginPage.insertLoginCredentials(username, password)
});

When(/^User click on login button$/, async () => { 
    await LoginPage.submitLoginButton();
});

Then(/^Show error message "(.+)"$/, async (errorMessage) => {
    const actualErrorMessage = await LoginPage.getErrorMessage();
    expect(errorMessage).toBe(actualErrorMessage);
});

