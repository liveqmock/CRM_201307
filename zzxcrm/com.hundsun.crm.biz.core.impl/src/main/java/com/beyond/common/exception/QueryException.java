/**
 * 
 */
package com.beyond.common.exception;

/**
 * @author liyue
 *
 */
public class QueryException extends RuntimeException {

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
}
