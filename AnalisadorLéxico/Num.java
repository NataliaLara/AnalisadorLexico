

public class Num /*extends Token*/{
	//diferen�a da classe token � o dado value que � o valor da constante	
	
	public final int value;
	
	public Num(int value) {
		//super(Tag.NUM);
		this.value=value;
	}
	
	public String toString() {
		return ""+ value;
	}

}
