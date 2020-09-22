package Lexico;

public class Word extends Token{
	
	private String lexeme = "";
	
	//correspondem a tokens de padrões fixos
	public static final Word
		mult = new Word("*",Tag.MULT),
		div = new Word("/",Tag.DIV),
		les = new Word("<",Tag.LES),
		gre = new Word(">",Tag.GRE),
		per = new Word("%",Tag.PER),
		and = new Word("&&",Tag.AND),
		or = new Word("||",Tag.OR),
		eq = new Word("==", Tag.EQ),
		ne = new Word("!=", Tag.NE),
		le = new Word("<=",Tag.LE),
		ge = new Word(">=",Tag.GE),
		True = new Word("true", Tag.TRUE),
		False = new Word ("false", Tag.FALSE);
	
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
