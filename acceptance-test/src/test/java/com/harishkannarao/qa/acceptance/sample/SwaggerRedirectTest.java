package com.harishkannarao.qa.acceptance.sample;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;

public class SwaggerRedirectTest extends AbstractBaseTest {

    @Test
    void verify_swagger_redirect_status() {
        Response response = given()
                .spec(createRequestSpec(false))
                .basePath("/swagger-ui.html")
                .when()
                .get();

        assertThat(response.statusCode(), equalTo(302));
        assertThat(response.getHeader("Location"), endsWith("/swagger-ui/index.html?configUrl=/api-docs/swagger-config"));
    }

    @Test
    void verify_swagger_redirection() {
        Response response = given()
                .spec(createRequestSpec())
                .basePath("/swagger-ui.html")
                .when()
                .get();

        assertThat(response.statusCode(), equalTo(200));
    }
}
