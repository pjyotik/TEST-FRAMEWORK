package com.pjyotik.pages.qdpm;

import com.pjyotik.base.Base;
import com.pjyotik.base.DriverFactory;
import org.openqa.selenium.By;
import org.testng.Assert;

public class HomePage  extends Base {

    By sidebarMenu_Dashboard = By.xpath("//ul[@class='page-sidebar-menu']//i/following-sibling::span[text()='Dashboard']");

    public void clickOnSideMenu(String menu) {
        String xpath_Menu = "//ul[@class='page-sidebar-menu']//i/following-sibling::span[text()=" + menu + "']";
        DriverFactory.getInstance().getDriver().findElement(By.xpath(xpath_Menu)).click();
    }

    public void clickOnSideSubMenu(String menu, String submenu) {
        String MenuXpath = "//ul[@class='page-sidebar-menu']//i/following-sibling::span[text()='" + menu + "']";
        DriverFactory.getInstance().getDriver().findElement(By.xpath(MenuXpath)).click();
        String submenuXpath = "//ul[@class='page-sidebar-menu']//i/following-sibling::span[text()='" + menu + "']/ancestor::a/following-sibling::ul//span[text()='" + submenu + "']";
        DriverFactory.getInstance().getDriver().findElement(By.xpath(submenuXpath)).click();
    }

    public void checkIfDashBoardPageIsOpened() {
        Assert.assertTrue(isElementPresent_custom(DriverFactory.getInstance().getDriver().findElement(sidebarMenu_Dashboard), "DashBoardMenu"));
    }

}
