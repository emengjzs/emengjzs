//package iohelper;
//
//import grammar.Grammar;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.LinkedList;
//
//import debug.Debug;
//
//public class test {
//	private static String base = "D:\\Downloads\\Diff_SLR_LR1_LALR_Compile-master\\Diff_SLR_LR1_LALR_Compile-master\\LR1\\CompPrinLab\\Grammar Analyze\\Result\\";
//	private static String path = base + "ACTION.txt";
//	private static String savepath = "..\\Input\\form.txt";
//	private static String gotopath = base + "GOTO.txt";
//	private BufferedReader br;
//	private BufferedWriter wr;
//	ArrayList<Grammar> grammarList;
//	public test() {
//		GrammarInputHandler grammarinput = new GrammarInputHandler();
//		grammarList = grammarinput.getGrammarList();
//	}
//	
//	public static void main(String args[]) {
//		test t = new test();
//		t.open();t.read();
//		
//	}
//	
//	public void open() {
//		try {
//			br = new BufferedReader(new FileReader(new File(path)));
//			wr = new BufferedWriter(new FileWriter(new File(savepath)));
//		} catch (IOException e) {
//			Debug.print("Error: File Not Found.", e);
//		}
//	}
//	
//	private void open(String path) {
//		try {
//			br = new BufferedReader(new FileReader(new File(path)));
//		} catch (FileNotFoundException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
//	}
//	
//	private int getindex(String nt, int index) {
//		int size = this.grammarList.size();
//		for(int i = 0; i < size; ++i) {
//			if(this.grammarList.get(i).getHead().getSign().equals(nt)) {
//				return index + i;
//			}
//		}
//		return -1;
//	}
//	
//	public void read() {
//		
//		String s;
//		try {
//			br.readLine();
//			while((s = br.readLine()) != null) {
//				Debug.print(s);
//				s = s.replace("_KULV_END_", "$");
//				String args[] = s.split("\t"); 
//				if(Integer.parseInt(args[3]) == 1) {
//					int index = getindex(args[4], Integer.parseInt(args[5]));
//					wr.write(args[0]+" "+args[1]+" "+args[3] + " "+ index + "\n");
//				}
//				else{
//					wr.write(args[0]+" "+args[1]+" "+args[3] + " "+ args[4] + "\n");
//				}
//				wr.flush();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		try {
//			wr.write("GOTO\n");
//		} catch (IOException e1) {
//			// TODO 自动生成的 catch 块
//			e1.printStackTrace();
//		}
//		open(gotopath);
//		try {
//			while((s = br.readLine()) != null) {
//				wr.write(s + "\n");
//				wr.flush();
//			}
//		} catch (IOException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
//		try {
//			br.close();
//		} catch (IOException e1) {
//			// TODO 自动生成的 catch 块
//			e1.printStackTrace();
//		}try {
////			wr.close();
//		} catch (IOException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
//	}
//	
//	
//}
