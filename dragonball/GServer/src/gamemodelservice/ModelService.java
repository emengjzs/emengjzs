package gamemodelservice;

/**�߼����ṩ�ӿ�**/
public interface ModelService {

	/**
	 * �����ַ�����ʽΪ "1,2,3,4,5,6, "
	 */
	public String initGame(String item);

	public String swap(String twoBlock);

	public String triggerItem(String message);

	public String animateFinish();

	public String getTip();

	public String startSuper();

	public String endSuper();

	public String triggerAllItem();

	public int getScore();

}
