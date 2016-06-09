package model.IO;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class IOHelper {
	/**
	 * inputFromConsole方法用于传递从控制台输入的String
	 * 
	 * @return 输入的String
	 */
	public static String inputFromConsole(String info) {
		System.out.println(info);
		
		String input = null;
		BufferedReader br;

		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			input = br.readLine();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return input;
	}
}
