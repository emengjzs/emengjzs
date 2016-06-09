package structures;

import java.util.Set;

import basic.Vertex;

/**
 * 实现子集构造的三个重要操作接口
 * @author emengjzs
 *
 */
public interface SubsetConstructionOperations {

	/**
	 * ε-closure(s)
	 * @param s
	 * @return Set<Vertex<Integer>>
	 */
	public abstract Set<Vertex<Integer>> e_closure(Vertex<Integer> s);

	/**
	 * ε-closure(T)
	 * @param T
	 * @return Set<Vertex<Integer>>
	 */
	public abstract Set<Vertex<Integer>> e_closure(Set<Vertex<Integer>> T);
	
	
	public abstract Set<Vertex<Integer>> move(Set<Vertex<Integer>> T,
			Character a);

}