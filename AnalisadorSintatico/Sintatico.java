package AnalisadorSintatico;

import java.io.FileNotFoundException;
import java.io.IOException;

import AnalisadorLexico.*;

public class Sintatico {

    private Lexer lexer;
    private Token token;
    private int tok; //tag do token 

    public Sintatico(String diretorio) throws IOException{
        lexer = new Lexer(diretorio);       
    }

    public void initParser() throws IOException, NumberException, WordException, TokenException {
        advance();
        program();
    }
    void error(){
        System.out.println("Sequência não esperada na linha "+lexer.line);
    }
    //Usado em testes para encontrar erros
    /*
    void error(int tokEsperado,String metodo){
        System.out.println("Sequência não esperada na linha "+lexer.line+ ", no método "+metodo);
        System.out.println("Token esperado "+tokEsperado);
        System.exit(0);
    }
    
    void error(int [] tokEsperado,String metodo){
        System.out.println("Sequência não esperada na linha "+lexer.line+ ", no método "+metodo);
        for (int i = 0; i < tokEsperado.length; i++) {
            System.out.println("Tokens esperados "+tokEsperado[i]);
        }
        
        System.exit(0);
    }*/

    void eat(int t) throws IOException, NumberException, WordException, TokenException {
        if (tok==t) advance();
        else error();
       }

    void advance() throws IOException, NumberException, WordException, TokenException {
        try{
            token = lexer.scan();
            tok=token.tag;
            System.out.println(token.toString()+ "\t"+token.tag);	
        }catch(NullPointerException n){
            if(tok!=Tag.END)
                error();
            else
                System.out.println("\nFim da Análise Sintática");
            System.exit(0);
        }
        
    }

    //Novos
    public void program() throws IOException, NumberException, WordException, TokenException {
        switch(tok){
            case Tag.PRG: eat(Tag.PRG); eat(Tag.ID); eat(Tag.IS); body(); break;
            default: error();
        }
    }
    
     
    
    public void body() throws IOException, NumberException, WordException, TokenException {
        decl_list_init(); eat(Tag.INIT); stmt_list(); eat(Tag.END); 
       
        if(tok!=Tag.END)
            error();
    }
    
     
    
    public void decl_list_init() throws IOException, NumberException, WordException, TokenException {
        switch(tok){            
            case Tag.DECLARE: eat(Tag.DECLARE); decl_list(); break;
            default: return;
        }
    }
    
     
    
    public void decl_list() throws IOException, NumberException, WordException, TokenException {
        decl();
        decl_list_continue();
    }
    
     
    
    public void decl_list_continue() throws IOException, NumberException, WordException, TokenException {
        switch(tok){
            case Tag.SEMI: eat(Tag.SEMI); decl(); decl_list_continue(); break;
            default: return;
        }    
    }
    
     
    
    public void decl() throws IOException, NumberException, WordException, TokenException {
        ident_list(); eat(Tag.COLON); type();
    }
    
     
    
    public void ident_list() throws IOException, NumberException, WordException, TokenException {
        eat(Tag.ID); ident_list_continue();
    }
    
     
    
    public void ident_list_continue() throws IOException, NumberException, WordException, TokenException {
        switch(tok){
            case Tag.COMMA: eat(Tag.COMMA); eat(Tag.ID); ident_list_continue(); break;
            default: return;
        }
    }

    public void type() throws IOException, NumberException, WordException, TokenException {
        switch(tok){
            case Tag.INT: eat(Tag.INT);
                break;
            case Tag.FLOAT: eat(Tag.FLOAT);
                break;
            case Tag.CHAR: eat(Tag.CHAR);
                break;
            default: error();
        }
    }
    
     
    
    public void stmt_list() throws IOException, NumberException, WordException, TokenException {
        stmt(); stmt_list_continue();
    }
    
     
    
    public void stmt_list_continue() throws IOException, NumberException, WordException, TokenException {
        switch(tok){
            case Tag.SEMI: eat(Tag.SEMI); stmt(); stmt_list_continue();
            default: return;
        }
    }
    
     
    
    public void stmt() throws IOException, NumberException, WordException, TokenException {
        switch(tok){
            case Tag.ID: assign_stmt();
                break;
            case Tag.IF: if_stmt();
                break;
            case Tag.WHILE: while_stmt();
                break;
            case Tag.REPEAT: repeat_stmt();
                break;
            case Tag.IN: read_stmt();
                break;
            case Tag.OUT: write_stmt();
                break;
            default: error();
        }
    }
    
     
    
    public void assign_stmt() throws IOException, NumberException, WordException, TokenException {
        eat(Tag.ID); eat(Tag.ASSING); simple_expr();
    }
    
     
    
    public void if_stmt() throws IOException, NumberException, WordException, TokenException {
        eat(Tag.IF); condition(); eat(Tag.THEN); stmt_list(); else_stmt(); eat(Tag.END);
    }
    
     
    
