package com.harishkannarao.qa.acceptance.sample.webdriver;


import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

public class WebDriverTestWatcher implements TestWatcher {

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        WebDriverFactory.INSTANCE.closeAllWebDrivers();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        WebDriverFactory.INSTANCE.takeScreenShots(context.getRequiredTestClass().getName() + "#" + context.getDisplayName(), true);
        WebDriverFactory.INSTANCE.closeAllWebDrivers();
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        WebDriverFactory.INSTANCE.closeAllWebDrivers();
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        WebDriverFactory.INSTANCE.takeScreenShots(context.getRequiredTestClass().getName() + "#" + context.getDisplayName(), false);
        WebDriverFactory.INSTANCE.closeAllWebDrivers();
    }
}
