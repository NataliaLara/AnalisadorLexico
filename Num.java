package Lexico;

public class Num extends Token{
	//diferença da classe token é o dado value que é o valor da constante	
	
	public final int value;
	
	public Num(int value) {
		super(Tag.NUM);
		this.value=value;
	}
	
	public String toString() {
		return ""+ value;
	}

}
