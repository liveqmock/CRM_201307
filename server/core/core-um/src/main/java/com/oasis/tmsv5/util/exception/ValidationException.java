package com.oasis.tmsv5.util.exception;

import java.util.Map;

public class ValidationException extends GLException {
   
    /**
     * 
     */
    private static final long serialVersionUID = 8934584679428076450L;
    
    public ValidationException (Map<String,String> errors){
        //super();
        super.setErrorMap(errors);
    }
    
    public ValidationException (){
        super();
    }
    
    public ValidationException(String s) {
        super(s);
    }
    
    public ValidationException(String s,Throwable e) {
        super(s,e);
    }
    
    public ValidationException(Map<String,String> errors,Throwable e) {
    	if(e instanceof ValidationException){
    		GLException ex = new GLException(e);
    		ex.setErrorMap(errors);
    		throw ex;
    		
    	}
    }
    
}
