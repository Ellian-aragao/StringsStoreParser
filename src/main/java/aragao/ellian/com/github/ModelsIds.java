package aragao.ellian.com.github;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ModelsIds {
	VENDEDOR("001"),
	CLIENTE("002"),
	VENDA("003");

	private final String id;
}
