package com.aprilhorizon.advice;

public class LoggedMethodResponse {
    private Object content;
    private String typeName;

    public LoggedMethodResponse(Object content, String typeName) {
        this.content = content;
        this.typeName = typeName;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
