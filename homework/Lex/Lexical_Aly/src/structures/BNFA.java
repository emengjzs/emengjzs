package structures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import debug.Debug;
import basic.Vertex;

public class BNFA extends NFA {
	
	
	protected ArrayList<Vertex<Integer>> end = new ArrayList<Vertex<Integer>>();
	

	public void setEnd(Vertex<Integer> vertex) {
		end.add(vertex);
	}
	
		
	protected String getName() {
		return "BNFA";
	}
	
	public void show() {
		Debug.print("-------------" + getName() + "--------------");
		showGraph();
		Debug.print("------------------------------");
		Debug.print("开始态\t：" + this.start.getData().toString());
		
		System.out.print("结束态\t：");
		for(Vertex<Integer> v : end) {
			System.out.print(v.getData() + " ");
		}
		Debug.print("\n边数\t: " + getSizeOfEdge());
		Debug.print("顶点数\t: " + getSizeOfVertex());
		Debug.print("------------------------------");
	}
	
	
	public ArrayList<Vertex<Integer>> getAllEndVertex() {
		return this.end;
	}
	
	public Vertex<Integer> initEnd() {
		Vertex<Integer> e = this.insertVertex(registVertexID());
		end.add(e);
		return e;
	}
	
	public boolean isEnd(Vertex<Integer> v) {
		return this.end.contains(v);
	}
	
	public boolean hasEnd(Set<Vertex<Integer>> vertexSet) {
		Iterator<Vertex<Integer>> itr = end.iterator();
		while(itr.hasNext()) {
			if(vertexSet.contains(itr.next())) {
				return true;
			}
		}
		return false;
	}
	
	public void addAllEnd(NFA nfa) {
		if(nfa instanceof BNFA) {
			this.end.addAll(((BNFA) nfa).getAllEndVertex());
		}
		else {
			this.end.add(nfa.getEndVertex());
		}
		
	}
	
}
