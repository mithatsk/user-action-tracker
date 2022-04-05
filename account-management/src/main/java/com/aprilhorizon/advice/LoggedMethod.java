package com.aprilhorizon.advice;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class LoggedMethod {
    public String className;
    public String methodName;
    public LoggedMethodResponse response;
    public String executionTime;
    public ChronoUnit executionTimeUnit;
    public ArrayList<LoggedArgument> arguments = new ArrayList<>();

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

    public ChronoUnit getExecutionTimeUnit() {
        return executionTimeUnit;
    }

    public void setExecutionTimeUnit(ChronoUnit executionTimeUnit) {
        this.executionTimeUnit = executionTimeUnit;
    }

    public ArrayList<LoggedArgument> getArguments() {
        return arguments;
    }

    public void setArguments(ArrayList<LoggedArgument> arguments) {
        this.arguments = arguments;
    }
}