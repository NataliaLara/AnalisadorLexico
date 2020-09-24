
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		String defaultDir = System.getProperty("user.dir"); //o diret�rio pode ser alterado para sua pasta
		
		Lexer lexer = new Lexer(defaultDir+"/file.txt");	
		Token token;
		int cont=0;
		do {
			cont++;
			token=lexer.scan();
			if(token.tag!=261&token.toString().equals("65535")) break;
			else System.out.println(token);
			//System.out.println(token);
		}while(token!=null);

	}

}
