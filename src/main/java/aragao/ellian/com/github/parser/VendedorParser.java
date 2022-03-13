package aragao.ellian.com.github.parser;

import aragao.ellian.com.github.models.Vendedor;

import java.util.Optional;

public class VendedorParser extends Parser<Vendedor> {

	@Override
	protected boolean isNotValidInput(String[] inputs) {
		return super.isNotValidInput(inputs) || !Vendedor.getId().equals(inputs[0]);
	}

	@Override
	public Optional<Vendedor> parse(String[] inputs) {
		if (isNotValidInput(inputs)) {
			return Optional.empty();
		}
		final var vendedor = Vendedor.builder()
				.withCpf(inputs[1])
				.withName(inputs[2])
				.withSalary(inputs[3])
				.build();
		return Optional.of(vendedor);
	}
}
