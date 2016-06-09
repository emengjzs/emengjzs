package grammar;

import java.util.Iterator;
import java.util.List;

import lex.Token;

public class TokenStream {
	private Iterator<Token> tokenItr;	
	private Token currentToken;			// ��Token
	
	public static final String END_SIGN ="$"; 
	
	
	public TokenStream(List<Token> tokenList) {
		addEndSign(tokenList); //ΪToken���н�β�����ս��
		this.tokenItr = tokenList.iterator();
		this.currentToken = tokenItr.next();
	}
	
	
	public Token getCurrentToken() {
		return this.currentToken;
	}
	
	public Token goNext() {
		this.currentToken = tokenItr.next();
		return this.currentToken;
	}
	
	/**ΪToken���н�β�����ս��*/
	private void addEndSign(List<Token> tokenList) {
		Token end = new Token();
		end.setType(END_SIGN);
		end.setValue("");
		tokenList.add(end);
	}
}
