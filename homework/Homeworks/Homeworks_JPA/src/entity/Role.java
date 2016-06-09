package entity;

public enum Role {
	DEFAULT("default"),
	ADMIN("admin"),
	STUDENT("student"),
	TEACHER("teacher"),
	TUITOR("tuitor"),
	CHARGE("charge"),
	PRIME("prime");
	
	private static String[] info = {"��", "ϵͳ����Ա", "ѧ��", "��ʦ", "����", "Ժϵ������", "�ܸ�����"};
	
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
