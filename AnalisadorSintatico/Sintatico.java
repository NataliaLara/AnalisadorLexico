package AnalisadorSintatico;

import java.io.FileNotFoundException;
import java.io.IOException;

import AnalisadorLexico.*;

public class Sintatico {

    private Lexer lexer;
    Token token;
    int tok; //tag do token 

    public Sintatico() throws FileNotFoundException {
        String defaultDir = System.getProperty("user.dir"); // o diret�rio pode ser alterado para sua pasta

        lexer = new Lexer(defaultDir + "/file.txt");
    }

    void error(){
        System.out.println("Sequência não esperada");
    }

    void eat(int t) throws IOException, NumberException, WordException, TokenException {
        if (tok==t) advance();
        else error();
       }

    void advance() throws IOException, NumberException, WordException, TokenException {
        token = lexer.scan();
        tok=token.tag;
    }

    void getToken()throws IOException, NumberException, WordException, TokenException{
        token =lexer.scan();
        tok=token.tag;
    }

    void program() throws IOException, NumberException, WordException, TokenException {
        getToken();
        eat(Tag.PRG); eat(Tag.IS);
    }

}