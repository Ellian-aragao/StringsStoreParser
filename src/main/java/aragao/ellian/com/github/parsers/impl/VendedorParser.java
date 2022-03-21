package aragao.ellian.com.github.parsers.impl;

import aragao.ellian.com.github.models.Vendedor;
import aragao.ellian.com.github.parsers.Parser;
import aragao.ellian.com.github.parsers.ParsersEnum;

import java.util.Optional;
import java.util.regex.Pattern;

public class VendedorParser implements Parser<Vendedor> {

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