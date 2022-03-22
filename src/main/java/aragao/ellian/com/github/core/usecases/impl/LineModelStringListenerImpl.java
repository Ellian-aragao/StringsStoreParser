package aragao.ellian.com.github.core.usecases.impl;

import aragao.ellian.com.github.core.exceptions.InvalidModelParsedException;
import aragao.ellian.com.github.core.models.Cliente;
import aragao.ellian.com.github.core.models.Vendas;
import aragao.ellian.com.github.core.models.Vendedor;
import aragao.ellian.com.github.core.usecases.LineModelStringListener;
import aragao.ellian.com.github.core.usecases.ports.DataLineStringParserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class LineModelStringListenerImpl implements LineModelStringListener {

	private final DataLineStringParserPort lineStringParser;

	private final Consumer<Cliente> clienteConsumer;

	private final Consumer<Vendedor> vendedorConsumer;

	private final Consumer<Vendas> vendasConsumer;


	@Override
	public void onLineModelString(String lineModelString) {
		lineStringParser.whichParserFromDataLineModel(lineModelString)
				.flatMap(parser -> parser.parse(lineModelString))
				.ifPresent(this::patternMatchingObjectWithModels);
	}


	private void patternMatchingObjectWithModels(Object data) {
		if (data instanceof Cliente cliente) {
			clienteConsumer.accept(cliente);
			return;
		}
		if (data instanceof Vendedor vendedor) {
			vendedorConsumer.accept(vendedor);
			return;
		}
		if (data instanceof Vendas vendas) {
			vendasConsumer.accept(vendas);
			return;
		}
		throw new InvalidModelParsedException();
	}
}
