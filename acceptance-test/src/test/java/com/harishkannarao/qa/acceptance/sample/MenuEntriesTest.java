package com.harishkannarao.qa.acceptance.sample;

import com.harishkannarao.qa.acceptance.sample.tag.Regression;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Regression
public class MenuEntriesTest extends AbstractBaseTest {

    @Test
    void verify_menu_entries() {
        Response response = given()
                .spec(createRequestSpec(false))
                .basePath("/menuentries")
                .accept(ContentType.JSON)
                .when()
                .get();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getList("", String.class), not(empty()));
    }
}
