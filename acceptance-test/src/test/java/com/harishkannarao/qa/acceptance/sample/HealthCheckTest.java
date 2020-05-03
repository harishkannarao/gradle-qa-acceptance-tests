package com.harishkannarao.qa.acceptance.sample;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HealthCheckTest extends AbstractBaseTest {

    @Test
    void verify_health_check() {
        Response response = given()
                .spec(createRequestSpec())
                .basePath("/health-check")
                .accept(ContentType.JSON)
                .when()
                .get();

        JsonPath jsonPath = response.jsonPath();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(jsonPath.getString("status"), equalTo("UP"));
        assertThat(jsonPath.getString("commit"), not(emptyOrNullString()));
    }
}
