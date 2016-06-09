package structures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import structures.reexception.InvalidReOperatorException;
import debug.Debug;

public class NFAConstructor {
	private Stack<NFA> nfaStack = new Stack<NFA>();
	private ReComputer reComputer;
	
	public NFAConstructor() {
		reComputer = new ReComputer();
	}
	
	public NFA construct(ArrayList<Item> postfixRe) {
		Iterator<Item> itr  = postfixRe.iterator();		
		Item item;
		
		try {
		
		while(itr.hasNext()) {
			item = itr.next();
			
			if(item.isOperand()) {
				NFA nfa = new NFA((Character) item.get());
				nfaStack.push(nfa);
			}
			
			else if(item.isOperator()) {
				ReOperator op = (ReOperator) item.get();
				NFA result;
				NFA right;
				NFA left; 
				switch(op) {
				case OR:
					right = nfaStack.pop();
					left = nfaStack.pop();
					result = reComputer.OR(left, right);
					break;
				case CONCAT:
					right = nfaStack.pop();
					left = nfaStack.pop();
					result = reComputer.CONCAT(left, right);
					break;
				case STAR:
					result = reComputer.STAR(nfaStack.pop());
					break;
				default:
					throw new InvalidReOperatorException();
				}
				nfaStack.push(result);
			}
		} 
		
		}catch(Exception e) {Debug.print("正则符号操作数个数不正确");e.printStackTrace();}
		return nfaStack.pop();
	}
	
	
}
