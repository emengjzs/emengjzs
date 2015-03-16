package grammar;

public class Action {
	public static enum Type {
		MOVE,
		PRODUCE,
		ACCEPT,
		ERROR
		
	}
	
	private static String[] chs = {"移入", "规约", "接受", "错误"};
	
	private Type type;
	private Integer index;
	
	public Action() {
		this.setIndex(-1);
		this.type = Type.ERROR;
	}
	
	public Action(Type t, Integer i) {
		this.type = t;
		this.setIndex(i);
	}
	
	public String toString() {
		return chs[this.type.ordinal()];
	}
	
	public Type getType() {
		return this.type;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
