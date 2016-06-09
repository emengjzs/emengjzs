package structures;

import java.util.Set;

import basic.Vertex;

/**
 * ʵ���Ӽ������������Ҫ�����ӿ�
 * @author emengjzs
 *
 */
public interface SubsetConstructionOperations {

	/**
	 * ��-closure(s)
	 * @param s
	 * @return Set<Vertex<Integer>>
	 */
	public abstract Set<Vertex<Integer>> e_closure(Vertex<Integer> s);

	/**
	 * ��-closure(T)
	 * @param T
	 * @return Set<Vertex<Integer>>
	 */
	public abstract Set<Vertex<Integer>> e_closure(Set<Vertex<Integer>> T);
	
	
	public abstract Set<Vertex<Integer>> move(Set<Vertex<Integer>> T,
			Character a);

}