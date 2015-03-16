package lex;

import iohelper.TextInputHandler;

import java.util.LinkedList;
import java.util.List;

import debug.Debug;
import structures.Dtran;
import structures.EndStatePatternSearcher;

/**
 * The real Tokenizer！！！
 * @author emengjzs
 *
 */
public class Tokenizer {
	private Dtran dtran;
	private EndStatePatternSearcher ESPS;
	private TextInputHandler input;
	private List<Token> tokenList;
	private int start = -1;
	private char ch;
	public Tokenizer(Dtran dfa, EndStatePatternSearcher esps) {
		this.dtran = dfa;
		this.ESPS = esps;
		tokenList = new LinkedList<Token>();
	}
	
	public List<Token> process(TextInputHandler input) {
		this.input = input;	
		ch = input.getNextChar();
		start = input.getCurrentChar();
		while(start != -1) {
			dtran.searchStart();
			g();
		}
		return this.tokenList;
	}
	
	public void clear() {
		dtran.searchStart();
		tokenList.clear();
	}
	
	private void g() {
		if(start == -1) return;
		while(start != -1 && dtran.findNext(ch)) {
			// Debug.print("find next");
			if(input.hasNext()) {
				ch = input.getNextChar();
				start = input.getCurrentChar();
				continue;
			}
			else {
				this.installToken();
				start = -1;
				// Debug.print("find end");
				return;
			}
		}
		if(dtran.reachEnd()) {
			input.rollback();
			//
			//	ch = input.getNextChar();
			if(! input.isStringBufferEmpty())
				this.installToken();
			ch = input.getNextChar();
			start = input.getCurrentChar();
			// Debug.print("next char" + start);
		}
		else {
			logError();
			ch = input.getNextChar();
			start = input.getCurrentChar();
		}
		
	}
				
	private void logError() {
		Debug.print("不能识别的非法词素：" + input.dump());
	}
	
	private void installToken() {
		Token token = new Token();
		token.setValue(input.dump());
		token.setType(dtran.getTokenType(ESPS));
		if(! token.getType().equals("WHITE") && ! token.getType().equals("COMMIT")) {
			Debug.print(token.toString());
			tokenList.add(token);		
		}
	}
	
	
}
