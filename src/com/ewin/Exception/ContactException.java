package com.ewin.Exception;

public class ContactException extends Exception {

    String message;

    public ContactException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
