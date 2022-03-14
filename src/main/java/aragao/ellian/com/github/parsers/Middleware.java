package aragao.ellian.com.github.parsers;

import java.util.Objects;
import java.util.Optional;

public abstract class Middleware {
	private Middleware next;

	public Middleware linkWith(Middleware next) {
		this.next = next;
		return next;
	}

	public abstract Optional<?> check(String dataString);

	protected Optional<?> checkNext(String dataString) {
		if (Objects.isNull(next)) {
			return Optional.empty();
		}
		return next.check(dataString);
	}
}
