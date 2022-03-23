package aragao.ellian.com.github.services;

import aragao.ellian.com.github.core.usecases.ProducerLineModelString;
import aragao.ellian.com.github.core.usecases.ReadFileModels;
import aragao.ellian.com.github.infra.entrypoint.ListFilesAvailableEntrypoint;

import java.util.List;
import java.util.Objects;

public class ConsumeFilesAndProduceToQueue {
	private final ListFilesAvailableEntrypoint listFilesAvailableEntrypoint;
	private final ReadFileModels readFileModels;
	private final ProducerLineModelString producerLineModelString;

	public ConsumeFilesAndProduceToQueue(ListFilesAvailableEntrypoint listFilesAvailableEntrypoint, ReadFileModels readFileModels, ProducerLineModelString producerLineModelString) {
		this.listFilesAvailableEntrypoint = Objects.requireNonNull(listFilesAvailableEntrypoint);
		this.readFileModels = Objects.requireNonNull(readFileModels);
		this.producerLineModelString = Objects.requireNonNull(producerLineModelString);
	}


	public void consumeFilesAndProduceToQueue() {
		listFilesAvailableEntrypoint.findFilesToParse()
				.stream()
				.map(readFileModels::read)
				.flatMap(List::stream)
				.forEach(producerLineModelString::produceLineStringModel);
	}
}
