package aragao.ellian.com.github.parser;

import java.util.Objects;
import java.util.Optional;

public abstract class Parser<T> {
	public abstract Optional<T> parse(String[] inputs);

	protected boolean isNotValidInput(String[] inputs) {
		return Objects.isNull(inputs) || inputs.length == 0;
	}
}
