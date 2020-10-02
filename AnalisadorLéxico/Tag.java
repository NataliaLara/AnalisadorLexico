

public class Tag {
	
	public final static int
		// Palavras Reservadas
        PRG = 256,
        IS = 257,
        INIT = 258,
        END = 259,
        DECLARE = 260,
        INT = 261,
        FLOAT = 262,
        CHAR = 263,
        IF = 264,
        THEN = 265,
        ELSE = 266,
        REPEAT = 267,
        UNTIL = 268,
        WHILE = 269,
        DO = 270,
        IN = 271,
        OUT = 272,

        // Operadores
        ASSING = 287,
        EQ = 288,
        GT = 289,
        GE = 290,
        LT = 291,
        LE = 292,
        NOT = 293,
        NE = 294,
        PLUS = 295,
        MINUS = 296,
        OR = 297,
        MULT = 298,
        DIV = 299,
		AND = 300,
		GG = 311,
		LL = 312,

        //Outros tokens
        SEMI = 301,
        LP = 302,
        RP = 303,
        COMMA = 304,
        DOT = 305,
        COLON = 306,
        SQ = 307,
        DQ = 308,
        ID = 309,
        INT_=310,
        EOF = 311,
        LIT = 312; //literal
	
	// a numera��o � dessa forma para simplificar a implementa��o
	//o autor tomou base da tabela asc (vai at� 255)

}
