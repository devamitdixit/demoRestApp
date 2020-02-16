package com.tul.exceptions;

public class Errors  {
    private String errorCode;
    private String errorMessage;
    private String statusCode;

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Errors(String errorCode, String errorMessage, String statusCode) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
