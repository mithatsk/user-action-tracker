package com.aprilhorizon.advice.models;

public final class LogResultTuple<MethodLog extends LoggedMethod, Object> {
    private final MethodLog methodLog;
    private final Object response;

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
