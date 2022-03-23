package aragao.ellian.com.github.infra.adapters.parsers.impl;

import aragao.ellian.com.github.core.models.Item;
import aragao.ellian.com.github.core.models.Vendas;
import aragao.ellian.com.github.core.usecases.ports.ParserPort;
import aragao.ellian.com.github.infra.adapters.parsers.PatternStringLineDataEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
public class VendaParser implements ParserPort<Vendas> {

	private final Pattern vendaPattern;
	private final ParserPort<List<Item>> itemsParser;

	private VendaParser(Pattern vendaPattern, ParserPort<List<Item>> itemsParser) {
		this.vendaPattern = vendaPattern;
		this.itemsParser = itemsParser;
	}

	public static VendaParser of(PatternStringLineDataEnum venda, ListItemsParser listItemsParser) {
		return new VendaParser(Objects.requireNonNull(venda).getPattern(), Objects.requireNonNull(listItemsParser));
	}

	@Override
	public boolean isNotValidInput(String inputs) {
		return !vendaPattern.matcher(inputs).matches();
	}

	@Override
	public Optional<Vendas> parse(String input) {
		log.debug("Parsing venda: {}", input);
		if (isNotValidInput(input)) {
			return Optional.empty();
		}
		final var inputs = input.split(PatternStringLineDataEnum.getDELIMITER());
		final var items = itemsParser.parse(inputs[2]).orElseThrow();
		final var vendas = Vendas.builder()
				.withSaleId(inputs[1])
				.withItems(items)
				.withSalesmanName(inputs[3])
				.build();
		return Optional.of(vendas);
	}
}
