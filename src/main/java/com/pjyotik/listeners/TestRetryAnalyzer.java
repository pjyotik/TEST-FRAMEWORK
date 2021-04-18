package com.pjyotik.listeners;

import com.pjyotik.components.PropertiesOperations;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestRetryAnalyzer implements IRetryAnalyzer {
    int counter = 1;
    int retryMaxLimit  = Integer.parseInt(PropertiesOperations.getPropertyValueByKey("retryCount"));

    @Override
    public boolean retry(ITestResult result) {
        if(counter<retryMaxLimit) {
            counter++;
            return true;
        }
        return false;
    }

}
