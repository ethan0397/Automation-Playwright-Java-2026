package com.company.automation.core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightManager {
    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();

    public static void init() {
        try {
            playwright.set(Playwright.create());
            browser.set(BrowserFactory.create(playwright.get()));
            context.set(browser.get().newContext());
            page.set(context.get().newPage());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static Page getPage() {
        return page.get();
    }

    // ===== SAFE CHECK =====
    public static boolean isInitialized() {
        return playwright.get() != null
                && browser.get() != null
                && context.get() != null
                && page.get() != null;
    }

    public static void close() {
        if (page.get() != null) {
            page.get().close();
            page.remove();
        }
        if (context.get() != null) {
            context.get().close();
            context.remove();
        }
        if (browser.get() != null) {
            browser.get().close();
            browser.remove();
        }
        if (playwright.get() != null) {
            playwright.get().close();
            playwright.remove();
        }
    }
}
