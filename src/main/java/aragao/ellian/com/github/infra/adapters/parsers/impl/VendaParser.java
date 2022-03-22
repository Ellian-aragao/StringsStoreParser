package aragao.ellian.com.github.infra.adapters.parsers.impl;

import aragao.ellian.com.github.core.models.Item;
import aragao.ellian.com.github.core.models.Vendas;
import aragao.ellian.com.github.core.usecases.ports.ParserPort;
import aragao.ellian.com.github.infra.adapters.parsers.ParsersEnum;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class VendaParser implements ParserPort<Vendas> {

	private static final Pattern PATTERN = ParsersEnum.VENDA.getPattern();

	private final ParserPort<List<Item>> itemsParser;

	@Override
	public boolean isNotValidInput(String inputs) {
		return !PATTERN.matcher(inputs).matches();
	}

	@Override
	public Optional<Vendas> parse(String input) {
		if (isNotValidInput(input)) {
			return Optional.empty();
		}
		final var inputs = input.split(ParsersEnum.getDELIMITER());
		final var items = itemsParser.parse(inputs[2]).orElseThrow();
		final var vendas = Vendas.builder()
				.withSaleId(inputs[1])
				.withItems(items)
				.withSalesmanName(inputs[3])
				.build();
		return Optional.of(vendas);
	}
}
