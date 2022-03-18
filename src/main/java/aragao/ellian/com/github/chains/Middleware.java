package aragao.ellian.com.github.chains;

import java.util.Objects;

public abstract class Middleware {
	private Middleware next;

	public Middleware linkWith(Middleware next) {
		this.next = next;
		return next;
	}

	public abstract boolean check(String dataString);

	protected boolean checkNext(String dataString) {
		if (Objects.isNull(next)) {
			return true;
		}
		return next.check(dataString);
	}
}
