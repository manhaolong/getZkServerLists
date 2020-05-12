package com.ismarthealth.notice.component.server.exception;

public class BaseException extends RuntimeException {
    private String message;
    private Integer returnCode;

    public BaseException(Integer returnCode, String message) {
        super(message);
        this.message = message;
        this.returnCode = returnCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }
}
