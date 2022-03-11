package aragao.ellian.com.github;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Main {

	private static final String delimitador = "ç";

	private static final List<String> listStrings = List.of(
			"001ç1234567891234çPedroç50000",
			"001ç3245678865434çPauloç40000.99",
			"002ç2345675434544345çJose da SilvaçRural",
			"002ç2345675433444345çEduardo PereiraçRural",
			"003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro",
			"003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo"
	);

	public static void main(String[] args) {
		listStrings.forEach(dataToParse -> {
			System.out.println(dataToParse);
			final var strings = dataToParse.split(delimitador);
			if (Vendedor.id.equals(strings[0])) {
				final var vendedor = new Vendedor(strings[1], strings[2], new BigDecimal(strings[3]));
				System.out.println(vendedor);
			}
			if (Cliente.id.equals(strings[0])) {
				final var cliente = new Cliente(strings[1], strings[2], strings[3]);
				System.out.println(cliente);
			}
			if (Vendas.id.equals(strings[0])) {
				final var items = Arrays.stream(strings[2].replaceAll("[\\[\\]]", "").split(","))
						.map(item -> {
							final var itensAttributes = item.split("-");
							return new Item(
									itensAttributes[0],
									Integer.parseInt(itensAttributes[1]),
									new BigDecimal(itensAttributes[2])
							);
						})
						.toList();
				final var vendas = new Vendas(strings[1], items, strings[3]);
				System.out.println(vendas);
			}
		});
	}
}
