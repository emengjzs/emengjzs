package basic;


/**
 * ͼ��
 * @author emengjzs
 *
 * @param <E> ���ϵĸ����Զ�������
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
	 * �Ƿ������γɻ�
	 * @return
	 */
	public boolean isSelfLoop() {
		return source == destination;
	}
	
	/**
	 * ���ϵ�����E�Ƿ�Ϊ��
	 * @return
	 */
	public boolean isNull() {
		return e == null;
	}
	
	public E getData() {
		return this.e;
	}
}