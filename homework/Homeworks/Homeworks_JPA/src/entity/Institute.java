package entity;

import java.util.ArrayList;

public enum Institute {
	SOFTWARE("软件学院"),
	MATH("数学系"),
	ELEC("电子系"),
	PYHISCS("物理学院");
	
	String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String s) {
		this.name = s;
	}
	
	private Institute(String s) {
		this.name = s;
	}
	
	public static ArrayList<String> findAll() {
		ArrayList<String> str = new ArrayList<String>();
		Institute ins[] = Institute.values();
		for(Institute i : ins) {
			str.add(i.toString());
		}
		return str;
	}
	
	public String toString() {
		return this.name;
	}
}
