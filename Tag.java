package Lexico;

public class Tag {
	
	public final static int
		//Palavras reservadas
		PRG=256,
		BEG=257,
		END = 258,
		TYPE = 259,
		INT = 260,
		CHAR = 261,
		BOOL = 262,
		TRUE = 263,
		FALSE = 264,
		
		//Operadores e pontuação
		GRE = 280,
		LES = 281,
		DIV = 282,
		MULT = 283,
		PER = 284,
		IF = 285,
		AND = 286,
		OR = 287,
		EQ = 288,
		GE = 289,
		LE = 290,
		NE = 291,
		
		//Outros tokens
		NUM = 278,
		ID = 279;
	
	// a numeração é dessa forma para simplificar a implementação
	//o autor tomou base da tabela asc (vai até 255)

}
