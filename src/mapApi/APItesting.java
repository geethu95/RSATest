package mapApi;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.payload;
public class APItesting {

	public static void main(String[] args) {

		RestAssured.baseURI="https://rahulshettyacademy.com/";

		//add new place
		String response=given().queryParam("key","qaclick123").header("Content-Type","application/json").
				body(payload.Addplace()).
				when().post("maps/api/place/add/json").
				then().log().all().assertThat().statusCode(200).body("scope", equalTo ("APP")).header("Server","Apache/2.4.18 (Ubuntu)"). 
				extract().response().asString();

		System.out.println(response);
		JsonPath js= new JsonPath(response);  //passing json
		String placeid=js.getString("place_id");
		System.out.println("placeid "+placeid);

		//update address for the place added
		String newAddress="Test address update";
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json").
		body("{\r\n"
				+ "\"place_id\":\""+placeid+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "").
		when().put("/maps/api/place/update/json").
		then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));

		//get place
		String getPlaceResponse= given().log().all().queryParam("key","qaclick123").queryParam("place_id",placeid).
				when().get("/maps/api/place/get/json").
				then().assertThat().log().all().statusCode(200).extract().response().asString();

		JsonPath js1= new JsonPath(getPlaceResponse);
		String actualaddress =js1.getString("address");
		System.out.println("actualaddress");
		Assert.assertEquals(actualaddress,newAddress);

	}

}
