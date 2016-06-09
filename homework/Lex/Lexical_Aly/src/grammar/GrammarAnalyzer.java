package grammar;

import iohelper.ResultWriter;

import java.util.ArrayList;
import java.util.List;

import debug.Debug;
import lex.Token;

public class GrammarAnalyzer {
	
	private ArrayList<Grammar> grammarList;
	
	private TokenStream tokenStream;
		
	private StateStack<Integer> stateStack;
	
	private StateStack<Sign> signStack;
	
	private GrammarAlyForm form;
	
	private StringBuffer info;
	
	private ResultWriter writer;
	
	public GrammarAnalyzer(ArrayList<Grammar> grammarList, GrammarAlyForm form) {
		this.info = new StringBuffer();
		this.form = form;
		this.grammarList = grammarList;
		stateStack = new StateStack<Integer>();
		signStack = new StateStack<Sign>();
	}
	
	public void init(List<Token> tokenList) {
		tokenStream = new TokenStream(tokenList);
		this.stateStack.clear();
		this.stateStack.push(0);
		this.signStack.clear();
		this.writer = new ResultWriter();
	}
	
	
	
	public void analysis() {
		boolean run = true;
		while(run) {
			info.setLength(0);
			info.append(this.getStateStackInfo() + "\t");
			info.append(this.getSignStackInfo() + "\t");
			Integer index = this.stateStack.getTop();
			Terminal searchTerminal = new Terminal(this.tokenStream.getCurrentToken());
			info.append(searchTerminal.sign + " \t");
			Action action = this.form.getAction(index, searchTerminal);
			switch(action.getType()) {
				case MOVE:
					move(action.getIndex(),  searchTerminal);
					break;
				case PRODUCE:
					produce(action.getIndex());
					break;
				case ACCEPT:
					info.append(action.toString());
					run = false;
					break;
				case ERROR:
					error();
					run = false;
					break;
			}
			printInfo();
		}
		this.writer.close();
	}
	
	private void printInfo() {
		Debug.print(this.info.toString());
		writer.writeLine(this.info.toString());
	}
	
	private String getStateStackInfo() {
		return this.stateStack.toString();
	}
	
	private String getSignStackInfo() {
		return this.signStack.toString();
	}
	
	
	private void move(Integer index, Terminal t) {
		info.append("“∆»Î: " + t.toString() + '\t');
		this.stateStack.push(index);
		this.signStack.push(t);
		this.tokenStream.goNext();
	}
	
	private void produce(Integer index) {
		Grammar g = this.grammarList.get(index);
		int count = g.length();
		this.stateStack.pop(count);
		this.signStack.pop(count);
		info.append("πÈ‘º" + index + ": " + g.toString() + '\t');
		Integer top = this.stateStack.getTop();
		
		Integer goTo = this.form.getGOTO(top, g.getHead());
		//Debug.print("GOTO: " + g.getHead().sign + " " + goTo);
		this.stateStack.push(goTo);
		this.signStack.push(g.getHead());
	}
	
	private void error() {
		info.append("¥ÌŒÛ£°£°£°");
	}
}
