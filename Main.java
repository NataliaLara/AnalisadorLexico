
import java.io.FileNotFoundException;
import java.io.IOException;

import AnalisadorLexico.*;
import AnalisadorSintatico.*;


public class Main {
    public static void main(String[] args) throws IOException,NumberException,WordException,TokenException,FileNotFoundException{
		String defaultDir = System.getProperty("user.dir"); //o diret�rio pode ser alterado para sua pasta
        String diretorio = defaultDir+"/Testes/file.txt";	
		Sintatico parser= new Sintatico(diretorio);		 	
		System.out.println("Token\tTag\n");
		parser.initParser();		
	}
}
