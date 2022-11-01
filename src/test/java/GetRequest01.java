import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class GetRequest01 {

    @Test
    public void test01(){

        String url = "https://restful-booker.herokuapp.com/booking";

        Response response = given().when().get(url);
        //given().when().get(url) -> end point'e göndermek için request oluşturmuş olduk.
        //Response response -> api tarafından bana dönen response (cevap)

        // Response response = given().auth().basic("username", "password" ).when().get(url)
        // basic auth ile request göndermek için

        //response.prettyPrint();     //response'taki body'i yazdırır

        //response.prettyPeek();         //response taki herşeyi yazdırır.

        //response.peek();

        //response.print();     //string olarak dataye verir
        // [{"bookingid":1215},{"bookingid":844},{"bookingid":87},{"bookingid":747}, ...]

        System.out.println(response.statusCode());
        System.out.println(response.statusLine());
        System.out.println(response.contentType());


        // 1) JUnit Assert leri ile API testi yapabiliriz.
        assertEquals("Status Kod Hatalı",200, response.getStatusCode());
        assertEquals("HTTP/1.1 200 OK", response.statusLine());
        assertEquals("application/json; charset=utf-8", response.contentType());

        // 2) assertThat ile
        response.then().assertThat()// rest assured assortion
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .statusLine("HTTP/1.1 200 OK");
    }
}