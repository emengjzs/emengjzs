package filechecker;

public class FileType {
	private String type;
	private String character_code;

	public String getCharacterCode() {
		return character_code;
	}

	public void setCharacterCode(String charCode) {
		this.character_code = charCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public boolean checkCharacterCode(String code) {
		System.out.println(code +" " + character_code);
		return this.character_code.equals(code.toUpperCase());
	}
	
	public int getCharacterCodeLen() {
		return this.character_code.length() / 2;
	}
}
