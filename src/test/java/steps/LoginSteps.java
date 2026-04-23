package steps;

import base.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.LoginPage;
import utils.ExcelReader;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

public class LoginSteps {
    private LoginPage loginPage;
    private Properties config;

    @Before
    public void setup() throws Exception {
        config = new Properties();
        config.load(new FileInputStream("src/test/resources/config.properties"));

        BaseTest.initializeDriver(config.getProperty("browser"), config.getProperty("grid.url"));
        loginPage = new LoginPage(BaseTest.getDriver());
    }

    @After
    public void tearDown() {
        BaseTest.quitDriver();
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        BaseTest.getDriver().get(config.getProperty("app.url"));
    }

    @When("I login using data from Excel row {int}")
    public void iLoginUsingDataFromExcelRow(Integer rowIndex) throws Exception {
        String excelPath = "src/test/resources/testdata/Data.xlsx";
        Map<String, String> data = ExcelReader.getRowData(excelPath, "Login", rowIndex);

        loginPage.enterUsername(data.get("Username"));
        loginPage.enterPassword(data.get("Password"));
        loginPage.clickLogin();
    }

    @Then("I should see the secure area dashboard")
    public void iShouldSeeTheSecureAreaDashboard() {
        Assert.assertTrue(loginPage.isLoginSuccessful());
    }
}