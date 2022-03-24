package aragao.ellian.com.github;

import aragao.ellian.com.github.config.ApplicationProperties;
import aragao.ellian.com.github.config.ThreadExecutorsManager;
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
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.Executors;

public class ApplicationContext {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(ApplicationContext.class);
	private ThreadExecutorsManager threadExecutorsManager;

	private List<Runnable> services;


	public void initializeContexts() {
		log.info("Initializing contexts");
		ApplicationProperties.initApplicationProperties();
		final var applicationProperties = ApplicationProperties.getInstance();

		threadExecutorsManager = ThreadExecutorsManager.builder()
				.scheduledExecutorService(Executors.newScheduledThreadPool(applicationProperties.threadsToScheduledProcess()))
				.executorServiceBlockinIo(Executors.newFixedThreadPool(applicationProperties.threadsToIO()))
				.build();

		final var repository = new DatabaseInMemory();
		final var generateReport = new GenerateReportImpl(repository, repository, repository);
		final var listenerLineModelString = new ListenerLineModelStringImpl(
				initializeFactoryParsers(),
				repository::saveCliente,
				repository::saveVendedor,
				repository::saveVendas
		);
		final var lineToParseQueue = new LineToParseQueueImpl();
		final var producerLineModelString = new ProducerLineModelStringImpl(lineToParseQueue);

		final var readFileModels = new ReadFileModelsImpl(new BlockingIOReadFiles(applicationProperties));
		final var writeFileReport = new WriteFileReportImpl(new BlockingIOWriteFile(applicationProperties));

		final var listFilesAvailableEntrypoint = new ListFilesAvailableEntrypoint(applicationProperties);

		final var consumeFilesAndProduceToQueue = new ConsumeFilesAndProduceToQueue(
				threadExecutorsManager.executorServiceBlockinIo(),
				listFilesAvailableEntrypoint,
				readFileModels,
				producerLineModelString
		);
		final var listenQueueAndPersists = new ListenQueueAndPersists(
				threadExecutorsManager.scheduledExecutorService(),
				lineToParseQueue,
				listenerLineModelString
		);
		final var generateReportFromDatabase = new GenerateReportFromDatabase(
				threadExecutorsManager.scheduledExecutorService(),
				generateReport,
				writeFileReport
		);

		services = List.of(consumeFilesAndProduceToQueue, listenQueueAndPersists, generateReportFromDatabase);
		log.trace("Contexts initialized");
	}


	private FactoryParsers initializeFactoryParsers() {
		log.trace("Initializing factory parsers");
		final var itemParser = ItemParser.of(PatternStringLineDataEnum.ITEM);
		final var listItemsParser = ListItemsParser.of(PatternStringLineDataEnum.LISTA_ITEMS, itemParser);
		return FactoryParsers.builder()
				.withGenericEntryPatternPattern(PatternStringLineDataEnum.GENERIC_ENTRY_PATTERN.getPattern())
				.withParser(ClienteParser.of(PatternStringLineDataEnum.CLIENTE))
				.withParser(VendedorParser.of(PatternStringLineDataEnum.VENDEDOR))
				.withParser(VendaParser.of(PatternStringLineDataEnum.VENDA, listItemsParser))
				.build();
	}

	public void startServices() {
		log.info("Starting services");
		services.forEach(Runnable::run);
	}

	public void stopServices() {
		log.info("Stopping services");
		threadExecutorsManager.shutdownExecutors();
	}
}
