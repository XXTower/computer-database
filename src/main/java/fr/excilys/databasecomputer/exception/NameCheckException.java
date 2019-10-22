package fr.excilys.databasecomputer.exception;

public class NameCheckException extends Throwable {
	private static final long serialVersionUID = 1L;
	
	public NameCheckException (String message) {
		super(message);
	}
}
