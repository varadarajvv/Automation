package com.VRJD.Petstore.Tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.VRJD.Petstore.EndPoints.UserEndPoints2;
import com.VRJD.Petstore.PayLoad.User;
import com.github.javafaker.Faker;

import io.restassured.response.Response;

public class UserTests2 {

	Faker faker;
	User userPayload;
	public Logger logger;

	@BeforeClass
	public void setup() {
		faker = new Faker();
		userPayload = new User();
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		logger = LogManager.getLogger(this.getClass());
		logger.debug("debugging.....");
	}

	@Test(priority = 1)
	public void TC_001_Create_User() {
		logger.info("********** Creating user  ***************");
		Response response = UserEndPoints2.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("********** User is Created ***************");
	}

	@Test(priority = 2)
	public void TC_002_Get_User_ByName() {
		logger.info("********** Reading User Info ***************");
		Response response = UserEndPoints2.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("********** User Info  is Displayed ***************");
	}

	@Test(priority = 3)
	public void TC_003_Update_User_ByName() {
		logger.info("********** Updating User ***************");
		// Update data using Payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		Response response = UserEndPoints2.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().body();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("********** User Updated ***************");
		// Checking Data after Update
		Response responseAfterupdate = UserEndPoints2.readUser(this.userPayload.getUsername());
		Assert.assertEquals(responseAfterupdate.getStatusCode(), 200);
	}

	@Test(priority = 4)
	public void TC_004_Delete_User_ByName() {
		logger.info("**********   Deleting User  ***************");
		Response response = UserEndPoints2.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("********** User Deleted ***************");
	}

}
