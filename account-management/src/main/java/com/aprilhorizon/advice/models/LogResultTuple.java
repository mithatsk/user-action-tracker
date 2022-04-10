package com.aprilhorizon.advice.models;

public class LogResultTuple<MethodLog extends LoggedMethod, Object> {
    private MethodLog methodLog;
    private Object response;

    public LogResultTuple(MethodLog methodLog, Object response) {
        this.methodLog = methodLog;
        this.response = response;
    }

    public MethodLog getMethodLog() {
        return methodLog;
    }

    public Object getResponse() {
        return response;
    }
}
