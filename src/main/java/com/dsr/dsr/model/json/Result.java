package com.dsr.dsr.model.json;

import java.util.Date;

public class Result {
    private String type;

    private String content;

    private Date time;

    public Result(String content) {
        this.type = "text";
        this.content = content;
    }

    public Result(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public Result(String type, String content, Date time) {
        this.type = type;
        this.content = content;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
