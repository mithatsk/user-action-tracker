package com.aprilhorizon.advice;

public class LoggedArgument {
    private String parameter;
    private String argument;
    private String typeName;

    public LoggedArgument(String parameter, String argument, String typeName) {
        this.parameter = parameter;
        this.argument = argument;
        this.typeName = typeName;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
