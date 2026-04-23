package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

public class ApiSteps {
    private String endpoint;
    private Response response;

    @Given("the API endpoint is {string}")
    public void theApiEndpointIs(String url) {
        this.endpoint = url;
    }

    @When("I send a GET request")
    public void iSendAGetRequest() {
        response = RestAssured.given()
                .header("Accept", "application/json")
                .header("User-Agent", "Mozilla/5.0")
                .get(endpoint);
        System.out.println(response.getBody().asString());
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }

    @Then("the response should contain user {string}")
    public void theResponseShouldContainUser(String expectedName) {
        String actualName = response.jsonPath().getString("name");
        Assert.assertEquals(actualName, expectedName);
    }
}