package game.model;

import java.util.Random;

public class RandomBase_Stub {
	private static Random random = new Random();

	// prosibility 概率百分比， prosibility = 60 则概率为60%
	// 以该概率大小判定，成功则返回true，否则返回false
	public static boolean get(double prosibility) {
		double result = random.nextDouble();
		return (result < prosibility / 100.0);
	}

	public int getRandomNum(int start, int end) {
		double result = random.nextDouble();
		return (int) ((end - start + 1) * result + start);
		// [0,3] [0,1)->0 [1,2)->1 [2,3)->2 [3,4)->3
	}

	public static int getNextInt(int end) {
		return random.nextInt(end);
	}


}