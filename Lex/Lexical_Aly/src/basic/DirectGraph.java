package basic;
import java.util.Iterator;
import java.util.LinkedList;

import debug.Debug;


public class DirectGraph<V, E> {
	
	/*顶点集合链表*/
	protected LinkedList<Vertex<V>> vertexList;
	protected int numEdges = 0;
	
	
	public DirectGraph() {
		vertexList = new LinkedList<Vertex<V>>();
	}
	
	public DirectGraph(V v) {
		vertexList = new LinkedList<Vertex<V>>();
		Vertex<V> vertex = new Vertex<V>(v);
		vertexList.add(vertex);
	}
	
	public Vertex<V> insertVertex(V v) {
		Vertex<V> vertex = new Vertex<V>(v);
		vertexList.add(vertex);
		return vertex;
	}
	public void insertGraph(DirectGraph<V, E> g) {
		this.vertexList.addAll(g.vertexList);
		this.addnumEdges(g.getSizeOfEdge());
	}
	
	public Edge<E> insertEdge(E e, Vertex<V> from, Vertex<V> to) {
		Edge<E> edge = new Edge<E>(e, from, to);
		from.addOutEdge(edge);
		to.addInEdge(edge);
		this.addnumEdges(1);
		return edge;
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean deleteEdge(Edge<E> e) {
		Vertex<V> from = e.getSource();
		if (from != null) {
			from.removeOutEdge(e);
		}
		Vertex<V> to = e.getDestination();
		if (to != null) {
			to.removeInEdge(e);
		}
		this.addnumEdges(-1);
		return false;
	}
	
	public boolean deleteVertex(Vertex<V> v) {
		this.vertexList.remove(v);
		LinkedList<Edge> deleteEdgeList = new LinkedList<Edge>();
		deleteEdgeList.addAll(v.getInEdgeList());
		deleteEdgeList.addAll(v.getOutEdgeList());
		Iterator<Edge> itr = deleteEdgeList.iterator();
		Edge<E> e;
		while(itr.hasNext()) {
			e = itr.next();
			this.deleteEdge(e);
		}	
		return true;
	}
	
	/**得到顶点数*/
	public int getSizeOfVertex() {
		return vertexList.size();
	}
	
	/**得到边数*/
	public int getSizeOfEdge() {
		return numEdges;
	}
	
	
	public boolean isEmpty() {
		return this.getSizeOfVertex() == 0;
	}
	
	public boolean isAlone() {
		return this.getSizeOfVertex() == 1;
	}
	
	protected void addnumEdges(int n) {
		this.numEdges += n;
	}
	
	public Iterator<Vertex<V>> getVertexIterator() {
		return this.vertexList.iterator();
	}
	
	public void showGraph() {
		Iterator<Vertex<V>> itr = getVertexIterator();
		while(itr.hasNext()) {
			Vertex<V> v = itr.next();
			Iterator<Edge> it = v.getOutEdgeIterator();
			while(it.hasNext()) {
				Edge e = it.next();
				Debug.print(e.getSource().getData() + "-> (" + e.getData() + ") -> " + e.getDestination().getData());
			}
		}
	}
	
	
}
