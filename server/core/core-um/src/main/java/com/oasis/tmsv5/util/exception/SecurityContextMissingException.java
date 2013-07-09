package com.oasis.tmsv5.util.exception;



public class SecurityContextMissingException extends GLException {

    private static final long serialVersionUID = 7192220317022054078L;
    
    private static final String error = "can not find security context";

    public SecurityContextMissingException(Exception e) {
        super(e);
    }

    public SecurityContextMissingException(String msg) {
        super(msg);
    }

    public SecurityContextMissingException() {
       super(error);
    }

    public SecurityContextMissingException(Throwable e) {
        super(e);
    }

    public SecurityContextMissingException(String msg, Throwable e) {
        super(msg, e);
    }
    
    

}
