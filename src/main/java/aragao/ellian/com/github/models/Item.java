package aragao.ellian.com.github.models;

import java.math.BigDecimal;

public record Item(String id, Integer quantity, BigDecimal price) {
	public static ItemBuilder builder() {
		return new ItemBuilder();
	}

	public static class ItemBuilder {
		private String id;
		private Integer quantity;
		private BigDecimal price;

		public ItemBuilder withId(String id) {
			this.id = id;
			return this;
		}

		public ItemBuilder withQuantity(Integer quantity) {
			this.quantity = quantity;
			return this;
		}

		public ItemBuilder withQuantity(String quantity) {
			this.quantity = Integer.parseInt(quantity);
			return this;
		}


		public ItemBuilder withPrice(BigDecimal price) {
			this.price = price;
			return this;
		}

		public ItemBuilder withPrice(String price) {
			this.price = new BigDecimal(price);
			return this;
		}

		public Item build() {
			return new Item(id, quantity, price);
		}
	}
}
