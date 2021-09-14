package TestRunner;

import Pages.Login;
import Setup.Setup;
import Utils.Utils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;

public class LoginTestRunner extends Setup {
    Login objLogin;
    Utils utils;

    @Test(enabled = false, priority = 1, description = "Login with valid username and password", groups = "login")
    public void doLogin() throws Exception {
        driver.get("http://automationpractice.com");
        objLogin = new Login(driver);
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(new FileReader("./src/test/resources/user.json"));
        JSONObject jsonObject = (JSONObject) obj;

        String email = (String) jsonObject.get("email");
        String password = (String) jsonObject.get("password");
        utils = new Utils(driver);
        utils.readJSONArray(0);
        String user = objLogin.doLogin(utils.getEmail(), utils.getPassword());
        Assert.assertEquals(user, "Samia Nira");
        driver.findElement(By.xpath("//a[@class='logout']")).click();
    }

    @Test(enabled =true,priority =3,description ="Login with wrong password",groups ="login")

    public void doLoginForWrongPassword() throws IOException, ParseException, InterruptedException {
        driver.get("http://automationpractice.com");
        objLogin = new Login(driver);

        utils = new Utils(driver);
        utils.readJSONArray(1);

        String authError = objLogin.doLoginForWrongPassword(utils.getEmail(), utils.getPassword());
        Assert.assertEquals(authError, "Authentication failed.");

    }

    @Test(enabled = false, priority = 2, description = "Login with invalid email", groups = "login")
    public void doLoginInvalidEmail() throws IOException, ParseException, InterruptedException {
        driver.get("http://automationpractice.com");
        objLogin = new Login(driver);

        utils = new Utils(driver);
        utils.readJSONArray(2);

        String error = objLogin.doLoginForInvalidEmail(utils.getEmail(), utils.getPassword());
        Assert.assertEquals(error, "Invalid email address.");

    }

}
