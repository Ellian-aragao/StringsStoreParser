package aragao.ellian.com.github.models;

import java.math.BigDecimal;
import java.util.List;

public record Vendas(String saleId, List<Item> items, String salesmanName) {

	public BigDecimal precoDaVenda() {
		return items.stream()
				.map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public static VendasBuilder builder() {
		return new VendasBuilder();
	}

	public static class VendasBuilder {
		private String saleId;
		private List<Item> items;
		private String salesmanName;

		public VendasBuilder withSaleId(String saleId) {
			this.saleId = saleId;
			return this;
		}

		public VendasBuilder withItems(List<Item> items) {
			this.items = items;
			return this;
		}

		public VendasBuilder withSalesmanName(String salesmanName) {
			this.salesmanName = salesmanName;
			return this;
		}

		public Vendas build() {
			return new Vendas(saleId, items, salesmanName);
		}
	}
}
