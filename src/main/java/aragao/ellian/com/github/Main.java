package aragao.ellian.com.github;

import aragao.ellian.com.github.models.Cliente;
import aragao.ellian.com.github.models.Item;
import aragao.ellian.com.github.models.Vendas;
import aragao.ellian.com.github.models.Vendedor;

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
			if (Vendedor.getId().equals(strings[0])) {
				final var vendedor = Vendedor.builder()
						.withCpf(strings[1])
						.withName(strings[2])
						.withSalary(strings[3])
						.build();
				System.out.println(vendedor);
			}
			if (Cliente.getId().equals(strings[0])) {
				final var cliente = Cliente.builder()
						.withCnpj(strings[1])
						.withName(strings[2])
						.withBusinessArea(strings[3])
						.build();
				System.out.println(cliente);
			}
			if (Vendas.getId().equals(strings[0])) {
				final var items = Arrays.stream(strings[2].replaceAll("[\\[\\]]", "").split(","))
						.map(item -> {
							final var itensAttributes = item.split("-");
							return Item.builder()
									.withId(itensAttributes[0])
									.withQuantity(itensAttributes[1])
									.withPrice(itensAttributes[2])
									.build();
						})
						.toList();
				final var vendas = Vendas.builder()
						.withSaleId(strings[1])
						.withItems(items)
						.withSalesmanName(strings[3])
						.build();
				System.out.println(vendas);
			}
		});
	}
}
