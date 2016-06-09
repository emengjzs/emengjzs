package structures.reexception;

public class InvalidReOperatorException extends ReException {
	public InvalidReOperatorException() {
		super("非法正则运算符");
	}
}
