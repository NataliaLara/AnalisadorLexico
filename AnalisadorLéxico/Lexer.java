

import java.io.*;
import java.util.*;

public class Lexer {
	public static int line =1; //contator de linhas
	private char ch = ' '; //caractere lido do arquivo
	private FileReader file;
	
	private Hashtable words = new Hashtable(); //tabela de s�mbolos
	
	//Metodo para inserir palavras reservadas na HashTable
	//é feita antes de iniciar o processamento
	private void reserve(Word w) {
		words.put(w.getLexeme(),w); //lexema � a chave para entrada na HashTable
		// n pode haver mais de um elemento na tabela de s�mbolos com o mesmo lexema
	}
	
	//M�todo construtor
	public Lexer(String fileName) throws FileNotFoundException{
		try {
			file = new FileReader(fileName);
		}
		catch(FileNotFoundException e ) {
			System.out.println("Arquivo n�o encontrado");
			throw e;
		}
		
		//Insere palavras reservadas na HashTable
		
		reserve(new Word("program",Tag.PRG));
		reserve(new Word("is",Tag.IS));
		reserve(new Word("init",Tag.INIT));
		reserve(new Word("end",Tag.END));
		reserve(new Word("declare",Tag.DECLARE));
		reserve(new Word("int",Tag.INT));
		reserve(new Word("float",Tag.FLOAT));
		reserve(new Word("char",Tag.CHAR));
		reserve(new Word("if",Tag.IF));
		reserve(new Word("then",Tag.THEN));
		reserve(new Word("else",Tag.ELSE));
		reserve(new Word("repeat",Tag.REPEAT));
		reserve(new Word("until",Tag.UNTIL));
		reserve(new Word("while",Tag.WHILE));
		reserve(new Word("do",Tag.DO));
		reserve(new Word("in", Tag.IN));
		reserve(new Word("out", Tag.OUT));
	}
	
	//l� o pr�ximo caractere do arquivo
	private void readch() throws IOException{
		ch  = (char)file.read();
	}
	
	//L� o pr�ximo caractere do arquivo e verifica se � igual a c
	private boolean readch(char c ) throws IOException{
		readch();
		if (ch!=c) return false;
		ch = ' ';
		return true;
	}
	
	// m�todo que implementa a an�lise l�xica
	//m�todo chamado pelo analisador sint�tico
	public Token scan() throws IOException{
		ch=' ';
		//Desconsidera delimitadores 
		for(;; readch()) {
			if(ch == ' ' || ch=='\t' || ch =='\r' || ch == '\b')continue;
			else if(ch == '\n')  line++;
			else break;
		}
		
		//Reconhecimento de tokes
		switch (ch) {
			//Operadores
			case '*':				
				return Word.mult;
			case '+':				
				return Word.plus;
			case '-':
				return Word.minus;
			case '/':
				return Word.div;
			case '!':
				if (readch('=')) {
					return Word.ne;
				}				
				else return Word.not;
			case '&':
				if (readch('&')) {
					return Word.and;
				}				
				else return new Token('&');
			case '|':
				if (readch('|')) {
					return Word.or;
				}else return new Token('|');
			case '=':
				if (readch('=')) {
					return Word.eq;
				}				
				else return Word.assing;
			case '<':
				readch();
				if (ch=='=') {
					return Word.le;
				}
				if (ch=='<') {
					return Word.ll;
				}
				return Word.lt;
			case '>':
				readch();
				if (ch=='=') {
					return Word.ge;
				}
				if (ch=='>') {
					return Word.gg;
				}
				return Word.gt;
			case '(':
				return Word.lp;
			case ')':
				return Word.rp;
			case ';':
				return Word.semi;
			case ',':
				return Word.comma;
			case ':':
				return Word.colon;
			case '\'':
				return Word.sq;
			case '\"':
				return Word.dq;
			case '.':
				return Word.dot;
			
		}
		
		//Numeros
		if (Character.isDigit(ch)) {
			float value=0;
			boolean isfloat = false;
			do {
				value = 10*value + Character.digit(ch,10);
				readch();
				if (ch=='.'){
					isfloat = true;
					readch();
					if (!Character.isDigit(ch)){
						//criar excecao
						//System.out.println("Erro - float");
						return new Word("Erro - float",Tag.ID);
					}
					break;
				}
			}while(Character.isDigit(ch));
			if(isfloat==false){
				//verificar caractere depois de numero
				if(Character.isLetter(ch)){
					ch=' ';
					//erro - mal formacao do int
					return new Word("Erro: Int mal formado",Tag.ID);
				}
				return new Int((int)value);
			}		
			else{
				float potencia =10;
				do{
					value+= Character.digit(ch,10)/potencia;
					potencia*=10;
					readch();
				}while(Character.isDigit(ch));
				if(Character.isLetter(ch)){
					ch=' ';
					//erro - mal formacao do int
					return new Word("Erro: Float Mal formado",Tag.FLOAT);
				}
				return new Float(value);
			}
			

			
		}
		
		//Identificadores
		if (Character.isLetter(ch)) {
			StringBuffer sb = new StringBuffer();
			do {
				sb.append(ch);
				readch();
			}while(Character.isLetterOrDigit(ch) || ch =='_');
			
			String s = sb.toString();
			Word w = (Word)words.get(s);
			if (w!=null) return w; //inclui se palavra n�o existe na HashTable
			w = new Word(s,Tag.ID);
			words.put(s, w);
			return w;
		}
		
		//Caracteres nao especificados
		Token t = new Token(ch);
		ch = ' ';
		return t;		
		
	}
	
	
	
	
}
