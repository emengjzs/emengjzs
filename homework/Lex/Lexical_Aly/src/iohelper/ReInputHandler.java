package iohelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import lex.PatternList;
import debug.Debug;

public class ReInputHandler {
	private static String path = "..\\Input\\re.txt";
	private static String KEY_WORDS = "KEY_WORDS";
	private PatternList patternlist = new PatternList();;
	private BufferedReader br;

	public void open() {
		try {
			br = new BufferedReader(new FileReader(new File(path)));
		} catch (IOException e) {
			Debug.print("Error: File Not Found.", e);
		}
	}

	public static void main(String[] args) {
		ReInputHandler r = new ReInputHandler();
		r.open();
		try {
			r.getPatternList();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			Debug.print(e.getMessage());
		}
	}

	private void getKeyWords(String s) throws Exception {
		String[] args = s.split(" ");
		int len = args.length;
		boolean result;
		for (int i = 1; i < len; ++i) {
			result = this.patternlist.addPattern(args[i],
					args[i].toUpperCase(), 65535);
			if (!result) {
				Debug.print("重复定义" + args[i] + "!");
				throw new Exception("定义错误。请检查re.txt语法是否正确");
			}
		}
		// this.patternlist.print();
	}

	private void getRe(String s) throws Exception {
		String[] args = s.split(" ");
		int len = args.length;
		boolean result = true;
		if (len == 2) {
			result = this.patternlist.addPattern(args[1], args[0], 0);
		} else if (len == 3) {
			result = this.patternlist.addPattern(args[1], args[0],
					Integer.parseInt(args[2]));
		}
		else if(len > 3) {
			int priority = 0;
			String re = args[1];
			int use = 0;
			for(int i = 2; i < 4; i++) {
				if(args[i].equals("use"))
					use = i;
			}
			if(use == 0)
				error();
			priority = use == 2 ? 0 : Integer.parseInt(args[2]);
			for(int i = use + 1; i < len ; ++ i) {
				String replace = this.patternlist.getRe(args[i]);
				if(re != null)
					re = replace(re, args[i], replace);
			}		
			result = this.patternlist.addPattern(re, args[0], priority);
		}
		if (!result) {
			Debug.print("重复定义" + args[1] + "!");
			error();
		}
	}

	private void error() throws Exception {
		throw new Exception("定义错误。请检查re.txt语法是否正确");
	}
	
	public PatternList getPatternList() throws Exception {
		
		String s;
		while ((s = br.readLine()) != null) {
			if (s.startsWith("//"))
				continue;
			if (s.startsWith(KEY_WORDS)) {
				getKeyWords(s);
			} else {
				getRe(s);
			}
		}
		this.patternlist.print();
		return this.patternlist;
	}

	private String replace(String strSource, String strFrom, String strTo) {
		if (strSource == null) 
			return null;
		int i = 0;
		if ((i = strSource.indexOf(strFrom, i)) >= 0) {
			char[] cSrc = strSource.toCharArray();
			char[] cTo = strTo.toCharArray();
			int len = strFrom.length();
			StringBuffer buf = new StringBuffer(cSrc.length);
			buf.append(cSrc, 0, i).append(cTo);
			i += len;
			int j = i;
			while ((i = strSource.indexOf(strFrom, i)) > 0) {
				buf.append(cSrc, j, i - j).append(cTo);
				i += len;
				j = i;
			}
			buf.append(cSrc, j, cSrc.length - j);
			return buf.toString();
		}
		return strSource;
	}

}
