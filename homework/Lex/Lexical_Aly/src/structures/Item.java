package structures;

public class Item {
	private Object item;
	
	public Item(Object item) {
		this.item = item;
	}
	
	public boolean isOperand() {
		return ! isOperator();
	}
	
	public boolean isOperator() {
		return item instanceof ReOperator;
	}
	
	public Object get() {
		return item;
	}
	
	public String toString() {
		return item.toString();
	}
}
