package model.IO;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class IOHelper {
	/**
	 * inputFromConsole�������ڴ��ݴӿ���̨�����String
	 * 
	 * @return �����String
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
