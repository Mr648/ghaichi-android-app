package com.sorinaidea.ghaichi.webservice.model.responses;

public class Response {
    protected boolean error;
    protected String message;

    public boolean hasError() {
        return isError();
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public boolean isError() {
        return error;
    }
}
