package AnalisadorLexico;
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
		//System.out.println("ch: "+ch);
		if (ch==65535){
			//System.out.println("Fim");
		}
		
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
	public Token scan() throws IOException,NumberException,WordException,TokenException{
		
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
				ch=' ';			
				return Word.mult;
			case '+':		
				ch=' ';			
				return Word.plus;
			case '-':
				ch=' ';		
				return Word.minus;
			case '/':
				ch=' ';
				return Word.div;
			case '!':
				if (readch('=')) {
					ch=' ';
					return Word.ne;
				}				
				else return Word.not;
			case '&':
				if (readch('&')) {
					ch=' ';
					return Word.and;
				}				
				else {
					ch=' ';
					return new Token('&');
				}
			case '|':
				if (readch('|')) {
					ch=' ';
					return Word.or;
				}else {
					ch=' ';
					return new Token('|');
				}
			case '=':
				if (readch('=')) {
					ch=' ';
					return Word.eq;
				}				
				else {
					//ch=' ';
					return Word.assing;
				}
			case '<':
				readch();
				if (ch=='=') {
					ch=' ';
					return Word.le;
				}
				if (ch=='<') {
					ch=' ';
					return Word.ll;
				}
				ch=' ';
				return Word.lt;
			case '>':
				readch();
				if (ch=='=') {
					ch=' ';
					return Word.ge;
				}
				if (ch=='>') {
					ch=' ';
					return Word.gg;
				}
				return Word.gt;
			case '(':
				ch=' ';
				return Word.lp;
			case ')':
				ch=' ';
				return Word.rp;
			case ';':
				ch=' ';
				return Word.semi;
			case ',':
				ch=' ';
				return Word.comma;
			case ':':
				ch=' ';
				return Word.colon;
			case '\'':
				ch=' ';
				return Word.sq;
			case '\"' : //leitura de literal
				StringBuffer sb = new StringBuffer();
				do {
					sb.append(ch);
					readch();
				}while(ch!='\n'&ch !='\"');
				if(ch=='\n')
					throw new WordException("Má formação de literal na linha "+line);
				else{
					sb.append(ch);
					String s = sb.toString();
					ch = ' ';
					return new Word(s, 312);
				}	
			case  '“': //leitura de literal
			sb = new StringBuffer();
			do {
				sb.append(ch);
				readch();
			}while(ch!='\n'&ch!='”');
			if(ch=='\n')
				throw new WordException("Má formação de literal na linha "+line);
			else{
				sb.append(ch);
				String s = sb.toString();
				ch = ' ';
				return new Word(s, 312);
			}		
			case '.':
				ch=' ';
				return Word.dot;
			case '_':
				throw new WordException("Token não especificado na gramática na linha "+line);
			
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
						throw new NumberException("Número Mal Formado na linha "+line);
					}
					break;
				}
			}while(Character.isDigit(ch));
			if(isfloat==false){
				if(Character.isLetter(ch)){
					ch=' ';
					throw new NumberException("Número Mal Formado na linha "+line);
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
					throw new NumberException("Número Mal Formado na linha "+line);
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
			if (w!=null) return w; //inclui se palavra nao existe na HashTable
			w = new Word(s,Tag.ID);
			words.put(s, w);
			return w;
		}

		if(ch==65535){ //fim do arquivo
			return null;
		}
		ch = ' ';
		throw new WordException("Token não especificado na gramática na linha "+line);		
		
	}
	
	
	
	
}
