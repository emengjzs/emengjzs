package structures;

import java.util.Stack;

public class OperatorStack extends Stack<ReOperator>{


	/**
	 * 
	 */
	private static final long serialVersionUID = -2885401498076592931L;

	public int isp() {
		return isEmpty() ? 0 : peek().isp();
	}
	
}
