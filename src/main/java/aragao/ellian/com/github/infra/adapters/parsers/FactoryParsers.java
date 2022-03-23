package aragao.ellian.com.github.infra.adapters.parsers;

import aragao.ellian.com.github.core.usecases.ports.DataLineStringParserPort;
import aragao.ellian.com.github.core.usecases.ports.ParserPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class FactoryParsers implements DataLineStringParserPort {

	private final Pattern genericEntryPatternPattern;

	private final List<ParserPort<?>> listParsers;

	private FactoryParsers(Pattern genericEntryPatternPattern, List<ParserPort<?>> listParsers) {
		Objects.requireNonNull(genericEntryPatternPattern, "genericEntryPatternPattern is null");
		Objects.requireNonNull(listParsers, "listParsers is null");
		if (listParsers.isEmpty()) {
			throw new IllegalArgumentException("listParsers is empty");
		}
		this.listParsers = listParsers;
		this.genericEntryPatternPattern = genericEntryPatternPattern;
	}

	public static FactoryParsersBuilder builder() {
		return new FactoryParsersBuilder();
	}

	@Override
	public Optional<ParserPort<?>> whichParserFromDataLineModel(String dataLine) {
		if (Objects.isNull(dataLine) || !genericEntryPatternPattern.matcher(dataLine).matches()) {
			return Optional.empty();
		}
		return listParsers.stream()
				.filter(parser -> !parser.isNotValidInput(dataLine))
				.findFirst();
	}

	public static class FactoryParsersBuilder {
		private Pattern genericEntryPatternPattern;
		private List<ParserPort<?>> listParsers;

		public FactoryParsersBuilder withGenericEntryPatternPattern(Pattern genericEntryPatternPattern) {
			Objects.requireNonNull(genericEntryPatternPattern, "genericEntryPatternPattern is null");
			this.genericEntryPatternPattern = genericEntryPatternPattern;
			return this;
		}

		public FactoryParsersBuilder withParser(ParserPort<?> parser) {
			Objects.requireNonNull(parser, "parser is null");
			if (Objects.isNull(listParsers)) {
				listParsers = new ArrayList<>();
			}
			listParsers.add(parser);
			return this;
		}

		public FactoryParsers build() {
			return new FactoryParsers(genericEntryPatternPattern, listParsers);
		}
	}
}
