package aragao.ellian.com.github.core.exceptions;

public class StringDataLineIsNullOrEmptyException extends NullOrEmptyException {
	public StringDataLineIsNullOrEmptyException(String dataLine) {
		super("String data line is null or empty", dataLine);
	}
}
