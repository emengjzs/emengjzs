package structures;

public enum ReOperator {
	/**
	 * 连接
	 */
	CONCAT,
	
	/**
	 * 或
	 */
	OR,
	
	/**
	 * 星号
	 */
	STAR,
	
	/**
	 * 左括号
	 */
	LEFT_BRACKET,
	
	/**
	 * 右括号
	 */
	RIGHT_BRACKET,
	
	;		
	private static char[] 		SIGN_MAP 	 = {'.', 	'|', 	'*', 	'(', 	')'  };
	private static boolean[]	RE_END_TAG	 = {false, 	false, 	true, 	false, true };
	private static boolean[]	RE_START_TAG = {false, 	false, false, 	true, 	false};
	private static int[] 		ISP 		 = {5, 3, 7, 1, 8};
	private static int[]		ICP			 = {4, 2, 6, 8, 1};
	
	
	public static boolean isOperator(char ch) {
		return getOperatorType(ch) != null;
	}
	
	
	public static ReOperator getOperatorType(char ch) {
		int length = SIGN_MAP.length;
		for(int i = 0; i < length; ++i) {
			if(ch == SIGN_MAP[i]) {
				return ReOperator.values()[i];
			}
		}	
		return null;		
	}
	
	public char toChar() {
		return SIGN_MAP[this.ordinal()];
	}
	
	public String toString() {
		return String.valueOf(SIGN_MAP[this.ordinal()]);
	}
	
	/**
	 * 一个子正则表达式是否可以此字符结尾
	 * @return boolean 
	 */
	public boolean canBeEndOfRe() {
		return RE_END_TAG[this.ordinal()];
	}
	
	/**
	 * 一个子正则表达式是否可以此字符开头
	 * @return boolean 
	 */
	public boolean canBeStartOfRe() {
		return RE_START_TAG[this.ordinal()];
	}
	
	public int isp() {
		return ISP[this.ordinal()];
	}
	
	public int icp() {
		return ICP[this.ordinal()];
	}
}
