package com.oasis.tmsv5.util.exception;



public class MissingConfigurationDataException extends GLException {

    private static final long serialVersionUID = -8228297962601250162L;


    public MissingConfigurationDataException(Exception e) {
        super(e);
    }

    public MissingConfigurationDataException(String msg) {
        super(msg);
    }

    public MissingConfigurationDataException() {
        super();
    }

    public MissingConfigurationDataException(Throwable e) {
        super(e);
    }

    public MissingConfigurationDataException(String msg, Throwable e) {
        super(msg, e);
    }

}
