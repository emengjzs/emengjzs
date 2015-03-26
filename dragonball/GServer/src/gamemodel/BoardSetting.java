package gamemodel;

import gamemodel.block.Block.TYPE;


public class BoardSetting {
	public static int length = 9;									//��
	public static int height = 9;									//��	
	public static TYPE type[] = {TYPE.PURPLE, 
								  TYPE.GREEN, 
								  TYPE.RED,
								  TYPE.YELLOW, 
								  TYPE.BLUE				  
								  };								//�����ɵķ�������
	public static char ch[] = {'��','��', '��' ,'��','��','��'};		//ͼ��,�Լ�ѡ���
	public static int typeNum = type.length;						//�����ɵķ���������
	public final static int MATCH_NUM = 3; 						//������
	
	public static final double SUPER_MODE_RATE 	= 2.0;			//����ģʽ��������
	public static final double ITEM_RATE 			= 1.1;			//����Ч����������
	
	public static final int THREE_SCORE 	= 100;					//��������
	public static final int FOUR_SCORE 	= 200;					//��������
	public static final int FIVE_SCORE 	= 500;					//��������
	public static final int A_ITEM_SCORE 	= 200;					//����A��������
	public static final int B_ITEM_SCORE 	= 800;					//����B��������
	
	
	
	public Direction fallDirection = Direction.DOWN;				//���䷽��
}
