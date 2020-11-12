package AnalisadorLexico;
public class Word extends Token{
	
	private String lexeme = "";
	
	//correspondem a tokens de padroes fixos
	
	//Operadores Logicos
	public static Word not = new Word("!", Tag.NOT);
	public static Word and = new Word("&&", Tag.AND);
	public static Word or = new Word("||", Tag.OR);
	public static Word eq = new Word("==", Tag.EQ);
	public static Word ne = new Word("!=", Tag.NE);
	public static Word lt = new Word("<", Tag.LT);
	public static Word le = new Word("<=", Tag.LE);
	public static Word gt = new Word(">", Tag.GT);
	public static Word ge = new Word(">=", Tag.GE);
	public static Word gg= new Word(">>", Tag.GG);
	public static Word ll = new Word("<<", Tag.LL);

	//Operadores
	public static Word assing = new Word("=", Tag.ASSING);
	public static Word plus = new Word("+", Tag.PLUS);
	public static Word minus = new Word("-", Tag.MINUS);
	public static Word mult = new Word("*", Tag.MULT);
	public static Word div = new Word("/", Tag.DIV);

	//Outros Tokens
	public static Word lp = new Word("(", Tag.LP);
	public static Word rp = new Word(")", Tag.RP);
	public static Word semi = new Word(";", Tag.SEMI);
	public static Word comma = new Word(",", Tag.COMMA);
	public static Word colon = new Word(":", Tag.COLON);
	public static Word sq = new Word("\'", Tag.SQ);
	public static Word dq = new Word("\"", Tag.DQ);
	public static Word dot = new Word(".", Tag.DOT);
	
	public Word(String s, int tag) {
		super(tag);
		lexeme=s;
	}
	
	public String toString() {
		return "" + lexeme;
	}
	
	public String getLexeme() {
		return lexeme;
	}

}
