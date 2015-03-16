package structures;

import java.util.ArrayList;

import debug.Debug;
import basic.Vertex;
import lex.Pattern;

/**
 * 根据终态搜索对应的词法单元/正则模式
 * @author emengjzs
 *
 */
public class EndStatePatternSearcher {
	private ArrayList<Integer> vertexList;
	private ArrayList<Pattern> patternList;
	private int len = 0;
	
	public EndStatePatternSearcher() {
		vertexList = new ArrayList<Integer>();
		patternList = new ArrayList<Pattern>();
	}
	
	public void registEndState(Vertex<Integer> v, Pattern p) {
		int index = getPatternIndex(v.getData());
		if(index == -1) {
			vertexList.add(v.getData());
			patternList.add(p);
			++ len;
		}
		else {
			Pattern old = patternList.get(index);
			Debug.split();
			Debug.print("Pattern Conflict!");
			Debug.print(old.toString() + " VS " + p.toString());
			int r = old.compareTo(p);
			if(r == 0) {
				Debug.print("The pattern " + p.getType()  + " may have intersection with " + old.getType());
			}
			else {
				Pattern result = r > 0 ? old : p;
				patternList.set(index, result);
				Debug.print("The pattern " + result.getType() + " is reserved.");
			}	
		}
	}
	
	public Pattern getPattern(Vertex<Integer> v) {
		int index = getPatternIndex(v.getData());
		return index == -1 ? null : patternList.get(index);
	}
	
	
	private int getPatternIndex(int vertexID) {
		for(int index = 0; index < len; index ++) {
			if(vertexList.get(index).equals(vertexID)) {
				return index;
			}
		}	
		return -1;
	}
}
