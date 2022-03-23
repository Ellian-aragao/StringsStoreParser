package aragao.ellian.com.github.infra.adapters.parsers.impl;

import aragao.ellian.com.github.core.models.Item;
import aragao.ellian.com.github.core.usecases.ports.ParserPort;
import aragao.ellian.com.github.infra.adapters.parsers.PatternStringLineDataEnum;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class ItemParser implements ParserPort<Item> {

	private final Pattern itemPattern;

	private ItemParser(Pattern itemPattern) {
		this.itemPattern = itemPattern;
	}


	public static ItemParser of(PatternStringLineDataEnum item) {
		return new ItemParser(Objects.requireNonNull(item).getPattern());
	}

	@Override
	public boolean isNotValidInput(String inputs) {
		return !itemPattern.matcher(inputs).matches();
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
