package com.pjyotik.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.pjyotik.base.DriverFactory;
import com.pjyotik.base.ExtentReport;
import com.pjyotik.base.ExtentReportFactory;
import com.pjyotik.components.JiraOperations;
import com.pjyotik.components.PropertiesOperations;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListenersImplementation implements ITestListener {

    JiraOperations jiraOperations = new JiraOperations();

    static ExtentReports report;
    ExtentTest test;

    public void onStart(ITestContext context) {
        try {
            report = ExtentReport.setupExtentReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onFinish(ITestContext context) {
        //close extent
        report.flush();
    }

    public void onTestStart(ITestResult result) {
        //before each test case
        test = report.createTest(result.getMethod().getMethodName());
        ExtentReportFactory.getInstance().setExtent(test);
    }

    public void onTestSuccess(ITestResult result) {
        ExtentReportFactory.getInstance().getExtent().log(Status.PASS, "Test Case: "+result.getMethod().getMethodName()+ " is Passed.");
        ExtentReportFactory.getInstance().removeExtentObject();
    }

    public void onTestFailure(ITestResult result) {
        ExtentReportFactory.getInstance().getExtent().log(Status.FAIL, "Test Case: "+result.getMethod().getMethodName()+ " is Failed.");
        ExtentReportFactory.getInstance().getExtent().log(Status.FAIL, result.getThrowable());

        //add screenshot for failed test.
        File src = ((TakesScreenshot) DriverFactory.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy HH-mm-ss");
        Date date = new Date();
        String actualDate = format.format(date);

        String screenshotPath = System.getProperty("user.dir")+
                "/Reports/Screenshots/"+actualDate+".jpeg";
        File dest = new File(screenshotPath);

        try {
            FileUtils.copyFile(src, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ExtentReportFactory.getInstance().getExtent().addScreenCaptureFromPath(screenshotPath, "Test case failure screenshot");
            ExtentReportFactory.getInstance().removeExtentObject();

        } catch (IOException e) {
            e.printStackTrace();
        }
        ///////JIRA defect creation part
        String automaticJIRAcreation = PropertiesOperations.getPropertyValueByKey("automatic_Issue_Creation_In_JIRA");
        if(automaticJIRAcreation.trim().equalsIgnoreCase("ON")) {
            String issueS = "Automation Test Failed - "+result.getMethod().getMethodName();
            String issueD = "Test Data to be passed here.";
            String issueNumber = null;
            try {
                issueNumber = jiraOperations.createJiraIssue("AutomationTesting", issueS, issueD, "10000", "5", "AutomationTesting", "SIT", "5f782c4b95fe8e0069705791");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            try {
                jiraOperations.addAttachmentToJiraIssue(issueNumber, screenshotPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void onTestSkipped(ITestResult result) {
        ExtentReportFactory.getInstance().getExtent().log(Status.SKIP, "Test Case: "+result.getMethod().getMethodName()+ " is skipped.");
        ExtentReportFactory.getInstance().removeExtentObject();
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onTestFailedWithTimeout(ITestResult result) {
    }



}
