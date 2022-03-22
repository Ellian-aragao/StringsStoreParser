package aragao.ellian.com.github.infra.adapters.parsers.impl;

import aragao.ellian.com.github.core.models.Cliente;
import aragao.ellian.com.github.core.usecases.ports.ParserPort;
import aragao.ellian.com.github.infra.adapters.parsers.ParsersEnum;

import java.util.Optional;
import java.util.regex.Pattern;

public class ClienteParser implements ParserPort<Cliente> {

	private static final Pattern PATTERN = ParsersEnum.CLIENTE.getPattern();

	@Override
	public boolean isNotValidInput(String inputs) {
		return !PATTERN.matcher(inputs).matches();
	}

	@Override
	public Optional<Cliente> parse(String input) {
		if (isNotValidInput(input)) {
			return Optional.empty();
		}
		final var inputs = input.split(ParsersEnum.getDELIMITER());
		final var cliente = Cliente.builder()
				.withCnpj(inputs[1])
				.withName(inputs[2])
				.withBusinessArea(inputs[3])
				.build();
		return Optional.of(cliente);
	}
}
