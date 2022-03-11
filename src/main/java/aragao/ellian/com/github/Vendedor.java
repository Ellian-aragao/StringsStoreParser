package aragao.ellian.com.github;

import java.math.BigDecimal;

public record Vendedor(String cpf, String name, BigDecimal salary) {
	public static final String id = ModelsIds.VENDEDOR.getId();
}
