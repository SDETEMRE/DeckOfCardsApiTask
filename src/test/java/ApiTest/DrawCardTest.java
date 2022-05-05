package ApiTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class DrawCardTest {

    @Test
    public void test1(){

        Response response = given().accept(ContentType.JSON).queryParam("count", 2)
                .when().get("https://deckofcardsapi.com/api/deck/new/draw/");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        assertTrue(response.headers().hasHeaderWithName("Date"));
        assertTrue(response.body().asString().contains("deck_id"));

        String name = response.path("success").toString();
        assertEquals("true",name);
    }
    @Test
    public void test2(){

        //HamcrestMatchersApiTest

        given().accept(ContentType.JSON)
                .and().queryParam("count",1)
                .when().get("https://deckofcardsapi.com/api/deck/new/draw/")
                .then().statusCode(200)
                .and().contentType("application/json")
                .and().assertThat().body("success",equalTo(true),"remaining",equalTo(51));

    }
    @Test
    public void NegativeTests(){
        //negative test scenario

        given().accept(ContentType.JSON)
                .queryParam("count",55)
                .when().get("https://deckofcardsapi.com/api/deck/new/draw/")
                .then().assertThat().statusCode(200)
                .assertThat().body("success",equalTo(false),
                        "error",equalTo("Not enough cards remaining to draw 55 additional"),
                        "remaining",equalTo(0));
    }

}
