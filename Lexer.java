package Lexico;

import java.io.*;
import java.util.*;

public class Lexer {
	public static int line =1; //contator de linhas
	private char ch = ' '; //caractere lido do arquivo
	private FileReader file;
	
	private Hashtable words = new Hashtable(); //tabela de s�mbolos
	
	//M�todo para inserir palavras reservadas na HashTable
	//� feita antes de iniciar o processamento
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
		reserve(new Word("if",Tag.IF));
		reserve(new Word("program",Tag.PRG));
		reserve(new Word("begin",Tag.BEG));
		reserve(new Word("type",Tag.TYPE));
		reserve(new Word("int",Tag.INT));
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
				System.out.println("*");
				ch = ' ';
				return Word.mult;
			case '%':
				System.out.println("%");
				ch = ' ';
				return Word.per;
			case '/':
				System.out.println('/');
				ch = ' ';
				return Word.div;
			case '!':
				if (readch('=')) {
					System.out.println("!=");
					return Word.ne;
				}
				else return new Token('!');
			case '&':
				if (readch('&')) {
					System.out.println("&&");
					return Word.and;
				}
				else return new Token('&');
			case '|':
				if (readch('|')) {
					System.out.println("||");
					return Word.or;
				}
				else return new Token('|');
			case '=':
				if (readch('=')) {
					System.out.println("==");
					return Word.eq;
				}
				else return new Token('=');
			case '<':
				if (readch('=')) {
					System.out.println("<=");
					return Word.le;
				}
				System.out.println("<");
				return Word.les;
			case '>':
				if (readch('=')) {
					System.out.println(">=");
					return Word.ge;
				}
				System.out.println(">");
				return Word.gre;
		}
		
		//N�meros
		if (Character.isDigit(ch)) {
			int value=0;
			do {
				value = 10*value + Character.digit(ch,10);
				readch();
			}while(Character.isDigit(ch));
			System.out.println(value);
			return new Num(value);
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
			System.out.println(w);
			return w;
		}
		
		//Caracteres n�o especificados
		Token t = new Token(ch);
		ch = ' ';
		return t;		
		
	}
	
	
	
	
}
