package iohelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResultWriter {
	private static String base = "..\\Output\\";
	private static String resultpath = base + "result.txt";
	
	private BufferedWriter wr;
	
	public ResultWriter() {
		open(resultpath);
	}
	
	public ResultWriter(String name) {
		open(name);
	}
	
	public void close() {
		try {
			this.wr.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	
	public void writeLine(String s) {
		write(s + "\n");
	}
	
	public void write(String s){
		try {
			this.wr.write(s);
			wr.flush();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}	
	}
	
	private void open(String name) {
		File f = new File(base + name);
		try {
			if(! f.exists()) {
				f.createNewFile();
			}
			wr = new BufferedWriter(new FileWriter(f));
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
