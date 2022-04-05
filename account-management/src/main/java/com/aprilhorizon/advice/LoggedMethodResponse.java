package com.aprilhorizon.advice;

public class LoggedMethodResponse {
    private String content;
    private String typeName;

    public LoggedMethodResponse(String response, String typeName) {
        this.content = response;
        this.typeName = typeName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
