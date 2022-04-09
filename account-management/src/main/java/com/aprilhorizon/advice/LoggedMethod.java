package com.aprilhorizon.advice;

import java.util.ArrayList;

public class LoggedMethod {
    private String className;
    private String methodName;
    private LoggedMethodResponse response;
    private String executionTime;
    private String executionTimeUnit;
    private ArrayList<LoggedArgument> arguments = new ArrayList<>();

    public LoggedMethod() {
        this.className = "";
        this.methodName = "";
        this.response = new LoggedMethodResponse();
        this.executionTime = "0";
        this.executionTimeUnit = "milis";
        this.arguments = new ArrayList<>();
    }

    public LoggedMethod(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public LoggedMethodResponse getResponse() {
        return response;
    }

    public void setResponse(LoggedMethodResponse response) {
        this.response = response;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public String getExecutionTimeUnit() {
        return executionTimeUnit;
    }

    public void setExecutionTimeUnit(String executionTimeUnit) {
        this.executionTimeUnit = executionTimeUnit;
    }

    public ArrayList<LoggedArgument> getArguments() {
        return arguments;
    }

    public void setArguments(ArrayList<LoggedArgument> arguments) {
        this.arguments = arguments;
    }
}