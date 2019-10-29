package util;
/**
 * @Author Joe Leon/Lewis Brown
 * Barrowed Code From Assignment 6
 * 10/21/2019
 **/
public class DatabaseException extends RuntimeException {
	private static final long serialVersionUID = 00000000000000000002;

	public DatabaseException(Throwable e) {
		super(e);
	}
}
