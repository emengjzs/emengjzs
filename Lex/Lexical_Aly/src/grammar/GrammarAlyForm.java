package grammar;

import grammar.Action.Type;

import java.util.ArrayList;

import debug.Debug;

/**
 * 语法分析表
 * @author emengjzs
 *
 */
public class GrammarAlyForm {
	// 状态列，下标对应状态序号
	private ArrayList<State> stateList;
	
	public void addAction(int i, Terminal type, Type action, int index) {
		check(i);
		this.stateList.get(i).addAction(type, new Action(action, index));
		// Debug.print(this.stateList.size());
	}
	
	
	public void addGOTO(int i, Nonterminal type, int index) {
		check(i);
		this.stateList.get(i).addGOTO(type, index);
	}
	
	public GrammarAlyForm() {
		this.stateList = new ArrayList<State>();
	}
	// 根据终结符号给出动作
	public Action getAction(Integer state, Terminal sign) {
		// Debug.print("State: " + state + " Sign: " + sign.sign);
		return stateList.get(state).getAction(sign);
	}
	// 根据非终结符号给出跳转状态
	public Integer getGOTO(Integer state, Nonterminal sign) {
		return stateList.get(state).getGOTO(sign);
	}
	
	private void check(int i) {
		if(this.stateList.size() <= i) {
			for(int j = this.stateList.size(); j <= i; ++j) {
				stateList.add(new State());
			}
		}
	}
	
	public void print() {
		
	}
	
}
