package aragao.ellian.com.github;

import aragao.ellian.com.github.config.ApplicationProperties;
import aragao.ellian.com.github.core.usecases.impl.*;
import aragao.ellian.com.github.infra.adapters.database.DatabaseInMemory;
import aragao.ellian.com.github.infra.adapters.files.BlockingIOReadFiles;
import aragao.ellian.com.github.infra.adapters.files.BlockingIOWriteFile;
import aragao.ellian.com.github.infra.adapters.parsers.FactoryParsers;
import aragao.ellian.com.github.infra.adapters.parsers.PatternStringLineDataEnum;
import aragao.ellian.com.github.infra.adapters.parsers.impl.*;
import aragao.ellian.com.github.infra.adapters.queues.LineToParseQueueImpl;
import aragao.ellian.com.github.infra.entrypoint.ListFilesAvailableEntrypoint;
import aragao.ellian.com.github.services.ConsumeFilesAndProduceToQueue;
import aragao.ellian.com.github.services.GenerateReportFromDatabase;
import aragao.ellian.com.github.services.ListenQueueAndPersists;

public class InitializerState {
	private final ConsumeFilesAndProduceToQueue consumeFilesAndProduceToQueue;
	private final ListenQueueAndPersists listenQueueAndPersists;
	private final GenerateReportFromDatabase generateReportFromDatabase;

	InitializerState() {
		ApplicationProperties.initApplicationProperties();
		final var repository = new DatabaseInMemory();
		final var factoryParsers = initializeFactoryParsers();
		final var generateReport = new GenerateReportImpl(repository, repository, repository);
		final var listenerLineModelString = new ListenerLineModelStringImpl(
				factoryParsers,
				repository::saveCliente,
				repository::saveVendedor,
				repository::saveVendas
		);
		final var lineToParseQueue = new LineToParseQueueImpl();
		final var producerLineModelString = new ProducerLineModelStringImpl(lineToParseQueue);
		final var applicationProperties = ApplicationProperties.getInstance();
		final var readFileModels = new ReadFileModelsImpl(new BlockingIOReadFiles(applicationProperties));
		final var writeFileReport = new WriteFileReportImpl(new BlockingIOWriteFile(applicationProperties));

		final var listFilesAvailableEntrypoint = new ListFilesAvailableEntrypoint(applicationProperties);

		consumeFilesAndProduceToQueue = new ConsumeFilesAndProduceToQueue(
				listFilesAvailableEntrypoint,
				readFileModels,
				producerLineModelString
		);
		listenQueueAndPersists = new ListenQueueAndPersists(
				lineToParseQueue,
				listenerLineModelString
		);
		generateReportFromDatabase = new GenerateReportFromDatabase(
				generateReport,
				writeFileReport
		);
	}

	private FactoryParsers initializeFactoryParsers() {
		final var itemParser = ItemParser.of(PatternStringLineDataEnum.ITEM);
		final var listItemsParser = ListItemsParser.of(PatternStringLineDataEnum.LISTA_ITEMS, itemParser);
		return FactoryParsers.builder()
				.withGenericEntryPatternPattern(PatternStringLineDataEnum.GENERIC_ENTRY_PATTERN.getPattern())
				.withParser(ClienteParser.of(PatternStringLineDataEnum.CLIENTE))
				.withParser(VendedorParser.of(PatternStringLineDataEnum.VENDEDOR))
				.withParser(VendaParser.of(PatternStringLineDataEnum.VENDA, listItemsParser))
				.build();
	}

	public ConsumeFilesAndProduceToQueue getConsumeFilesAndProduceToQueue() {
		return consumeFilesAndProduceToQueue;
	}

	public ListenQueueAndPersists getListenQueueAndPersists() {
		return listenQueueAndPersists;
	}

	public GenerateReportFromDatabase getGenerateReportFromDatabase() {
		return generateReportFromDatabase;
	}
}
