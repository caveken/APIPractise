package day02;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.Authentication;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class GetRequest06 extends Authentication {
    @Test
    public void test06() {
        String url="https://www.gmibank.com/api/tp-customers/114351";

        Response response=given().headers("Authorization","Bearer "+generateToken()).when().get(url);
        response.prettyPrint();

        // Matcher Class ile müsteri bilgilerini dogrulayin
        response.then().assertThat().body("firstName",equalTo("Della"),
                "lastName",equalTo("Heaney"),
                "email",equalTo("ricardo.larkin@yahoo.com"),
                "mobilePhoneNumber",equalTo("123-456-7893"),
                "accounts[0].balance",equalTo(11190),
                "accounts[0].accountType",equalTo("CHECKING"),
                "accounts[1].balance",equalTo(69700),
                "accounts[1].accountType",equalTo("CREDIT_CARD"));

        // JsonPath ile
        JsonPath json=response.jsonPath();
        assertEquals("Della",json.getString("firstname"));
        assertEquals("Heaney",json.getString("lastName"));
        assertEquals("ricardo.larkin@yahoo.com",json.getString("email"));
        assertEquals("123-456-7893",json.getString("mobilePhoneNumber"));
        assertEquals("CREDIT_CARD",json.getString("accounts[0].accountType"));
        assertEquals("CHECKING",json.getString("accounts[1].accountType"));
        assertEquals(69700,json.getInt("accounts[0].balance"));
        assertEquals(11190,json.getInt("accounts[1].balance"));
    }
}