    public void else_stmt() throws IOException, NumberException, WordException, TokenException {
        switch(tok){
            case Tag.ELSE: eat(Tag.ELSE); stmt_list();
                break;
            default: return;
        }
    }
    
     
    
    public void condition() throws IOException, NumberException, WordException, TokenException {
        expression();
    }
    
     
    
    public void repeat_stmt() throws IOException, NumberException, WordException, TokenException {
        eat(Tag.REPEAT); stmt_list(); stmt_suffix();
    }
    
     
    
    public void stmt_suffix() throws IOException, NumberException, WordException, TokenException {
        eat(Tag.UNTIL); condition();
    }
    
     
    
    public void while_stmt() throws IOException, NumberException, WordException, TokenException {
        stmt_prefix(); stmt_list(); eat(Tag.END);
    }
    
     
    
    public void stmt_prefix() throws IOException, NumberException, WordException, TokenException {
        eat(Tag.WHILE); condition(); eat(Tag.DO);
    }
    
     
    
    public void read_stmt() throws IOException, NumberException, WordException, TokenException {
        eat(Tag.IN); eat(Tag.LL); eat(Tag.ID);
    }
    
     
    
    public void write_stmt() throws IOException, NumberException, WordException, TokenException {
        eat(Tag.OUT); eat(Tag.GG); writable();
    }
    
     
    
    public void writable() throws IOException, NumberException, WordException, TokenException {
        switch(tok){
            case Tag.LIT: eat(Tag.LIT);
                break;
            case Tag.ID:
            case Tag.INT:
            case Tag.FLOAT:
            case Tag.CHAR:
            case Tag.LP:
            case Tag.NOT:
            case Tag.MINUS: simple_expr();
                break;
            default: error();
        }
    }
    
     
    
    public void expression() throws IOException, NumberException, WordException, TokenException {
        simple_expr(); expression_continue();
    }
    
    public void relop() throws IOException, NumberException, WordException, TokenException {
        switch(tok){
            case Tag.EQ: eat(Tag.EQ); break;
            case Tag.LT: eat(Tag.LT); break;
            case Tag.LE: eat(Tag.LE); break;
            case Tag.GT: eat(Tag.GT); break;
            case Tag.GE: eat(Tag.GE); break;
            case Tag.NE: eat(Tag.NOT); break;
            default: error();
        }
    }
    
    public void  expression_continue() throws IOException, NumberException, WordException, TokenException {
        switch(tok){
            case Tag.EQ:
            case Tag.LT:
            case Tag.LE:
            case Tag.GT:
            case Tag.GE:
            case Tag.NE: relop(); simple_expr();
                break;
            default: return;
        }
    }
    
     
    
    public void simple_expr() throws IOException, NumberException, WordException, TokenException {
        term(); simple_expr_continue();
    }
    
     
    
    public void simple_expr_continue() throws IOException, NumberException, WordException, TokenException {
        switch(tok){
            case Tag.PLUS:
            case Tag.MINUS:
            case Tag.OR: addop(); simple_expr(); break;
            default: return;
        }
    }

    public void addop() throws IOException, NumberException, WordException, TokenException {
        switch(tok){
            case Tag.PLUS: eat(Tag.PLUS); break;
            case Tag.MINUS: eat(Tag.MINUS); break;
            case Tag.OR: eat(Tag.OR); break;
            default: error();
        }
    }
    
     
    
    public void term() throws IOException, NumberException, WordException, TokenException {
        fator_a(); term_continue();
    }
    
    public void mulop() throws IOException, NumberException, WordException, TokenException {
        switch(tok){
            case Tag.MULT: eat(Tag.MULT); break;
            case Tag.DIV: eat(Tag.DIV); break;
            case Tag.AND: eat(Tag.AND); break;
            default: error();
        }
    }
    
    public void term_continue() throws IOException, NumberException, WordException, TokenException {
        switch(tok){
            case Tag.MULT: 
            case Tag.DIV:  
            case Tag.AND: mulop(); term(); 
                break;
            default: return;
        }
    }
    
     
    
    public void fator_a() throws IOException, NumberException, WordException, TokenException {
        switch(tok){
            case Tag.NOT: eat(Tag.NOT); factor(); break;
            case Tag.MINUS: eat(Tag.MINUS); factor(); break;
            case Tag.ID: factor(); break;
            case Tag.INT: factor(); break;
            case Tag.FLOAT: factor(); break;
            case Tag.CHAR: factor(); break;
            case Tag.LP: factor(); break;
            default: error();
        }
    }
    
     
    
    public void factor() throws IOException, NumberException, WordException, TokenException {
        switch(tok){
            case Tag.ID: eat(Tag.ID); break;
            case Tag.INT: eat(Tag.INT); break;
            case Tag.FLOAT: eat(Tag.FLOAT);break;
            case Tag.CHAR: eat(Tag.CHAR);break;
            case Tag.LP: eat(Tag.LP); expression(); eat(Tag.RP); break;
            default: error();
        }
    }


}