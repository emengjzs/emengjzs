package structures;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lex.Pattern;
import basic.Vertex;

public class DFAConstructor {
	private Dstates dstates;
	private NFA nfa;
	private DFA dfa;
	private EndStatePatternSearcher dfaESPSearcher;
	private EndStatePatternSearcher nfaESPSearcher;
	private Map<Integer, Vertex<Integer>> vertexMap;
	
	private List<Integer> endStateIDList; 
	
	public DFAConstructor() {
		dstates = new Dstates();
		
		endStateIDList = new LinkedList<Integer>();
	}
	
	public void setNFAESPSearcher(EndStatePatternSearcher nfaESPSearcher ) {
		this.nfaESPSearcher = nfaESPSearcher;
	}
	
	public DFA consrtuct(NFA nfa) {
		
		this.nfa = nfa;		
		init();
		
		Character ch;
		Set<Vertex<Integer>> U;
		int dfa_vertex_id;
		boolean hasEnd;		
		Vertex<Integer> from;
		
		while(dstates.hasUntagState()) {
			
			Set<Vertex<Integer>> state = dstates.getUntagState();
			from = vertexMap.get(dstates.getCurrentStateID());
			
			dstates.tagState(state);
			hasEnd = nfa.hasEnd(state);
			
			Set<Character> characterSet = getCharacterSet(state);		
			Iterator<Character> chItr = characterSet.iterator();		
			while(chItr.hasNext()) {
				ch = chItr.next();
				U = nfa.e_closure(nfa.move(state, ch));	// 闭包
				dfa_vertex_id = dstates.inState(U);		// 得到状态id
				// 若不在状态集中
				if(dfa_vertex_id == Dstates.INVALID_ID) {
					// printState(U);
					// 将U加入状态集并注册状态id
					dfa_vertex_id = dstates.addState(U);				
					// 向DFA加入点
					Vertex<Integer> vertex = dfa.insertVertex();			
					// 注册该点
					vertexMap.put(dfa_vertex_id, vertex);
				}
				
				Vertex<Integer> to = vertexMap.get(dfa_vertex_id);
				dfa.insertEdge(ch, from, to);			
			}
			
			if(hasEnd) {
				dfa.setEnd(from);
				endStateIDList.add(dstates.getCurrentStateID());
			}			
		}
		return dfa;
	}
	
	
	private void init() {
		dfa = new DFA();	
		Set<Vertex<Integer>> initState = nfa.e_closure(nfa.getStartVertex());
		vertexMap = new HashMap<Integer, Vertex<Integer>>();
		// printState(initState);
		int i0 = dstates.addState(initState);
		
		// 向DFA加入起点
		Vertex<Integer> start = dfa.initStart();
		// 注册该起点
		vertexMap.put(i0, start);
	}
	
	public EndStatePatternSearcher getDFAESPSearcher() {
		if(this.dfaESPSearcher == null)
			installPattern();
		return this.dfaESPSearcher;
	}
	
	/**
	 * 根据NFA终态表建造DFA终态表
	 * @param nfaESPSearcher
	 */
	private void installPattern() {
		if(this.nfaESPSearcher == null) return;
		dfaESPSearcher = new EndStatePatternSearcher();
		Iterator<Integer> endStateIDItr = this.endStateIDList.iterator();
		
		Vertex<Integer> endStateVertex;
		Vertex<Integer> v;
		Pattern p;
		while(endStateIDItr.hasNext()) {
			int id = endStateIDItr.next();
			endStateVertex = vertexMap.get(id);
			Set<Vertex<Integer>> state = this.dstates.getState(id);
			Iterator<Vertex<Integer>> itr = state.iterator();
			
			while(itr.hasNext()) {
				v = itr.next();
				p = nfaESPSearcher.getPattern(v);			
				if(p != null) {
					this.dfaESPSearcher.registEndState(endStateVertex, p);
				}
			}			
		}		
	}
	
	@SuppressWarnings("unchecked")
	private Set<Character> getCharacterSet(Vertex<Integer> v) {
		return new HashSet<Character>(v.outEdgeDataList());
	}
	
	private Set<Character> getCharacterSet(Set<Vertex<Integer>> state) {
		Set<Character> characterSet = new HashSet<Character>();
		Iterator<Vertex<Integer>> itr = state.iterator();
		Vertex<Integer> v;
		while(itr.hasNext()) {
			v = itr.next();
			characterSet.addAll(getCharacterSet(v));
		}
		return characterSet;
	}
	
	
	public static void printState(Set<Vertex<Integer>> state) {
		Iterator<Vertex<Integer>> itr = state.iterator();
		System.out.print("State: { ");
		Vertex<Integer> v;
		while(itr.hasNext()) {
			v = itr.next();
			System.out.print(v.getData() + " ");
		}
		System.out.print("}\n");
	}
	
	public static void printChar(Set<Character> state) {
		Iterator<Character> itr = state.iterator();
		System.out.print("[ ");
		while(itr.hasNext()) {
			System.out.print(itr.next() + ", ");
		}
		System.out.print(" ]\n");
	}
	
}
