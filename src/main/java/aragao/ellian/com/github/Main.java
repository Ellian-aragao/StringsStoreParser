package aragao.ellian.com.github;

import aragao.ellian.com.github.config.ApplicationProperties;
import aragao.ellian.com.github.core.repository.ModelsRepository;
import aragao.ellian.com.github.core.usecases.*;
import aragao.ellian.com.github.core.usecases.impl.GenerateReportImpl;
import aragao.ellian.com.github.core.usecases.impl.LineModelStringListenerImpl;
import aragao.ellian.com.github.core.usecases.impl.ProducerLineModelStringImpl;
import aragao.ellian.com.github.core.usecases.impl.ReadFileModelsImpl;
import aragao.ellian.com.github.core.usecases.ports.ProducerStringLineDataPort;
import aragao.ellian.com.github.core.usecases.ports.ReaderFilePort;
import aragao.ellian.com.github.infra.adapters.database.DatabaseInMemory;
import aragao.ellian.com.github.infra.adapters.files.BlockingIOReadFiles;
import aragao.ellian.com.github.infra.adapters.parsers.FactoryParsers;
import aragao.ellian.com.github.infra.adapters.parsers.impl.*;
import aragao.ellian.com.github.infra.adapters.queues.LineToParseQueueImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class Main {

	public static final ReaderFilePort BLOCKING_IO_READ_FILES;
	private static final FactoryParsers FACTORY_PARSERS = new FactoryParsers(
			new VendedorParser(),
			new ClienteParser(),
			new VendaParser(new ListItemsParser(new ItemParser()))
	);
	private static final ModelsRepository DATABASE_IN_MEMORY = new DatabaseInMemory();
	private static final GenerateReport GENERATE_REPORT = new GenerateReportImpl(DATABASE_IN_MEMORY, DATABASE_IN_MEMORY, DATABASE_IN_MEMORY);
	private static final LineModelStringListener LINE_MODEL_STRING_LISTENER = new LineModelStringListenerImpl(FACTORY_PARSERS, DATABASE_IN_MEMORY::saveCliente, DATABASE_IN_MEMORY::saveVendedor, DATABASE_IN_MEMORY::saveVendas);
	private static final ListenerStringLineData LISTENER_STRING_LINE_DATA;
	private static final ReadFileModels READ_FILE_MODELS;
	private static final ProducerLineModelString PRODUCER_LINE_MODEL_STRING;


	static {
		ApplicationProperties.initApplicationProperties();
		BLOCKING_IO_READ_FILES = new BlockingIOReadFiles(ApplicationProperties.getInstance());
		final var lineToParseQueue = new LineToParseQueueImpl();
		LISTENER_STRING_LINE_DATA = lineToParseQueue;
		PRODUCER_LINE_MODEL_STRING = new ProducerLineModelStringImpl(lineToParseQueue);
		READ_FILE_MODELS = new ReadFileModelsImpl(new BlockingIOReadFiles(ApplicationProperties.getInstance()), PRODUCER_STRING_LINE_DATA);
	}

	public static void main(String[] args) {
		while (true) {
			consumeFilesProcess();
			consumeQueueData();
			consumeDatabaseToProduceReport();
		}
	}

	private static void consumeDatabaseToProduceReport() {
		final var report = GENERATE_REPORT.generateReport();
		READ_FILE_MODELS.read("flat_out_file");
	}

	private static void consumeQueueData() {
		var listen = LISTENER_STRING_LINE_DATA.listen();
		while (listen.isPresent()) {
			listen.ifPresent(LINE_MODEL_STRING_LISTENER::onLineModelString);
			listen = LISTENER_STRING_LINE_DATA.listen();
		}
	}

	private static void consumeFilesProcess() {
		final var instance = ApplicationProperties.getInstance();
		final var listFiles = new File(instance.pathInput())
				.listFiles(file -> file.getName().endsWith(instance.extentionFiles()));
		Arrays.stream(listFiles).forEach(file -> BLOCKING_IO_READ_FILES
				.readFileAndProcess(file.getName(), PRODUCER_STRING_LINE_DATA::publish));
	}
}
