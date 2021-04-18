package com.pjyotik.pages.qdpm;

import com.pjyotik.base.Base;
import com.pjyotik.base.ExtentReportFactory;
import com.pjyotik.components.DB_Operations;
import com.pjyotik.components.ExcelOperations;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

public class TestCases extends Base {

    LoginPage loginPage = new LoginPage();
    HomePage homePage = new HomePage();
    TaskPage taskPage = new TaskPage();
    DB_Operations db_operations = new DB_Operations();
    ExcelOperations excel = new ExcelOperations("TaskCreationData");


    @Test(dataProvider = "taskCreationData")
    public void TaskCreationTest(Object obj1) throws Throwable {
        @SuppressWarnings("unchecked")
        HashMap<String, String> testData = (HashMap<String, String>) obj1;

        ExtentReportFactory.getInstance().getExtent().info("Test Data for this execution run is: "+ testData);

        loginPage.login(testData.get("UserName"), testData.get("Password"));
        //check if dashboard page opens
        homePage.checkIfDashBoardPageIsOpened();
        homePage.clickOnSideSubMenu("Tasks", "Add Task");
        //add task
        taskPage.createTask(testData);
        //verify task on UI
        taskPage.Search_Verify_TaskCreationOnUI(testData);

        //verify DB
        String sql = "SELECT * FROM `tasks` where name = '"+testData.get("TaskName")+"'";
        HashMap<String, String> dbData = db_operations.getSqlResultInMap(sql);
        String TaskName = dbData.get("name");
        assertEqualsString_custom(testData.get("TaskName"), TaskName, "DB_Task_Name");
        //assertEqualsString_custom("TaskFailureName", TaskName, "DB_Task_Name");

    }

    // DataProvider method --> return object array
    @DataProvider(name = "taskCreationData")
    public Object[][] testDataSupplier() throws Exception {
        Object[][] obj = new Object[excel.getRowCount()][1];
        for (int i = 1; i <= excel.getRowCount(); i++) {
            HashMap<String, String> testData = excel.getTestDataInMap(i);
            obj[i-1][0] = testData;
        }
        return obj;

    }

}
