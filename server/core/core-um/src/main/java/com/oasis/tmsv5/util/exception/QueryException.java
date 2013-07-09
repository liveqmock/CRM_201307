package com.oasis.tmsv5.util.exception;



public class QueryException extends GLException {

    private static final long serialVersionUID = 1L;

    public QueryException(Exception e) {
        super(e);
    }

    public QueryException(String msg) {
        super(msg);
    }

    public QueryException(String msg, Throwable e) {
        super(msg, e);
    }

    public QueryException() {
    }

}
