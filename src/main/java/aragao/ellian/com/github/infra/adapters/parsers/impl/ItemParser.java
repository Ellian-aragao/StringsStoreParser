package aragao.ellian.com.github.infra.adapters.parsers.impl;

import aragao.ellian.com.github.core.models.Item;
import aragao.ellian.com.github.core.usecases.ports.ParserPort;
import aragao.ellian.com.github.infra.adapters.parsers.ParsersEnum;

import java.util.Optional;
import java.util.regex.Pattern;

public class ItemParser implements ParserPort<Item> {

	private static final Pattern PATTERN = ParsersEnum.ITEM.getPattern();

	@Override
	public boolean isNotValidInput(String inputs) {
		return !PATTERN.matcher(inputs).matches();
	}

	@Override
	public Optional<Item> parse(String input) {
		if (isNotValidInput(input)) {
			return Optional.empty();
		}
		final var inputs = input.split("-");
		final var item = Item.builder()
				.withId(inputs[0])
				.withQuantity(inputs[1])
				.withPrice(inputs[2])
				.build();
		return Optional.of(item);
	}
}
