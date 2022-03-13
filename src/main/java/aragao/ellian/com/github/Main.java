package aragao.ellian.com.github;

import aragao.ellian.com.github.parser.*;

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
			new VendaParser(new ItemsParser(new ItemParser())).parse(strings).ifPresent(System.out::println);
			new ClienteParser().parse(strings).ifPresent(System.out::println);
			new VendedorParser().parse(strings).ifPresent(System.out::println);
		});
	}
}
