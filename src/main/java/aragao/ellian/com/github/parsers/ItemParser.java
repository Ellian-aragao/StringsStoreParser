package aragao.ellian.com.github.parsers;

import aragao.ellian.com.github.models.Item;

import java.util.Optional;
import java.util.regex.Pattern;

public class ItemParser implements Parser<Item> {

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
