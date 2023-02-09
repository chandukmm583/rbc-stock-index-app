package com.rbc.digital;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import java.io.IOException;
import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import org.springframework.context.annotation.Scope;
@Scope(SCOPE_CUCUMBER_GLUE)
public class CommonSteps  {
    CucumberIntegrationTest cucumberIntegrationTest = new CucumberIntegrationTest();
    @Given("No data is present")
    public void noDataIsPresent() {
        cucumberIntegrationTest.deleteAllData();
    }

    @When("the client calls getAllRecords")
    public void theClientCallsIndexRecords() throws IOException {
        cucumberIntegrationTest.executeGet("http://localhost:8080/index/records");
    }
    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        final HttpStatusCode currentStatusCode = cucumberIntegrationTest.latestResponse.getTheResponse().getStatusCode();
        assertThat("status code is incorrect : " + cucumberIntegrationTest.latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
    }
}
