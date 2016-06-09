package model;

import java.util.Random;

public class RandomBase {
	private static Random random = new Random();
	private int lastInt = -1;
	private int curInt = -1;
	private int count = 0;
	private final int maxSuccess = 3;
	
	// prosibility ���ʰٷֱȣ� prosibility = 60 �����Ϊ60%
	// �Ըø��ʴ�С�ж����ɹ��򷵻�true�����򷵻�false
	public static boolean get(double prosibility) {
		double result = random.nextDouble();
		return (result < prosibility / 100.0);
	}

	public int getRandomNum(int start, int end) {
		double result = random.nextDouble();
		return (int) ((end - start + 1) * result + start);
		// [0,3] [0,1)->0 [1,2)->1 [2,3)->2 [3,4)->3
	}

	public int getNextInt(int end) {
		
		curInt =  random.nextInt(end);
		if(curInt != lastInt) {
			lastInt = curInt;
			count = 1;
		}
		else {
			count ++;
			if(count > maxSuccess) {
				do {
					lastInt =  random.nextInt(end);
				} while(lastInt == curInt);
				count = 1;
			}		
		}
		return lastInt;		
		
	}


}