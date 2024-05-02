package com.myblog8.payload;

import java.util.Date;

public class ErrorDetails {

    private Date timestemp;
    private String message;
    private String details;

    public ErrorDetails(Date timestemp, String message, String details) {
        this.timestemp=timestemp;
        this.message=message;
        this.details = details;
    }

    public Date getTimestemp() {
        return timestemp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
