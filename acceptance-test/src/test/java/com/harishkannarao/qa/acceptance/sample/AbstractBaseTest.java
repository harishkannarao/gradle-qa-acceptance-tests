package com.harishkannarao.qa.acceptance.sample;

import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigFactory;
import com.harishkannarao.qa.acceptance.sample.webdriver.WebDriverFactory;
import com.harishkannarao.qa.acceptance.sample.webdriver.WebDriverTestWatcher;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static io.restassured.RestAssured.given;

@ExtendWith(value = {WebDriverTestWatcher.class})
public abstract class AbstractBaseTest {
    private static final Logger log = LoggerFactory.getLogger(AbstractBaseTest.class);

    protected final String environment = Optional.ofNullable(System.getenv("TEST_ENVIRONMENT"))
            .orElse("local");

    protected final TestProperties testProperties = new TestProperties(String.format("properties/%s.properties", environment));

    @BeforeEach
    void beforeTestStart(TestInfo testInfo) {
        log.info("Test Context :: Starting test: {}", testInfo.getDisplayName());
        printAppVersion();
    }

    @AfterEach
    void afterTestComplete(TestInfo testInfo) {
        log.info("Test Context :: Completing test: {}", testInfo.getDisplayName());
    }

    protected WebDriver newWebDriver() {
        return WebDriverFactory.INSTANCE.newWebDriver();
    }

    protected RequestSpecification createRequestSpec() {
        return createRequestSpec(true);
    }

    protected RequestSpecification createRequestSpec(boolean followRedirect) {
        return new RequestSpecBuilder()
                .setBaseUri(testProperties.applicationBaseUrl())
                .setConfig(createRestAssuredConfig(followRedirect))
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    private RestAssuredConfig createRestAssuredConfig(boolean followRedirect) {
        RestAssuredConfig restAssuredConfig = RestAssuredConfig.config()
                .redirect(createRedirectConfig(followRedirect));
        return CurlLoggingRestAssuredConfigFactory.updateConfig(restAssuredConfig);
    }

    private RedirectConfig createRedirectConfig(boolean followRedirect) {
        return RedirectConfig.redirectConfig().followRedirects(followRedirect);
    }

    private void printAppVersion() {
        given()
                .spec(createRequestSpec())
                .basePath("/health-check")
                .accept(ContentType.JSON)
                .when()
                .get();
    }

}
