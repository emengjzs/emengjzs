package game.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Debug {
	private static int DEBUG_MODE = 1;
	private static String cmd = null;
	private static BufferedReader br;

	/* 带提示的输入-命令行 */
	public static String input(String output) {
		Debug.output(output);
		return Debug.input();
	}

	/* 输入-cmd */
	public static String input() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			cmd = br.readLine();
		} catch (Exception e) {
		}
		return cmd;
	}

	public static boolean checkInvalidInput(String s) {
		if (s == null || s.trim().equals("")) {
			// Console.output(Massage.COURSE_NO_ID_ERROR.toString());
			return false;
		}
		return true;
	}

	// 输出message
	public static boolean output(String output) {
		if (DEBUG_MODE == 0)
			return false;
		System.out.print(output);
		return true;
	}
	
	public static void outputl(String output) {
		System.out.println(output);
	}
	
	public static void outputl(double output) {
		System.out.println(output);
	}
	
	public static void outputl(boolean output) {
		System.out.println(output);
	}

	public static void output(double d) {
		// TODO 自动生成的方法存根
		if (DEBUG_MODE == 0)
			return;
		System.out.print(d);
	}

	public static void output(boolean result) {
		if (DEBUG_MODE == 0)
			return;
		System.out.print(result);
	}

	public static void endLine() {
		System.out.println();
	}

	public static void output(int result) {
		if (DEBUG_MODE == 0)
			return;
		System.out.print(result);
	}

	public static void split() {
		outputl("======================");
	}

}
