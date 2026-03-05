package com.company.automation.utils;

import com.company.automation.core.PlaywrightManager;
import com.microsoft.playwright.Page;

import java.nio.file.Paths;

public class ScreenshotUtil {

    public static void capture(String testName) {
        Page page = PlaywrightManager.getPage();

        if (page == null) {
            System.err.println("⚠️ Cannot capture screenshot: Page is null");
            return;
        }

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("screenshots/" + testName + ".png")));
    }
}
