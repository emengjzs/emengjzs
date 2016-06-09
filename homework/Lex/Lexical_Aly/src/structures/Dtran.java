package structures;

/**
 * ת�������ӿ�
 * @author emengjzs
 *
 */
public interface Dtran {
	
	/**
	 * ��ʼ����/��λ
	 */
	public void searchStart();
	
	/**
	 * �Ƿ�������̬
	 * @return
	 */
	public boolean reachEnd();
	
	/**
	 * ������̬��ó�Token
	 * @param esps
	 * @return
	 */
	public String getTokenType(EndStatePatternSearcher esps);
	
	/**
	 * �Ƿ��ܹ������ַ�c�ҵ���һ״̬
	 * @param c
	 * @return
	 */
	public boolean findNext(Character c);
}