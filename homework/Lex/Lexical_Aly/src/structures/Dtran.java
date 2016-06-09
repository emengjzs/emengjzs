package structures;

/**
 * 转换表抽象接口
 * @author emengjzs
 *
 */
public interface Dtran {
	
	/**
	 * 开始搜索/复位
	 */
	public void searchStart();
	
	/**
	 * 是否已在终态
	 * @return
	 */
	public boolean reachEnd();
	
	/**
	 * 根据终态表得出Token
	 * @param esps
	 * @return
	 */
	public String getTokenType(EndStatePatternSearcher esps);
	
	/**
	 * 是否能够根据字符c找到下一状态
	 * @param c
	 * @return
	 */
	public boolean findNext(Character c);
}