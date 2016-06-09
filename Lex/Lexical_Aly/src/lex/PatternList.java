package lex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PatternList {
	public static String RESERVED[][] ={};
	
	private ArrayList<Pattern> patternlist = new ArrayList<Pattern>();
	
	
	private static Set<String> UNIT_SET = new HashSet<String>(16);
	
	
	public PatternList() {
		for(int i = 0; i < RESERVED.length; i ++) {
			addPattern(RESERVED[i][0], RESERVED[i][1]);
		}
	}
	
	private boolean registUnit(String uint) {
		return UNIT_SET.add(uint);
	}
	
	public String getRe(String type) {
		Iterator<Pattern> itr = this.patternlist.iterator();
		Pattern p;
		while(itr.hasNext()) {
			if((p = itr.next()).getType().equals(type)) {
				return p.getRe();
			}
		}
		return null;
	}
	
	public boolean addPattern(String re, String type) {
		return this.addPattern(re, type, 0);
	}
	
	public boolean addPattern(String re, String type, int priority) {
		boolean result = registUnit(type); 
		if(result) {
			patternlist.add(new Pattern(re, type, priority));
		}
		return result;
	}
	
	
	public Iterator<Pattern> getPatternIterator() {
		return this.patternlist.iterator();
	}
	
	public void print() {
		for(Pattern p : patternlist) {
			System.out.println(p.toString());
		}
	}
	
}
