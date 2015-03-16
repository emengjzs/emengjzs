package grammar;

import grammar.Action.Type;

import java.util.ArrayList;

import debug.Debug;

/**
 * �﷨������
 * @author emengjzs
 *
 */
public class GrammarAlyForm {
	// ״̬�У��±��Ӧ״̬���
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
	// �����ս���Ÿ�������
	public Action getAction(Integer state, Terminal sign) {
		// Debug.print("State: " + state + " Sign: " + sign.sign);
		return stateList.get(state).getAction(sign);
	}
	// ���ݷ��ս���Ÿ�����ת״̬
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
