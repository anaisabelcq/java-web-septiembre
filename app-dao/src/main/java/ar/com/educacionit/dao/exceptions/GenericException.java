package ar.com.educacionit.dao.exceptions;

public class GenericException extends Exception {

	private static final long serialVersionUID = -6818721669787938490L;

	public GenericException(String message) {
		super(message);
	}

	public GenericException(String message, Exception e) {
		super(message, e);
	}
}