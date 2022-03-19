package aragao.ellian.com.github;

import aragao.ellian.com.github.database.Database;
import aragao.ellian.com.github.database.ModelsRepository;
import aragao.ellian.com.github.parsers.FactoryParsers;
import aragao.ellian.com.github.parsers.impl.*;
import aragao.ellian.com.github.queues.LineToParseQueue;
import aragao.ellian.com.github.queues.impl.LineToParseQueueImpl;
import aragao.ellian.com.github.services.GenerateReportService;
import aragao.ellian.com.github.services.ParseLineProducerService;
import aragao.ellian.com.github.services.ReadFileAndProduceService;
import aragao.ellian.com.github.services.impl.GenerateReportServiceImpl;
import aragao.ellian.com.github.services.impl.ParseLineProducerServiceImpl;
import aragao.ellian.com.github.services.impl.ReadFilesWithBlockingIOServiceImpl;

import java.util.List;

public class Main {

	private static final FactoryParsers factoryParsers = new FactoryParsers(
			new VendedorParser(),
			new ClienteParser(),
			new VendaParser(new ListItemsParser(new ItemParser()))
	);
	private static final ReadFileAndProduceService readFilesWithBlockingIOService = new ReadFilesWithBlockingIOServiceImpl(
			new LineToParseQueueImpl()
	);
	private static final ModelsRepository database = new Database();
	private static final GenerateReportService generateReportService = new GenerateReportServiceImpl(database);
	private static final ParseLineProducerService PARSE_LINE_PRODUCER_SERVICE = new ParseLineProducerServiceImpl(factoryParsers, database);
	private static final LineToParseQueue lineToParseQueue = new LineToParseQueueImpl();

	private static final List<String> listStrings = List.of(
			"001ç1234567891234çPedroç50000",
			"001ç3245678865434çPauloç40000.99",
			"002ç2345675434544345çJose da SilvaçRural",
			"002ç2345675433444345çEduardo PereiraçRural",
			"003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro",
			"003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo"
	);
	private static final String fileName = System.getenv("HOMEPATH") + "/data/in/in.dat";

	public static void routine1() {
		final var listStrings = readFilesWithBlockingIOService.readFile(fileName);
		listStrings.forEach(dataToParse -> {
			System.out.println(dataToParse);
			final var parse = factoryParsers.whichParser(dataToParse);
			parse.map(parser -> parser.parse(dataToParse)).ifPresent(System.out::println);
		});
	}

	public static void main(String[] args) {
		if (!readFilesWithBlockingIOService.readFileAndPublish(fileName)) {
			throw new RuntimeException("erro na fila");
		}
		var listen = lineToParseQueue.listen();
		while (listen.isPresent()) {
			listen.ifPresent(PARSE_LINE_PRODUCER_SERVICE::parseLineAndPersist);
			listen = lineToParseQueue.listen();
		}
		System.out.println(database);
		System.out.println("REPORT:\n" + generateReportService.generateReport());
	}
}
