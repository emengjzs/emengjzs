package gamemodelservice;

/**逻辑层提供接口**/
public interface ModelService {

	/**
	 * 返回字符串格式为 "1,2,3,4,5,6, "
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
