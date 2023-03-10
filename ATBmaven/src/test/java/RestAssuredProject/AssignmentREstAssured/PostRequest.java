package RestAssuredProject.AssignmentREstAssured;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Reader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PostRequest{
    public static void main(String[] args) throws JsonProcessingException {

       String BASE_URI = "https://restful-booker.herokuapp.com";


        Booking booking = new Booking();
        booking.setFirstname("Nani Ram");
        booking.setLastname("Bhujel");
        booking.setTotalprice(2343);
        booking.setDepositpaid(true);

        BookingDate bookingDate=new BookingDate();
        bookingDate.setCheckin("2023-02-15");
        bookingDate.setCheckout("2023-02-25");

        booking.setBookingdates(bookingDate);
        booking.setAdditionalneeds("lunch and dinner");

        Gson gson=new Gson();
        String jsonObj=gson.toJson(booking);

        RequestSpecification requestSpecification = RestAssured.given();
        ValidatableResponse validatableResponse;
        requestSpecification.baseUri(BASE_URI)
        .basePath("/booking").headers("content-Type", "application/json")
                //.cookie("token", "fd771e317011e1f")
                .auth().basic("admin", "password123")
                .body(jsonObj);

                 Response response=requestSpecification.post();
                 validatableResponse = response.then();

                validatableResponse.statusCode(200)
                .body("booking.firstname", Matchers.equalTo("Nani Ram"))
                .body("booking.lastname", Matchers.equalTo("Bhujel"))
                .body("booking.totalprice", Matchers.equalTo(2343))
                .body("booking.depositpaid", Matchers.equalTo(true))
                .body("booking.bookingdates.checkin", equalTo("2023-02-15"))
                .body("booking.bookingdates.checkout", equalTo("2023-02-25"))
                .body("booking.additionalneeds", equalTo("lunch and dinner"))
                .log().all().toString();
                validatableResponse.time(Matchers.lessThan(400l));







    }
}
