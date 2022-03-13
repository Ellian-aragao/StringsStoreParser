package aragao.ellian.com.github.parser;

import aragao.ellian.com.github.models.Item;
import aragao.ellian.com.github.models.Vendas;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class VendaParser extends Parser<Vendas> {

	private final Parser<List<Item>> itemsParser;

	@Override
	protected boolean isNotValidInput(String[] inputs) {
		return super.isNotValidInput(inputs) || !Vendas.getId().equals(inputs[0]);
	}

	@Override
	public Optional<Vendas> parse(String[] inputs) {
		if (isNotValidInput(inputs)) {
			return Optional.empty();
		}
		final var items = itemsParser.parse(inputs[2].replaceAll("[\\[\\]]", "").split(","))
				.orElseThrow();

		final var vendas = Vendas.builder()
				.withSaleId(inputs[1])
				.withItems(items)
				.withSalesmanName(inputs[3])
				.build();
		return Optional.of(vendas);
	}
}
