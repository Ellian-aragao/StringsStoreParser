package aragao.ellian.com.github.parser;

import aragao.ellian.com.github.models.Cliente;

import java.util.Optional;

public class ClienteParser extends Parser<Cliente> {

	@Override
	protected boolean isNotValidInput(String[] inputs) {
		return super.isNotValidInput(inputs) || !Cliente.getId().equals(inputs[0]);
	}

	@Override
	public Optional<Cliente> parse(String[] inputs) {
		if (isNotValidInput(inputs)) {
			return Optional.empty();
		}
		final var cliente = Cliente.builder()
				.withCnpj(inputs[1])
				.withName(inputs[2])
				.withBusinessArea(inputs[3])
				.build();
		return Optional.of(cliente);
	}
}
