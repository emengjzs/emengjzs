package grammar;

import iohelper.FormInputHandler;
import iohelper.GrammarInputHandler;
import java.util.ArrayList;

import lex.Lex;

public class Main {

	
	public static void main(String[] args) {
		Main main = new Main();
		main.start();
	}
	
	public void start() {
		Lex lex = new Lex();
		GrammarInputHandler grammarinput = new GrammarInputHandler();
		ArrayList<Grammar> grammarList = grammarinput.getGrammarList();
		FormInputHandler forminput = new FormInputHandler();
		forminput.open();
		GrammarAnalyzer analyzer = new GrammarAnalyzer(grammarList, forminput.read());
		analyzer.init(lex.getTokenList());
		analyzer.analysis();
		
		/*
		BasicFrame basicUI  = new BasicFrame();
		JFrame frame = basicUI.getBasicFrame("语法分析结果", 400, 300);
		String title[] = {"栈", "符号", "动作"};
		String s[][] = {{"1","2","3"}};
		JTable resultTable = basicUI.getBasicTable(s, title);
		resultTable.setSize(400, 300);
		frame.add(resultTable);
		frame.repaint();
		frame.setVisible(true);
		*/
	}
}
