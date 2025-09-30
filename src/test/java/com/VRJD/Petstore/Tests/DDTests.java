package com.VRJD.Petstore.Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.VRJD.Petstore.EndPoints.UserEndPoints;
import com.VRJD.Petstore.PayLoad.User;
import com.VRJD.Petstore.Utilities.DataProviders;

import io.restassured.response.Response;

public class DDTests {

	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testPostuser(String userID, String userName, String firstName, String lastName, String userEmail,
			String password, String phoneNumber) {
		User userPayload = new User();
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstName(firstName);
		userPayload.setLastName(lastName);
		userPayload.setEmail(userEmail);
		userPayload.setPassword(password);
		userPayload.setPhone(phoneNumber);
		Response response = UserEndPoints.createUser(userPayload);
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void testDeleteUserByName(String userName) {
		Response response = UserEndPoints.deleteUser(userName);
		Assert.assertEquals(response.getStatusCode(), 200);
	}

}
