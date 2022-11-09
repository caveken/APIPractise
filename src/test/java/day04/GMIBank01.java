package day04;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojos.Customer;
import utilities.GMIBankBaseURL;
import utilities.ReadText;
import utilities.WriteToText;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GMIBank01 extends GMIBankBaseURL {
    /*/*
http://www.gmibank.com/api/tp-customers end point'ine
request gönderin
 1) Tüm Customer bilgilerini ekrana yazdırın.
 2) Tüm Customer SSN lerini ekrana yazdırın.
 3) Tüm Customer SSN lerini text dosyası olarak kaydedin
 4) Olusturduğunuz text dosyasından  SSNleri okuyarak
    "531-95-8437", "049-43-2360", "123-34-3434" SSNlerinin olduğunu doğrulayın
 */

    @Test
    public void test10() throws JsonProcessingException {
        Customer[] customers;
        spec01.pathParams("bir", "tp-customers");
        Response response = given().spec(spec01).headers("Authorization", "Bearer " + generateToken())
                .when()
                .get("/{bir}");
        //response.prettyPrint();

        ObjectMapper obj=new ObjectMapper();
        customers=obj.readValue(response.asString(),Customer[].class);

        for (int i=0;i<customers.length;i++){
            System.out.println(customers[i]);
        }
        for (int i=0; i<customers.length;i++)
            System.out.println(customers[i].getSsn());

        String  fileName="src/test/java/day04/SSNNumbers.txt";
        WriteToText.saveSSNData(fileName,customers);

        List<String > expSSN=new ArrayList<>();
        expSSN.add("531-95-8437");
        expSSN.add("049-43-2360");
        expSSN.add("123-34-3434");

        List<String > actSSN= ReadText.readCustomerSSNList(fileName);

        Assert.assertTrue("SSNler eslesmedi",actSSN.containsAll(expSSN));

    }
}
