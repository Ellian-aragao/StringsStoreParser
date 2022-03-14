package aragao.ellian.com.github.parsers;

import aragao.ellian.com.github.models.Cliente;
import aragao.ellian.com.github.models.Vendas;
import aragao.ellian.com.github.models.Vendedor;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class FactoryParsers {
	private static final Pattern patternVendedor = Pattern.compile(ParsersEnum.VENDEDOR.getRegex());
	private static final Pattern patternCliente = Pattern.compile(ParsersEnum.CLIENTE.getRegex());
	private static final Pattern patternVenda = Pattern.compile(ParsersEnum.VENDA.getRegex());

	private static Parser<Vendedor> vendedorParser;
	private static Parser<Cliente> clienteParser;
	private static Parser<Vendas> vendaParser;

	public Optional<Parser> whichParser(String dataToParse) {
		if (Objects.isNull(dataToParse) || dataToParse.isBlank()) {
			return Optional.empty();
		}

		if (patternVendedor.matcher(dataToParse).matches()) {
			synchronized (patternVendedor) {
				if (Objects.isNull(vendedorParser)) {
					vendedorParser = new VendedorParser();
				}
				return Optional.of(vendedorParser);
			}
		}
		if (patternCliente.matcher(dataToParse).matches()) {
			synchronized (patternCliente) {
				if (Objects.isNull(clienteParser)) {
					clienteParser = new ClienteParser();
				}
				return Optional.of(clienteParser);
			}
		}
		if (patternVenda.matcher(dataToParse).matches()) {
			synchronized (patternVenda) {
				if (Objects.isNull(vendaParser)) {
					vendaParser = new VendaParser(new ListItemsParser(new ItemParser()));
				}
				return Optional.of(vendaParser);
			}
		}
		return Optional.empty();
	}

	private Parser teste(String dataToParse, Pattern pattern, Parser parser, Supplier<Parser> supplierParser) {
		if (pattern.matcher(dataToParse).matches()) {
			synchronized (pattern) {
				if (Objects.isNull(parser)) {
					parser = supplierParser.get();
				}
				return parser;
			}
		}
		return null;
	}

}
