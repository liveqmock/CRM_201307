package com.oasis.tmsv5.util.exception;


public class PagingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static String ERROR_EXPORT_LIMIT = "E010000003";

    public PagingException(Exception e) {
        super(e);
    }

    public PagingException(String msg) {
        super(msg);
    }

    public PagingException() {
    }

}
