package AnalisadorLexico;
public class TokenException extends Exception{ 

    private String msg;
    public int contador;
    
    public TokenException (String msg){
      super(msg);
      this.msg = msg;
    } 
  } 
