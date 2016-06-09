package entity;

public enum Role {
	DEFAULT("default"),
	ADMIN("admin"),
	STUDENT("student"),
	TEACHER("teacher"),
	TUITOR("tuitor"),
	CHARGE("charge"),
	PRIME("prime");
	
	private static String[] info = {"无", "系统管理员", "学生", "教师", "助教", "院系负责人", "总负责人"};
	
	public static Role getInstance(String n) {
		try {
		 return Role.valueOf(n.toUpperCase());
		} catch (Exception e) {return Role.DEFAULT;}
	}
	
	
	private Role(String str) {
		this.setStr(str);
	}
	
	public String getInfo() {
		return info[this.ordinal()];
	}
	
	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	private String str;
}
