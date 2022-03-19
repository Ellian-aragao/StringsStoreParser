package aragao.ellian.com.github.services.impl;

import aragao.ellian.com.github.database.ModelsRepository;
import aragao.ellian.com.github.models.Cliente;
import aragao.ellian.com.github.models.Vendas;
import aragao.ellian.com.github.models.Vendedor;
import aragao.ellian.com.github.parsers.FactoryParsers;
import aragao.ellian.com.github.services.ParseLineProducerService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ParseLineProducerServiceImpl implements ParseLineProducerService {

	private final FactoryParsers factoryParsers;

	private final ModelsRepository modelsRepository;

	@Override
	public boolean parseLineAndPersist(String line) {
		return factoryParsers.whichParser(line)
				.flatMap(parser -> parser.parse(line))
				.map(this::patternMatchingObjectWithModels)
				.orElse(false);
	}

	private Boolean patternMatchingObjectWithModels(Object data) {
		if (data instanceof Cliente cliente) {
			modelsRepository.saveCliente(cliente);
			return true;
		}
		if (data instanceof Vendas vendas) {
			modelsRepository.saveVendas(vendas);
			return true;
		}
		if (data instanceof Vendedor vendedor) {
			modelsRepository.saveVendedor(vendedor);
			return true;
		}
		return false;
	}
}
