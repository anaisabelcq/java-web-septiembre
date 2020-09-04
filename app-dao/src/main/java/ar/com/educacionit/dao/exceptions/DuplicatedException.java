package ar.com.educacionit.dao.exceptions;

public class DuplicatedException extends Exception {

	private static final long serialVersionUID = -6818721669787938490L;

	public DuplicatedException(String message) {
		super(message);
	}
	public DuplicatedException(String message, Exception e) {
		super(message,e);
	}
}