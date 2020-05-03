package com.harishkannarao.qa.acceptance.sample;

import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigFactory;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public abstract class AbstractBaseTest {
    private static final Logger log = LoggerFactory.getLogger(AbstractBaseTest.class);

    protected final String environment = Optional.ofNullable(System.getenv("TEST_ENVIRONMENT"))
            .orElse("local");

    protected final TestProperties testProperties = new TestProperties(String.format("properties/%s.properties", environment));

    @BeforeEach
    void printTestNameBeforeStart(TestInfo testInfo) {
        log.info("Starting test: {}", testInfo.getDisplayName());
    }

    @AfterEach
    void printTestNameAfterComplete(TestInfo testInfo) {
        log.info("Completing test: {}", testInfo.getDisplayName());
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

}
