package grammar;

import debug.Debug;


public class Nonterminal extends Sign{
	public Nonterminal(String type) {
		this.sign = type;
	}
	
	public boolean equals(Sign s) {
		return s instanceof Nonterminal && s.sign.equals(sign);
	}
	
}
