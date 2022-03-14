package aragao.ellian.com.github;

import aragao.ellian.com.github.parsers.*;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

	private static final String delimitador = "ç";

//	private static final List<String> listStrings = List.of(
//			"001ç1234567891234çPedroç50000",
//			"001ç3245678865434çPauloç40000.99",
//			"002ç2345675434544345çJose da SilvaçRural",
//			"002ç2345675433444345çEduardo PereiraçRural",
//			"003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro",
//			"003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo"
//	);

	public static void main(String[] args) {
		final var listStrings = readFile("in.dat");
		listStrings.forEach(dataToParse -> {
			System.out.println(dataToParse);
			final var parse = new FactoryParsers().whichParser(dataToParse);
			parse.map(parser -> parser.parse(dataToParse)).ifPresent(System.out::println);
		});
	}

	private static List<String> readFile(String fileName) {
		try {
			final var lines = new LinkedList<String>();
			final var file = new File(fileName);
			final var scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				final var line = scanner.nextLine();
				lines.add(line);
			}
			scanner.close();
			return lines;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
