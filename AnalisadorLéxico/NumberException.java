public class NumberException extends Exception{ 

    private String msg;
    public int contador;
    
    public NumberException (String msg){
      super(msg);
      this.msg = msg;
    } 
  } 
