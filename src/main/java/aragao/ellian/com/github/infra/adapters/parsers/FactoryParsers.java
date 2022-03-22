package aragao.ellian.com.github.infra.adapters.parsers;

import aragao.ellian.com.github.core.usecases.ports.DataLineStringParserPort;
import aragao.ellian.com.github.core.usecases.ports.ParserPort;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class FactoryParsers implements DataLineStringParserPort {
	private static final Pattern GENERIC_PATTERN = ParsersEnum.GENERIC.getPattern();
	private final List<ParserPort<?>> listParsers;

	public FactoryParsers(ParserPort<?>... parsers) {
		listParsers = Arrays.stream(parsers).toList();
	}

	@Override
	public Optional<ParserPort<?>> whichParserFromDataLineModel(String dataLine) {
		if (Objects.isNull(dataLine) || !GENERIC_PATTERN.matcher(dataLine).matches()) {
			return Optional.empty();
		}
		return listParsers.stream()
				.filter(parser -> !parser.isNotValidInput(dataLine))
				.findFirst();
	}
}
