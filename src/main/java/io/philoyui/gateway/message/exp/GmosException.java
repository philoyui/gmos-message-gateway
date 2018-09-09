package io.philoyui.gateway.message.exp;

public class GmosException extends RuntimeException{

    public GmosException(String message, Exception e) {
        super(message,e);
    }

}
