package com.VRJD.Petstore.Utilities;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GenerateOktaToken {

	private static final String accessUri = "https://oauth2/default/v1/token ";
	private static final String clientId = " ";
	private static final String clientSecret = " ";
	private static final String clientGrantType = "client_credentials";
	private static final String clientScope = "Custom_Scope";

	public String generateOKTAToken() throws InterruptedException {
		String token = "";
		RequestSpecification requestSpecification = null;
		requestSpecification = RestAssured.given().auth().preemptive().basic(clientId, clientSecret).baseUri(accessUri)
				.header("accept", "application/json")
				.header("cache-control", "no-cache")
				.header("content-type", "application/x-www-form-urlencoded")
				.formParam("grant-type", clientGrantType)
				.formParam("scope", clientScope);
		System.out.println(requestSpecification.toString());
		Thread.sleep(10000);
		Response response = requestSpecification.post();
		Thread.sleep(10000);
		System.out.println("Response Recieved: " + response.toString());
		JsonPath jsonPath = response.jsonPath();
		token = jsonPath != null ? (jsonPath.getString("token_type")) + " " + jsonPath.getString("access_token") : "";
		System.out.println("TOKEN RECIEVED: " + token);
		return token;
	}

	public void verifyAPI() throws InterruptedException {

		RestAssured.baseURI = "";

		String country = "SG";
		String carrier = "FDX";
		String clearanceProcedure = "IMP";
		List<String> clearanceLocation = new ArrayList<>();
		clearanceLocation.add("SIN");

		String bearerToken = generateOKTAToken();

		Response response = 
				given()
				 .queryParam("country", country)
				 .queryParam("carrier", carrier)
				 .queryParam("clearanceProcedure", clearanceProcedure)
				 .queryParam("clearanceLocation", clearanceLocation)
				.header("Content-Type", "application/json")
				.header("", "")
				.header("", "")
				.header("", "")
				.header("Authorization", bearerToken)

				.when().get("")

				.then().assertThat().statusCode(200).extract().response();

		System.out.println("Status Code: " + response.getStatusCode());
		System.out.println("Response Body: " + response.asString());
		String responseDescription = getValueByJSONPath(response, "data.responseDescription");
		System.out.println("Response Description: " + responseDescription);
	}

	public static String getValueByJSONPath(Response response, String path) {
		JsonPath jsonPath = response.jsonPath();
		String value = jsonPath.getString(path);
		return value;
	}
}
