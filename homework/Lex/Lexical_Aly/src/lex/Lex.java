package lex;
import java.util.List;

import iohelper.ReInputHandler;
import iohelper.TextInputHandler;
import debug.Debug;


public class Lex {
		
	public  List<Token> getTokenList() {
		ReInputHandler r = new ReInputHandler();
		r.open();
		PatternList patternlist = null;
		try {
			patternlist = r.getPatternList();
		} catch (Exception e) {Debug.print(e.getMessage());}
		ReCompiler reCompiler = new ReCompiler(patternlist);
		reCompiler.compile();
		Tokenizer tokenizer = new Tokenizer(reCompiler.getDtran(), reCompiler.getEndStatePatternSearcher());
		TextInputHandler text = new TextInputHandler();
		Debug.split();
		List<Token> tokenlist = tokenizer.process(text);
		return tokenlist;
	}
}
