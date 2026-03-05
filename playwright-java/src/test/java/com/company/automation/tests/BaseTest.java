package com.company.automation.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import com.company.automation.core.PlaywrightManager;
import com.company.automation.core.TestLifecycle;
import com.microsoft.playwright.Page;

@ExtendWith(TestLifecycle.class)
public class BaseTest {

    protected Page page;

    @BeforeEach
    void setUp() {
        page = PlaywrightManager.getPage();
    }
}
