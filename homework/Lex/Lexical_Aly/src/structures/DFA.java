package structures;

import java.util.ArrayList;
import java.util.Iterator;

import basic.Edge;
import basic.Vertex;
import debug.Debug;

public class DFA extends NFA implements Dtran{
	
	private static int vertex_id = 0;
	
	private Vertex<Integer> ptr;
	
	protected ArrayList<Vertex<Integer>> end = new ArrayList<Vertex<Integer>>();
	
	
	protected int registVertexID() {
		return vertex_id++;
	}
	
	public void setEnd(Vertex<Integer> vertex) {
		end.add(vertex);
	}
	
	public Vertex<Integer> insertVertex() {
		return super.insertVertex(this.registVertexID());
	}
	
	public Vertex<Integer> initEnd() {
		Vertex<Integer> e = super.initEnd();
		this.end.add(e);
		return e;
	}
	
	protected String getName() {
		return "DFA";
	}
	
	
	public void show() {
		Debug.print("-------------" + getName() + "--------------");
		//showGraph();
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
	
	public ArrayList<Vertex<Integer>> getEndVertexs() {
		return this.end;
	}
	
	/* （非 Javadoc）
	 * @see structures.Dtran#searchStart()
	 */
	public void searchStart() {
		this.ptr = this.getStartVertex();
	}
	
	public boolean isEnd(Vertex<Integer> v) {
		return this.end.contains(v);
	}
	
	public boolean findNext(Character c) {
		Iterator<Edge> itr = this.ptr.getOutEdgeIterator();
		Edge<Character> e;
		while(itr.hasNext()) {
			e = itr.next();
			if((e.getData()).equals(c)) {
				this.ptr = e.getDestination();
				return true;
			}
		}
		return false;
	}
	
	/* （非 Javadoc）
	 * @see structures.Dtran#reachEnd()
	 */
	public boolean reachEnd() {
		return this.isEnd(ptr);
	}
	
	/* （非 Javadoc）
	 * @see structures.Dtran#getTokenType(structures.EndStatePatternSearcher)
	 */
	public String getTokenType(EndStatePatternSearcher esps) {
		return esps.getPattern(ptr).getType();
	}
}
