package AnalisadorLexico;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException,NumberException,WordException,TokenException,FileNotFoundException{
		String defaultDir = System.getProperty("user.dir"); //o diret�rio pode ser alterado para sua pasta
						
		Lexer lexer = new Lexer(defaultDir+"/Testes/file.txt");	
		Token token;		
		
		do {					
			token=lexer.scan();
			if(token!=null)	System.out.println(token);					
		}while(token!=null);
		
	}

}