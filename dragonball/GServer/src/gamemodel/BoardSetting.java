package gamemodel;

import gamemodel.block.Block.TYPE;


public class BoardSetting {
	public static int length = 9;									//列
	public static int height = 9;									//行	
	public static TYPE type[] = {TYPE.PURPLE, 
								  TYPE.GREEN, 
								  TYPE.RED,
								  TYPE.YELLOW, 
								  TYPE.BLUE				  
								  };								//可生成的方块种类
	public static char ch[] = {'●','○', '▲' ,'△','◆','◇'};		//图案,自己选择吧
	public static int typeNum = type.length;						//可生成的方块种类数
	public final static int MATCH_NUM = 3; 						//消除数
	
	public static final double SUPER_MODE_RATE 	= 2.0;			//超级模式分数倍率
	public static final double ITEM_RATE 			= 1.1;			//道具效果分数倍率
	
	public static final int THREE_SCORE 	= 100;					//三消分数
	public static final int FOUR_SCORE 	= 200;					//四消分数
	public static final int FIVE_SCORE 	= 500;					//五消分数
	public static final int A_ITEM_SCORE 	= 200;					//道具A消除分数
	public static final int B_ITEM_SCORE 	= 800;					//道具B消除分数
	
	
	
	public Direction fallDirection = Direction.DOWN;				//下落方向
}
