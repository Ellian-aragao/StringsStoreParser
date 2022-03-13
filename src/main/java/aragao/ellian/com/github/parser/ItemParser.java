package aragao.ellian.com.github.parser;

import aragao.ellian.com.github.models.Item;

import java.util.Optional;

public class ItemParser extends Parser<Item> {

	private final Integer itemsFieldsLenght = Item.class.getDeclaredFields().length;

	@Override
	protected boolean isNotValidInput(String[] inputs) {
		return super.isNotValidInput(inputs) || itemsFieldsLenght.equals(inputs.length);
	}

	@Override
	public Optional<Item> parse(String[] inputs) {
		if (isNotValidInput(inputs)) {
			return Optional.empty();
		}
		final var item = Item.builder()
				.withId(inputs[0])
				.withQuantity(inputs[1])
				.withPrice(inputs[2])
				.build();
		return Optional.of(item);
	}
}
