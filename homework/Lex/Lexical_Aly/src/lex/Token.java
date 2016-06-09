package lex;

public class Token {
	

	
	private String type; // word unit
	
	private String value; // ¾ßÌåµ¥´Ê

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return "(" + value + ", " + type + ")";
	}
}
