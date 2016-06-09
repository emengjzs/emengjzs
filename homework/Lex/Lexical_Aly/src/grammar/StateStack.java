package grammar;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class StateStack<T> {
	private Stack<T> stateStack;
	private int max;
	
	public StateStack() {
		this.stateStack = new Stack<T>();
		max = 0;
	}
	/*判断栈空*/
	public boolean isEmpty() {
		return stateStack.empty();
	}
	
	public void clear() {
		this.stateStack.clear();
	}
	
	public T getTop() {
		return this.stateStack.peek();
	}
	
	public void push(T i) {
		this.stateStack.push(i);
		++max;
	}
	
	public T pop() {
		return this.stateStack.pop();
	}
	/*连续出栈count次，返回列表*/
	public List<T> pop(int count) {
		if(this.stateStack.size() < count) {
			count = this.stateStack.size();
		}
		
		LinkedList<T> l = new LinkedList<T>();
		while(count-- > 0) {
			l.add(this.stateStack.pop());
		}
		return l;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(T i : this.stateStack) {
			sb.append(i);
			sb.append(' ');
		}
		return sb.toString();
	}
	public int size() {
		// TODO 自动生成的方法存根
		return this.stateStack.size();
	}
	
}
