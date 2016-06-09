package structures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

import lex.Pattern;
import basic.DirectGraph;
import basic.Edge;
import basic.Vertex;
import debug.Debug;

public class NFA extends DirectGraph<Integer, Character> 
				  implements SubsetConstructionOperations 
				  {
	
	private static int vertex_id = 0;
	
	protected Vertex<Integer> start;
	
	private Vertex<Integer> end;
	
	private Pattern pattern;
	
	public NFA() {
		super();
	}
	
	public void setPattern(Pattern p) {
		this.pattern = p;
	}
	
	public String getRe() {
		return pattern == null ? "Null" : this.pattern.getRe();
	}
	
	public String getType() {
		return pattern == null ? "Null" : this.pattern.getType();
	}
	
	public NFA(Character e) {
		super();
		start = this.insertVertex(registVertexID());
		end = this.insertVertex(registVertexID());
		this.insertEdge(e, start, end);
	}
	
	protected int registVertexID() {
		return vertex_id++;
	}
	
	public Edge<Character> insertNullEdge(Vertex<Integer> from, Vertex<Integer> to) {
		return insertEdge(null, from, to);
		
	}
	
	public Vertex<Integer> initStart() {
		start = this.insertVertex(registVertexID());
		return start;
	}
	
	public Vertex<Integer> initEnd() {
		end = this.insertVertex(registVertexID());
		return end;
	}
	
	public void setStart(Vertex<Integer> vertex) {
		start = vertex;
	}
	
	public void setEnd(Vertex<Integer> vertex) {
		end = vertex;
	}
	
	public Vertex<Integer> getStartVertex() {
		return start;
	}
	
	public Vertex<Integer> getEndVertex() {
		return end;
	}
	
	protected String getName() {
		return "NFA";
	}
	
	public boolean isEnd(Vertex<Integer> vertex) {
		return this.end != null && this.end == vertex;
	}
	
	public boolean hasEnd(Set<Vertex<Integer>> vertexSet) {
		return vertexSet.contains(end);
	}
	
	
	public void show() {
		Debug.print("-------------" + getName() + "--------------");
		showGraph();
		Debug.print("------------------------------");
		if(this.pattern != null) {
			Debug.print("正则\t：" + this.getRe());
			Debug.print("类型\t：" + this.getType());
		}
		Debug.print("开始态\t：" + this.start.getData().toString());
		Debug.print("结束态\t：" + this.end.getData().toString());
		Debug.print("边数\t: " + getSizeOfEdge());
		Debug.print("顶点数\t: " + getSizeOfVertex());
		Debug.print("------------------------------");
	}
	
	
	public Set<Vertex<Integer>> e_move(Vertex<Integer> s) {	
		Set<Vertex<Integer>> states = new HashSet<Vertex<Integer>>();
		Iterator<Edge> outEdgeItr = s.getOutEdgeIterator();
		while(outEdgeItr.hasNext()) {
			Edge e = outEdgeItr.next();
			if(e.isNull()) {
				states.add(e.getDestination());
			}
		}
		return states;		
	}
	
	
	@SuppressWarnings("unchecked")
	private Set<Vertex<Integer>> move(Vertex<Integer> s, Character a) {
		
		Set<Vertex<Integer>> states = new HashSet<Vertex<Integer>>();
		Iterator<Edge> outEdgeItr = s.getOutEdgeIterator();
		while(outEdgeItr.hasNext()) {
			Edge e = outEdgeItr.next();
			if((!e.isNull()) && a.equals(e.getData())) {
				// Debug.print("Vertex " + s.getData() + " can move to " + e.getDestination().getData() +" by " + a);
				states.add((Vertex<Integer>) e.getDestination());
			}
		}
		return states;		
	}
	
	
	
	/* （非 Javadoc）
	 * @see structures.SubsetConstructionOperations#e_closure(java.util.Set)
	 */
	@Override
	public Set<Vertex<Integer>> e_closure(Set<Vertex<Integer>> T) {
		Stack<Vertex<Integer>> vertexStack = new Stack<Vertex<Integer>>();
		
		Set<Vertex<Integer>> closureSet = new HashSet<Vertex<Integer>>();
		closureSet.addAll(T);
		
		Iterator<Vertex<Integer>> tItr = T.iterator();
		while(tItr.hasNext()) {
			Vertex<Integer> v = tItr.next();
			vertexStack.push(v);
		}
		
		Vertex<Integer> from;
		Vertex<Integer> to;
		while(! vertexStack.isEmpty()) {
			from = vertexStack.pop();
			Set<Vertex<Integer>> states = this.e_move(from);
			
			Iterator<Vertex<Integer>> stateItr = states.iterator();
			while(stateItr.hasNext()) {
				to = stateItr.next();
				if(! closureSet.contains(to)) {
					closureSet.add(to);
					vertexStack.push(to);
				}
			}
		}
		
		return closureSet;	
	}
	
	/* （非 Javadoc）
	 * @see structures.SubsetConstructionOperations#move(java.util.Set, java.lang.Character)
	 */
	@Override
	public Set<Vertex<Integer>> move(Set<Vertex<Integer>> T, Character a) {
		Set<Vertex<Integer>> closureSet = new HashSet<Vertex<Integer>>();
		Iterator<Vertex<Integer>> tItr = T.iterator();
		while(tItr.hasNext()) {
			Vertex<Integer> v = tItr.next();
			closureSet.addAll(move(v, a));
		}		
		return closureSet;		
	}

	@Override
	public Set<Vertex<Integer>> e_closure(Vertex<Integer> s) {
		Set<Vertex<Integer>> T = new HashSet<Vertex<Integer>>();
		T.add(s);
		Set<Vertex<Integer>> result = e_closure(T);
		result.add(s);
		return result;
	}
	
	public Pattern getPattern() {
		return this.pattern;
	}
}
