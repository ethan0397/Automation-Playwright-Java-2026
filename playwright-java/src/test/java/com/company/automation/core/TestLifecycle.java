package com.company.automation.core;

import com.company.automation.utils.ScreenshotUtil;
import org.junit.jupiter.api.extension.*;

public class TestLifecycle
        implements BeforeEachCallback, AfterEachCallback, AfterTestExecutionCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        if (!PlaywrightManager.isInitialized()) {
            PlaywrightManager.init();
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isPresent()) {
            ScreenshotUtil.capture(context.getDisplayName());
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {
        if (PlaywrightManager.isInitialized()) {
            PlaywrightManager.close();
        }
    }

}
