package com.pjyotik.pages.qdpm;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UserPage {

    @FindBy(xpath = "//button[text()='Add User']")
    WebElement btn_addUser;

    @FindBy(id = "search_menu")
    WebElement field_Search;

    @FindBy(xpath = "//*[@id='search_menu']//input[@name='search[keywords]']")
    WebElement txt_Search;

    @FindBy(xpath = "//*[@id='search_menu']//input[@type='submit']")
    WebElement btn_Search;

    @FindBy(id = "users_users_group_id")
    WebElement dd_group;

    @FindBy(name = "users[name]")
    WebElement txt_FullName;

    @FindBy(name = "users[password]")
    WebElement txt_Password;

    @FindBy(name = "users[email]")
    WebElement txt_Email;

    @FindBy(name = "extra_fields[9]")
    WebElement txt_Phone;

    @FindBy(id = "users_photo")
    WebElement btn_UserPhoto;

    @FindBy(id = "submit_button")
    WebElement btn_Save;

    @FindBy(id = "users_notify")
    WebElement chk_notifyUser;

    public UserPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

}
