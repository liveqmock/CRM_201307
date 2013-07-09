package com.oasis.tmsv5.util.exception;



public class LoginException extends GLException {
   
    /**
     * 
     */
    private static final long serialVersionUID = 8934584679428076450L;

    public LoginException (){
        super();
    }
    
    public LoginException(String s) {
        super(s);
    }
    
    public LoginException(String s,Throwable e) {
        super(s,e);
    }
    
}
