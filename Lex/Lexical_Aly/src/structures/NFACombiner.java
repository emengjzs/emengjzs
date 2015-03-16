package structures;

import java.util.Stack;

import basic.Vertex;

/**
 * 将多个正则对应的NFA合并
 * @author emengjzs
 *
 */
public class NFACombiner {
	
	private Stack<NFA> NFAList = new Stack<NFA>();
	
	private EndStatePatternSearcher endStateSearcher;
	
	private NFA result;
	
	public NFACombiner() {
		endStateSearcher = new EndStatePatternSearcher();
	}
	
	public void add(NFA nfa) {
		endStateSearcher.registEndState(nfa.getEndVertex(), nfa.getPattern());
		if(result == null) {
			result = nfa;
		}
		else {
			NFAList.push(nfa);
		}
	}
	
	public NFA combine() {
		if(NFAList.isEmpty() || result == null) 	return result;	
		
		while(! NFAList.isEmpty()) {
			NFA right = NFAList.pop();
			combine(right);
		}		
		return this.result;
	}
	
	
	private void combine(NFA right) {
		BNFA new_nfa = new BNFA();
		Vertex<Integer> start = new_nfa.initStart();	
		new_nfa.insertGraph(result);
		new_nfa.insertGraph(right);
		new_nfa.insertNullEdge(start, result.getStartVertex());
		new_nfa.insertNullEdge(start, right.getStartVertex());
		new_nfa.addAllEnd(result);
		new_nfa.addAllEnd(right);
		this.result = new_nfa;
	}
	
	public EndStatePatternSearcher getEndStateSearcher() {
		return this.endStateSearcher;
	}
	
}
