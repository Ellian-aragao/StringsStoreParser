package aragao.ellian.com.github.infra.adapters.parsers.impl;

import aragao.ellian.com.github.core.models.Item;
import aragao.ellian.com.github.core.usecases.ports.ParserPort;
import aragao.ellian.com.github.infra.adapters.parsers.PatternStringLineDataEnum;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ListItemsParser implements ParserPort<List<Item>> {

	private final Pattern patternList;
	private final ParserPort<Item> itemParser;

	private ListItemsParser(Pattern patternList, ParserPort<Item> itemParser) {
		this.patternList = patternList;
		this.itemParser = itemParser;
	}

	public static ListItemsParser of(PatternStringLineDataEnum patternList, ParserPort<Item> itemParser) {
		return new ListItemsParser(patternList.getPattern(), itemParser);
	}

	@Override
	public boolean isNotValidInput(String inputs) {
		return patternList.matcher(inputs).matches();
	}

	@Override
	public Optional<List<Item>> parse(String inputs) {
		if (isNotValidInput(inputs)) {
			return Optional.empty();
		}
		final var splitedStringItems = patternList.matcher(inputs).replaceAll("").split(",");
		final var items = Arrays.stream(splitedStringItems)
				.map(itemParser::parse)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.toList();
		return Optional.of(items);
	}
}
