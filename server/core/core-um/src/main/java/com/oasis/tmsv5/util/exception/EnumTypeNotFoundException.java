package com.oasis.tmsv5.util.exception;



public class EnumTypeNotFoundException extends GLException {

    private static final long serialVersionUID = 1L;

    public EnumTypeNotFoundException(Exception e) {
        super(e);
    }

    public EnumTypeNotFoundException(String msg) {
        super(msg);
    }

    public EnumTypeNotFoundException() {
    }

}