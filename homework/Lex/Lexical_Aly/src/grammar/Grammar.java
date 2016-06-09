package grammar;

import java.util.ArrayList;

public class Grammar {
	private Nonterminal head;
	String rawStr;
	ArrayList<String> production;
	
	public Grammar(String str) {
		StringBuilder sb = new StringBuilder();
		String args[] = str.split("->");
		head = new Nonterminal(args[0]);
		sb.append(getHead().sign + "->");
		production = new ArrayList<String>();
		String pl[] = args[1].split(" ");
		for(String t : pl) {
			production.add(t);
			sb.append(t);
		}
		this.rawStr = str;
	}
	
	public int length() {
		return this.production.size();
	}
	
	public String toString() {
		return this.rawStr;
	}

	public Nonterminal getHead() {
		return head;
	}

	
}
