package com.aprilhorizon.advice.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LoggedMethod {
    private String className;
    private String methodName;
    private LoggedMethodResponse response;
    private String executionDuration;
    private String executionDurationUnit;
    private String executionDateTime;
    private ArrayList<LoggedArgument> arguments = new ArrayList<>();

    public LoggedMethod() {
        this.className = "";
        this.methodName = "";
        this.response = new LoggedMethodResponse();
        this.executionDuration = "0";
        this.executionDurationUnit = "milis";
        this.executionDateTime = executionDateTime();
        this.arguments = new ArrayList<>();
    }

    private String executionDateTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }

    public LoggedMethod(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
        this.executionDateTime = executionDateTime();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public String getExecutionDuration() {
        return executionDuration;
    }

    public void setExecutionDuration(String executionDuration) {
        this.executionDuration = executionDuration;
    }

    public String getExecutionDurationUnit() {
        return executionDurationUnit;
    }

    public void setExecutionDurationUnit(String executionDurationUnit) {
        this.executionDurationUnit = executionDurationUnit;
    }

    public ArrayList<LoggedArgument> getArguments() {
        return arguments;
    }

    public void setArguments(ArrayList<LoggedArgument> arguments) {
        this.arguments = arguments;
    }

    public String getExecutionDateTime() {
        return executionDateTime;
    }

    public void setExecutionDateTime(String executionDateTime) {
        this.executionDateTime = executionDateTime;
    }
}