package aragao.ellian.com.github.core.exceptions;

class NullOrEmptyException extends IllegalArgumentException{
	public NullOrEmptyException(String message, String dataNullOrEmpty) {
		super(message + ": \"" + dataNullOrEmpty + "\"");
	}
}
