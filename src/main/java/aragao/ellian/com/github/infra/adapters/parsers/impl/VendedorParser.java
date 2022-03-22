package aragao.ellian.com.github.infra.adapters.parsers.impl;

import aragao.ellian.com.github.core.models.Vendedor;
import aragao.ellian.com.github.core.usecases.ports.ParserPort;
import aragao.ellian.com.github.infra.adapters.parsers.ParsersEnum;

import java.util.Optional;
import java.util.regex.Pattern;

public class VendedorParser implements ParserPort<Vendedor> {

	private static final Pattern PATTERN = ParsersEnum.VENDEDOR.getPattern();

	@Override
	public boolean isNotValidInput(String inputs) {
		return !PATTERN.matcher(inputs).matches();
	}

	@Override
	public Optional<Vendedor> parse(String input) {
		if (isNotValidInput(input)) {
			return Optional.empty();
		}
		final var inputs = input.split(ParsersEnum.getDELIMITER());
		final var vendedor = Vendedor.builder()
				.withCpf(inputs[1])
				.withName(inputs[2])
				.withSalary(inputs[3])
				.build();
		return Optional.of(vendedor);
	}
}
