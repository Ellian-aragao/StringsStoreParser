package aragao.ellian.com.github.services;

import aragao.ellian.com.github.core.usecases.ProducerLineModelString;
import aragao.ellian.com.github.core.usecases.ReadFileModels;
import aragao.ellian.com.github.infra.entrypoint.ListFilesAvailableEntrypoint;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

@Slf4j
public class ConsumeFilesAndProduceToQueue implements Runnable {

	private final ExecutorService executorService;
	private final ListFilesAvailableEntrypoint listFilesAvailableEntrypoint;
	private final ReadFileModels readFileModels;
	private final ProducerLineModelString producerLineModelString;

	public ConsumeFilesAndProduceToQueue(ExecutorService executorService, ListFilesAvailableEntrypoint listFilesAvailableEntrypoint, ReadFileModels readFileModels, ProducerLineModelString producerLineModelString) {
		this.executorService = Objects.requireNonNull(executorService);
		this.listFilesAvailableEntrypoint = Objects.requireNonNull(listFilesAvailableEntrypoint);
		this.readFileModels = Objects.requireNonNull(readFileModels);
		this.producerLineModelString = Objects.requireNonNull(producerLineModelString);
	}

	@Override
	public void run() {
		consumeFilesAndProduceToQueue();
	}

	private void consumeFilesAndProduceToQueue() {
		listFilesAvailableEntrypoint.findFilesToParse()
				.forEach(this::initProcessReadFileAsync);
	}

	private void initProcessReadFileAsync(String file) {
		executorService.execute(() -> readFileModels.read(file)
				.forEach(producerLineModelString::produceLineStringModel));
	}
}
