package com.company.automation.services;

import com.company.automation.pages.LoginPage;
import com.microsoft.playwright.Page;

public class AuthService {

    private final LoginPage loginPage;
    public AuthService(Page page) {
        this.loginPage = new LoginPage(page);
    }

    public void login(String email, String password) {
        loginPage.open();
        loginPage.login(email, password);
    }

}
