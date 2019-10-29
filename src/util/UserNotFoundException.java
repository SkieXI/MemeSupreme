package util;

public class UserNotFoundException extends RuntimeException 
{
	private static final long serialVersionUID = 00000000000000000001;

	public UserNotFoundException(Throwable e) {
		super(e);
	}
}
