package com.company.automation.tests.login;

import com.company.automation.tests.BaseTest;
import com.microsoft.playwright.Locator;
import com.company.automation.pages.LoginPage;
import com.company.automation.services.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@DisplayName("Login Feature")
public class LoginTest extends BaseTest {

    @Test
    @DisplayName("User can login successfully")
    void loginSuccess() {
        AuthService authService = new AuthService(page);
        authService.login("testauto@gmail.com", "Peek@123456");
        page.waitForURL("**/account**");
        Locator pageTitle = page.locator("h1:has-text('My account')");
        assertThat(pageTitle).isVisible();
        assertThat(pageTitle).containsText("My account");
    }

    @Test
    @DisplayName("User cannot login with invalid credentials")
    void loginFail() {
        AuthService authService = new AuthService(page);
        authService.login("invalid@gmail.com", "123");
        LoginPage loginPage = new LoginPage(page);
        assertThat(loginPage.isErrorDisplayed()).isVisible();
    }
}
