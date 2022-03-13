package aragao.ellian.com.github.parser;

import aragao.ellian.com.github.models.Item;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ItemsParser extends Parser<List<Item>> {

	private final Parser<Item> itemParser;

	@Override
	public Optional<List<Item>> parse(String[] inputs) {
		if (isNotValidInput(inputs)) {
			return Optional.empty();
		}
		final var items = Arrays.stream(inputs)
				.map(stringItemConcateneted -> stringItemConcateneted.split("-"))
				.map(listAttributsItemsToParse -> itemParser.parse(listAttributsItemsToParse).orElse(null))
				.toList();
		return Optional.of(items);
	}
}
