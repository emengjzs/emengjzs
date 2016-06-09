package basic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**顶点*/
public class Vertex<V> {
	
	/**点数据*/
	private V v;
	/**由顶点出发的所有边组成链表*/
	private LinkedList<Edge> outEdgeList = new LinkedList<Edge>();
	private LinkedList<Edge> inEdgeList = new LinkedList<Edge>();
	
	public Vertex(V v) {
		this.v = v;
	}
	
	void addOutEdge(Edge edge) {
		outEdgeList.addLast(edge);
	}
	
	void removeOutEdge(Edge edge) {
		outEdgeList.remove(edge);
	}
	
	void removeAllOutEdge() {
		outEdgeList.clear();
	}
	
	void removeAllInEdge() {
		inEdgeList.clear();
	}
	
	LinkedList<Edge> getOutEdgeList() {
		return this.outEdgeList;
	}
	
	
	LinkedList<Edge> getInEdgeList() {
		return this.inEdgeList;
	}
	
	void addInEdge(Edge edge) {
		inEdgeList.addLast(edge);
	}
	
	void removeInEdge(Edge edge) {
		inEdgeList.remove(edge);
	}
	
	public V getData() {
		return this.v;
	}
	
	public Iterator<Edge> getOutEdgeIterator() {
		return this.outEdgeList.iterator();
	}
	
	public Iterator<Edge> getInEdgeIterator() {
		return this.inEdgeList.iterator();
	}
	
	public boolean hasOutEdge() {
		return ! this.outEdgeList.isEmpty();
	}
	
	public boolean hasInEdge() {
		return ! this.inEdgeList.isEmpty();
	}
	
	public ArrayList outEdgeDataList() {
		ArrayList dataList = new ArrayList(); 
		Iterator<Edge> itr = this.getOutEdgeIterator();
		Edge e;
		while(itr.hasNext()) {
			e = itr.next();
			if(! e.isNull())
				dataList.add(e.getData());
		}
		return dataList;
	}
	
	
}