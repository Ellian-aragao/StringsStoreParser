package aragao.ellian.com.github;

import java.util.List;

public record Vendas(String saleId, List<Item> items, String salesmanName) {
	public static final String id = ModelsIds.VENDA.getId();
}
