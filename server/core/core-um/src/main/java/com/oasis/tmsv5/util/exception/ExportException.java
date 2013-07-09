package com.oasis.tmsv5.util.exception;



public class ExportException extends GLException {
   
    /**
     * 
     */
    private static final long serialVersionUID = 8934584679428076450L;

    public ExportException (){
        super();
    }
    
    public ExportException(String s) {
        super(s);
    }
    
    public ExportException(String s,Throwable e) {
        super(s,e);
    }
    
}
