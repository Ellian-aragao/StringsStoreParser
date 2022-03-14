package aragao.ellian.com.github.parsers;

import java.util.Optional;

public interface Parser<T> {
	Optional<T> parse(String input);

	boolean isNotValidInput(String inputs);
}
