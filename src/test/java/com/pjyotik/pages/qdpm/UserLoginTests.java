package com.pjyotik.pages.qdpm;

import com.pjyotik.base.Base;
import org.testng.annotations.Test;

public class UserLoginTests extends Base {

    LoginPage loginPage = new LoginPage();

    @Test
    public void ManagerLoginTest() throws Throwable {

        loginPage.login("admin@admin.com", "admin@123");
        Thread.sleep(2000); ////// not required, adding just to see tests are running in parallel


    }

    @Test
    public void ClientLoginTest() throws Throwable {

        loginPage.login("client@localhost.com", "admin@123");
        Thread.sleep(2000); ////// not required, adding just to see tests are running in parallel

    }

    @Test
    public void DesignerLoginTest() throws Throwable {

        loginPage.login("designer@localhost.com", "admin@123");
        Thread.sleep(2000); ////// not required, adding just to see tests are running in parallel
        assertEqualsString_custom("ExpectedTest", "ActualText", "LoginPageHomePage");

    }
}
