package com.vtb.hackathon.orchestra;

public class TestResult {
    private String taskName;
    private boolean success;
    private String message;
    
    public TestResult(String taskName, boolean success, String message) {
        this.taskName = taskName;
        this.success = success;
        this.message = message;
    }
    
    public String getTaskName() { return taskName; }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}
