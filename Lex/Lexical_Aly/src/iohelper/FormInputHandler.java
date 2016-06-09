package iohelper;

import grammar.Action;
import grammar.GrammarAlyForm;
import grammar.Nonterminal;
import grammar.Sign;
import grammar.Terminal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import debug.Debug;

public class FormInputHandler {
	private static String path = "..\\Input\\form.txt";
	private BufferedReader br;
	private GrammarAlyForm form;
	
	public void open() {
		try {
			br = new BufferedReader(new FileReader(new File(path)));
		} catch (IOException e) {
			Debug.print("Error: File Not Found.", e);
		}
	}
	
	public GrammarAlyForm read()  {
		form = new GrammarAlyForm();
		String s;
		Terminal t;
		try {
			while ((s = br.readLine()) != null) {
				if(s.equals("GOTO"))
					break;
				String args[] = s.split(" ");
				char ch = args[2].charAt(0);
				t = new Terminal(args[1]);
				if(ch == '0')
					form.addAction(Integer.parseInt(args[0]), t, Action.Type.MOVE, Integer.parseInt(args[3]));
				else if(ch == '1')
					form.addAction(Integer.parseInt(args[0]), t, Action.Type.PRODUCE, Integer.parseInt(args[3]));
				else if(ch == '2')
					form.addAction(Integer.parseInt(args[0]), t, Action.Type.ACCEPT, -1);	
				
			}
			
			Nonterminal nt = null;
			while ((s = br.readLine()) != null) {
				String args[] = s.split(" ");
			
					nt = new Nonterminal(args[1]);		
					form.addGOTO(Integer.parseInt(args[0]), nt, Integer.parseInt(args[2]));	
				
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.form;
	}
}
