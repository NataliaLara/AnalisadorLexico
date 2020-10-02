public class WordException extends Exception{ 

    private String msg;
    public int contador;
    
    public WordException (String msg){
      super(msg);
      this.msg = msg;
    } 
  } 
