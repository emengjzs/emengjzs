package iohelper;

import grammar.Grammar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import debug.Debug;

public class GrammarInputHandler {
	private static String path = "..\\Input\\grammar.txt";
	private BufferedReader br;
	LinkedList<Grammar> Grammars; 

	public GrammarInputHandler() {
		
	}
	
	public ArrayList<Grammar> getGrammarList() {
		if(Grammars == null) {
			Grammars = new LinkedList<Grammar>(); 
			open();
			read();
		}
		return new ArrayList<Grammar>(Grammars);
	}

	public void open() {
		try {
			br = new BufferedReader(new FileReader(new File(path)));
		} catch (IOException e) {
			Debug.print("Error: File Not Found.", e);
		}
	}
	
	
	public void read() {
		String s;
		try {
			while((s = br.readLine()) != null) {
				Grammar g = new Grammar(s);
				Debug.print(g.toString() + "\tsize: " + g.length());
				Grammars.add(g);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
