package aragao.ellian.com.github.core.usecases.ports;

import java.util.Optional;

public interface ParserPort<T> {
	Optional<T> parse(String input);

	boolean isNotValidInput(String inputs);
}
