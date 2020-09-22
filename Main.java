package Lexico;


import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		String defaultDir = System.getProperty("user.dir")+"/src/Lexico/"; //o diretório pode ser alterado para sua pasta
		
		Lexer lexer = new Lexer(defaultDir+"file.txt");		
		do {
			lexer.scan();
		}while(lexer.scan()!=null);
		

	}

}
