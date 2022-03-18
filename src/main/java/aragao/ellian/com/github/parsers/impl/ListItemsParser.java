package aragao.ellian.com.github.parsers.impl;

import aragao.ellian.com.github.models.Item;
import aragao.ellian.com.github.parsers.Parser;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class ListItemsParser implements Parser<List<Item>> {

	private static final Pattern PATTERN_LIST = Pattern.compile("[\\[\\]]");
	private final Parser<Item> itemParser;

	@Override
	public boolean isNotValidInput(String inputs) {
		return false;
	}

	@Override
	public Optional<List<Item>> parse(String inputs) {
		if (isNotValidInput(inputs)) {
			return Optional.empty();
		}
		final var splitedStringItems = PATTERN_LIST.matcher(inputs).replaceAll("").split(",");
		final var items = Arrays.stream(splitedStringItems)
				.map(itemParser::parse)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.toList();
		return Optional.of(items);
	}
}
