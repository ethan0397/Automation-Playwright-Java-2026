package com.company.automation.core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

import java.util.List;

import com.company.automation.config.Environment;

public class BrowserFactory {
    public static Browser create(Playwright playwright) {

        String browserName = Environment.getBrowser().toLowerCase();

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(Environment.isHeadless());
                
        // Maximize cho Chromium
        if ("chromium".equals(browserName)) {
            options.setArgs(List.of("--start-maximized"));
        }

        switch (browserName) {
            case "firefox":
                return playwright.firefox().launch(options);

            case "webkit":
                return playwright.webkit().launch(options);

            case "chromium":
            default:
                return playwright.chromium().launch(options);
        }
    }
}
