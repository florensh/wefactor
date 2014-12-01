package de.hhn.labswps.wefactor.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import de.hhn.labswps.wefactor.BaseTest;

public class LogInTest extends BaseTest {
    private Selenium selenium;

    @Before
    public void setUp() throws Exception {
        selenium = new DefaultSelenium("localhost", 8080, "V/:firefox.lnk",
                "http://www.google.com/");
        selenium.start("version=10;platform=WINDOWS;screenshot=false");
    }

    @Test
    public void testLogIn() throws Exception {
        selenium.open("/");
        selenium.click("link=Log in");
        selenium.waitForPageToLoad("30000");
        selenium.type("name=username", "Cagdas");
        selenium.type("name=password", "Canocan62");
        selenium.click("//button[@type='submit']");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=Cagdas");
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");
    }

    @After
    public void tearDown() throws Exception {
        selenium.stop();
    }
}
