package com.company.automation.pages;

import com.company.automation.config.Environment;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {

    public LoginPage(Page page) {
        super(page);
    }

    public void open() {
        page.navigate(Environment.getBaseUrl() + Environment.getLoginPath());
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    public void enterEmail(String email) {
        page.locator("input[type='email']").fill(email);
    }

    public void enterPassword(String password) {
        page.locator("//input[@id='password']").fill(password);
    }

    public void clickLogin() {
        page.locator("input[value='Login']").click();
    }

    public Locator isErrorDisplayed() {
        return page.getByText(
                "Invalid email or password",
                new Page.GetByTextOptions().setExact(false)
            );
    }

}
