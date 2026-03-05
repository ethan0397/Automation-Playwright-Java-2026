package com.company.automation.pages;
import com.company.automation.config.Environment;
import com.microsoft.playwright.Page;

public abstract class BasePage {

    protected final Page page;

    protected String baseUrl() {
        return Environment.getBaseUrl();
    }

    protected BasePage(Page page) {
        if (page == null) {
            throw new IllegalStateException(
                    "Page is null. Did you forget @ExtendWith(TestLifecycle.class)?");
        }
        this.page = page;
    }

}
