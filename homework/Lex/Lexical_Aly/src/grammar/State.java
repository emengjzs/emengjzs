package grammar;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class State {
	private Map<String, Action> actionList = new TreeMap<String, Action>();
	private Map<String, Integer> gotoList = new HashMap<String, Integer>();
	
	public void addAction(Terminal t, Action a) {
		this.actionList.put(t.getSign(), a);
	}
	
	public void addGOTO(Nonterminal t, Integer i) {
		this.gotoList.put(t.getSign(), i);
	}
	
	public Action getAction(Terminal t) {
		Action index = this.actionList.get(t.sign);
		if(index != null) {
			return index;
		}
		return new Action();
	}
	
	public Integer getGOTO(Nonterminal s) {
		return this.gotoList.get(s.getSign());
	}


	
}
