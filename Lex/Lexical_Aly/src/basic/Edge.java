package basic;


/**
 * 图边
 * @author emengjzs
 *
 * @param <E> 边上的附带自定义数据
 */
public class Edge<E> {
	private E e;
	private Vertex source;
	private Vertex destination;
	
	public Edge(E e, Vertex from, Vertex to) {
		this.e = e;			
		this.source = from;
		this.destination = to;
	}
	
	
	public Vertex getDestination() {
		return this.destination;
	}
	
	public Vertex getSource() {
		return this.source;
	}
	
	/**
	 * 是否自身形成环
	 * @return
	 */
	public boolean isSelfLoop() {
		return source == destination;
	}
	
	/**
	 * 边上的数据E是否为空
	 * @return
	 */
	public boolean isNull() {
		return e == null;
	}
	
	public E getData() {
		return this.e;
	}
}