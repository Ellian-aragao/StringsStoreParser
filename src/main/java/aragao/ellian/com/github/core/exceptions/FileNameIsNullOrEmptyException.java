package aragao.ellian.com.github.core.exceptions;

public class FileNameIsNullOrEmptyException extends NullOrEmptyException {
	public FileNameIsNullOrEmptyException(String fileName) {
		super("File name is null or empty", fileName);
	}
}
