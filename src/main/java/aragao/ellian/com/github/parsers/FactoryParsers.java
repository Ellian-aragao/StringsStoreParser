package aragao.ellian.com.github.parsers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class FactoryParsers {
	private static final Pattern GENERIC_PATTERN = ParsersEnum.GENERIC.getPattern();
	private final List<Parser<?>> listParsers;

	public FactoryParsers(Parser<?>... parsers) {
		listParsers = Arrays.stream(parsers).toList();
	}

	public Optional<Parser<?>> whichParser(String dataToParse) {
		if (Objects.isNull(dataToParse) || !GENERIC_PATTERN.matcher(dataToParse).matches()) {
			return Optional.empty();
		}
		return listParsers.stream()
				.filter(parser -> !parser.isNotValidInput(dataToParse))
				.findFirst();
	}
}
