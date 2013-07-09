package com.oasis.tmsv5.util.exception;



public class NotyetImplementedException extends GLException {

    private static final long serialVersionUID = 6705577798967124038L;

    public NotyetImplementedException(Exception e) {
        super(e);
    }

    public NotyetImplementedException(String msg) {
        super(msg);
    }

    public NotyetImplementedException() {
    }

    public NotyetImplementedException(Throwable e) {
        super(e);
    }

    public NotyetImplementedException(Throwable e, String msg) {
        super(msg, e);
    }

}
