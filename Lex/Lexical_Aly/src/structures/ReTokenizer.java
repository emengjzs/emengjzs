package structures;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import debug.Debug;
import structures.reexception.BracketException;

/**
 * ����<������ʽ>�Ĵʷ�������
 * @author emengjzs
 *
 */
public class ReTokenizer {
	private LinkedList<Item> rawTokens;
	
	boolean isBeforeSubRe;
	boolean isAfterSubRe;
	
	
	public ReTokenizer() {
		rawTokens = new LinkedList<Item>();
		isBeforeSubRe = false;
		isAfterSubRe = true;
	}
	
	public void tokenize(String re) {
		Debug.print(re);
		re = re.replaceAll("\\.", "\\\\\\.");
		Debug.print(re);
		StringCharacterIterator stringItr = new StringCharacterIterator(re);	
		char ch;
				
		while((ch = stringItr.current()) != StringCharacterIterator.DONE) {
			// Debug.print(ch);
			
			// ת��
			if(ch == '\\')  {
				ch = stringItr.next();
				if(ch == StringCharacterIterator.DONE) {
					ch = '\\';				
				}
				else {
					ch = getEscape(ch);
				}
				addToken(new Item(ch), true, true);			
			}
			
			// ��Ϊ������
			else if(ReOperator.isOperator(ch)) {	
				ReOperator operator = ReOperator.getOperatorType(ch);
				addToken(new Item(operator), operator.canBeStartOfRe(), operator.canBeEndOfRe());				
			}
			
			// ������
			else {
				addToken(new Item(ch), true, true);				
			}
					
			stringItr.next();
		}		
	}
	
	/**
	 * ������׺���ʽ
	 * @throws BracketException 
	 */
	public ArrayList<Item> constructPostfixRe() throws BracketException {
		ArrayList<Item> postfixTokens = new ArrayList<Item>();
		OperatorStack operatorStack = new OperatorStack();
		Stack<Item> itemStack = new Stack<Item>();
		Iterator<Item> itr = this.rawTokens.iterator();		
		while(itr.hasNext() || (!itemStack.isEmpty())) {
			
			Item item = itemStack.isEmpty() ? itr.next() : itemStack.pop();
			
			if(item.isOperand()) {
				postfixTokens.add(item);
			}
			else {
				ReOperator operator = (ReOperator) item.get();
				if(operatorStack.isp() < operator.icp()) {
					operatorStack.push(operator);
				}
				else if(operatorStack.isp() > operator.icp()) {
					postfixTokens.add(new Item(operatorStack.pop()));
					itemStack.push(item);
				}
				else {
					operatorStack.pop();				
				}
			}			
		}	
		while(! operatorStack.isEmpty()) {
			ReOperator op = operatorStack.pop();
			if(op == ReOperator.LEFT_BRACKET || op == ReOperator.RIGHT_BRACKET) {
				throw new BracketException();
			}
			postfixTokens.add(new Item(op));
		}
		return postfixTokens;
	}
	
	/**
	 * ����token
	 * @param item ����
	 * @param isAfterSubRe �������Ƿ�����Ǳ��ʽ�Ŀ�ͷ
	 * @param setBeforeSubRe �趨�����������Ӻ��Ƿ��Ǳ��ʽ�Ľ�β
	 */
	private void addToken(Item item, boolean isAfterSubRe, boolean setBeforeSubRe) {
		this.isAfterSubRe = isAfterSubRe;
		
		/*���������߶����ӱ��ʽ��������ӷ�*/
		if(this.isBeforeSubRe && this.isAfterSubRe) 
			this.rawTokens.add(new Item(ReOperator.CONCAT));
		
		this.rawTokens.add(item);
		isBeforeSubRe = setBeforeSubRe;
	}
	
	public void getString() {
		//StringBuffer string = new StringBuffer();
		Iterator<Item> itr = this.rawTokens.iterator();
		while(itr.hasNext()) {
			System.out.print(itr.next().toString());
		}
	}
	
	private char getEscape(char ch) {
		switch(ch) {
		case 't' : return '\t';
		case 'n' : return '\n';
		case 'r' : return '\r';
		case 'f' : return '\f';
		case 's' : return ' ';
		default  : return ch;
		}
	}
	
}
