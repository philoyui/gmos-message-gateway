package cn.com.gome.cloud.gmos.message.exp;

public class GmosException extends RuntimeException{

    public GmosException(String message) {
        super(message);
    }

    public GmosException(String message, Exception e) {
        super(message,e);
    }

}
