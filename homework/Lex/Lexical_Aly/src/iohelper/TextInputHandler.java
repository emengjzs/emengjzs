package iohelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import debug.Debug;

/**
 * Get the strings from file or other input source
 * @author emengjzs
 *
 */
public class TextInputHandler {
	private InputStream ins;
	private StringBuffer temp;
	private static int LAST = 0;
	private static int NOW = 1;
	private static int NEXT = 2;
	private int ch[]; 
	private int ptr = NOW;
	private static String path = "..\\Input\\text.txt";
	public TextInputHandler() {
		File inputFile = new File(path);
		try {
			ins = new FileInputStream(inputFile);
		} catch (FileNotFoundException e) {
			Debug.print("Error: File Not Found.", e);
		}
		
		init();
	}
	
	public TextInputHandler(InputStream ins) {
		this.ins = ins;
		temp = new StringBuffer();
		init();
	}
	
	
	private void init() {
		ch = new int[3];
		ch[LAST] = -1;
		ch[NOW] = -1;
		temp = new StringBuffer();
		try {
			ch[NEXT] = ins.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasNext() {
		return (ch[ptr + 1] != -1);
	}
	
	public char getNextChar() {		
		if(ptr == LAST) {
			this.temp.appendCodePoint(ch[++ptr]);
			return (char) ch[ptr];
		}
		ch[LAST] = ch[NOW];
		ch[NOW] = ch[NEXT];
		try {
			ch[NEXT] = ins.read();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		if(ch[ptr] != -1)
			this.temp.appendCodePoint(ch[ptr]);
		return (char) ch[ptr];
	}
	
	public int getCurrentChar() {
		return ch[ptr];
	}
	
	public int getLastChar() {
		return ch[LAST];
	}
	
	public void rollback() {
		ptr--;
		this.temp.deleteCharAt(temp.length() -1);
	}
	
	public String dump() {
		String s =  new String(this.temp);
		clearStringBuffer();
		return s;
	}
	
	public void clearStringBuffer() {
		temp.setLength(0);
	}
	
	public boolean isStringBufferEmpty() {
		return this.temp.length() == 0;
	}
}
