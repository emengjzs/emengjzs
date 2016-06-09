package entity;

import java.util.ArrayList;

public enum Institute {
	SOFTWARE("���ѧԺ"),
	MATH("��ѧϵ"),
	ELEC("����ϵ"),
	PYHISCS("����ѧԺ");
	
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
