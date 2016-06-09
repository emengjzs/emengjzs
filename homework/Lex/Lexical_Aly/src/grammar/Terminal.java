package grammar;

import lex.Token;

public class Terminal extends Sign {
    private String value;
    public static final String END_SIGN ="$"; 
    
	public Terminal(Token token) {
		this.sign = token.getType();
		this.value = token.getValue();
	}
	
	public Terminal(String type) {
		this.sign = type;
	}
	
	public boolean equals(Sign s) {
		return s instanceof Terminal && s.sign.equals(sign);
	}
	
	public boolean equals(String type) {
		return this.sign.equals(type);
	}
	
}
