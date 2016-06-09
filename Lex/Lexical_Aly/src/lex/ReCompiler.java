package lex;

import java.util.ArrayList;
import java.util.Iterator;
import structures.DFA;
import structures.DFAConstructor;
import structures.Dtran;
import structures.EndStatePatternSearcher;
import structures.Item;
import structures.NFA;
import structures.NFACombiner;
import structures.NFAConstructor;
import structures.ReTokenizer;
import structures.reexception.BracketException;

/**
 * 从RE到DFA，隐藏中间转换过程，返回转换表和终态表
 * @author emengjzs
 *
 */
public class ReCompiler {
	private PatternList patternlist;
	private Dtran dtran;
	private EndStatePatternSearcher esps;
	
	
	public ReCompiler(PatternList patternlist ) {
		this.patternlist = patternlist;
	}
	
	public void compile() {
		Iterator<Pattern> pitr = patternlist.getPatternIterator();
		
		NFACombiner NFAcombiner = new NFACombiner(); 
		
		while(pitr.hasNext()) {
			Pattern p = pitr.next();
			ReTokenizer constructor = new ReTokenizer();
			constructor.tokenize(p.getRe());
			ArrayList<Item> pofixRe = null;
			try {
				pofixRe = constructor.constructPostfixRe();
			} catch (BracketException e) {e.printStackTrace();}
			NFAConstructor NFAconstructor = new NFAConstructor();
			NFA nfa = NFAconstructor.construct(pofixRe);
			nfa.setPattern(p);	
			//nfa.show();
			NFAcombiner.add(nfa);
		}
		
		NFA bnfa = NFAcombiner.combine();
		// bnfa.show();
		
		DFAConstructor DFAconstructor = new DFAConstructor();
		DFA dfa = DFAconstructor.consrtuct(bnfa);
		DFAconstructor.setNFAESPSearcher(NFAcombiner.getEndStateSearcher());			
		//dfa.show();
		this.dtran = dfa;
		this.esps = DFAconstructor.getDFAESPSearcher();
	}

	public EndStatePatternSearcher getEndStatePatternSearcher() {
		return esps;
	}

	public Dtran getDtran() {
		return dtran;
	}
	
}
