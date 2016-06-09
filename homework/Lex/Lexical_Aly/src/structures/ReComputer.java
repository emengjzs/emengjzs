package structures;
import java.util.Iterator;

import basic.Edge;
import basic.Vertex;

/**
 * ’˝‘Ú‘ÀÀ„∆˜
 * @author emengjzs
 *
 */
public class ReComputer {
	
	public NFA OR(NFA left, NFA right) {
		NFA new_nfa = new NFA();
		Vertex<Integer> start = new_nfa.initStart();
		Vertex<Integer> end = new_nfa.initEnd();
		new_nfa.insertGraph(left);
		new_nfa.insertGraph(right);
		new_nfa.insertNullEdge(start, left.getStartVertex());
		new_nfa.insertNullEdge(start, right.getStartVertex());
		new_nfa.insertNullEdge(left.getEndVertex(), end);
		new_nfa.insertNullEdge(right.getEndVertex(), end);
		return new_nfa;
	}
	
	public NFA CONCAT(NFA left, NFA right) {
		NFA new_nfa = left;
		Vertex<Integer> left_end = left.getEndVertex();
		Iterator<Edge> right_start_itr = right.getStartVertex().getOutEdgeIterator();
		while(right_start_itr.hasNext()) {
			Edge<Character> e = right_start_itr.next();
			Vertex<Integer> to = e.isSelfLoop() ? left_end : e.getDestination();
			new_nfa.insertEdge(e.getData(), left_end, to);
		}
		right.deleteVertex(right.getStartVertex());
		new_nfa.insertGraph(right);		
		new_nfa.setEnd(right.getEndVertex());
		return new_nfa;
	}
	
	public NFA STAR(NFA one) {
		NFA new_nfa = new NFA();
		Vertex<Integer> start = new_nfa.initStart();
		Vertex<Integer> end = new_nfa.initEnd();
 		new_nfa.insertGraph(one);
		new_nfa.insertNullEdge(start, one.getStartVertex());
		new_nfa.insertNullEdge(one.getEndVertex(), one.getStartVertex());
		new_nfa.insertNullEdge(one.getEndVertex(), end);
		new_nfa.insertNullEdge(start, end);
		return new_nfa;
	}
}
