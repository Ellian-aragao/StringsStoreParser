package aragao.ellian.com.github.infra.adapters.parsers.impl;

import aragao.ellian.com.github.core.models.Vendedor;
import aragao.ellian.com.github.core.usecases.ports.ParserPort;
import aragao.ellian.com.github.infra.adapters.parsers.PatternStringLineDataEnum;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class VendedorParser implements ParserPort<Vendedor> {

	private final Pattern vendedorPattern;

	private VendedorParser(Pattern vendedorPattern) {
		this.vendedorPattern = vendedorPattern;
	}

	public static VendedorParser of(PatternStringLineDataEnum vendedor) {
		return new VendedorParser(Objects.requireNonNull(vendedor).getPattern());
	}

	@Override
	public boolean isNotValidInput(String inputs) {
		return !vendedorPattern.matcher(inputs).matches();
	}

	@Override
	public Optional<Vendedor> parse(String input) {
		if (isNotValidInput(input)) {
			return Optional.empty();
		}
		final var inputs = input.split(PatternStringLineDataEnum.getDELIMITER());
		final var vendedor = Vendedor.builder()
				.withCpf(inputs[1])
				.withName(inputs[2])
				.withSalary(inputs[3])
				.build();
		return Optional.of(vendedor);
	}
}
