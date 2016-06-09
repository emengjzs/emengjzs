package lex;



public class Pattern implements Comparable<Pattern>{
	private String re;
	private String type;
	private int priority;
	
	public Pattern(String re, String type, int priority) {
		this.re = re;
		this.type = type;
		this.priority = priority;
	}
	
	public String getRe() {
		return re;
	}
	
	public String getType() {
		return type;
	}

	@Override
	public int compareTo(Pattern arg0) {
		return new Integer(this.priority).compareTo(arg0.priority); 
	}
	
	public boolean isSimple() {
		return this.re.length() == 1 || (this.re.startsWith("\\") && this.re.length() == 2);
	}
	
	public String toString() {
		return "RE: " + this.re + "\tTYPE: " + this.type;
	}
}
