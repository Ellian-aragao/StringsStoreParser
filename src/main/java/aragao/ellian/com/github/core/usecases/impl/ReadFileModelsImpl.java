package aragao.ellian.com.github.core.usecases.impl;

import aragao.ellian.com.github.core.exceptions.FileNameIsNullOrEmptyException;
import aragao.ellian.com.github.core.usecases.ProducerLineModelString;
import aragao.ellian.com.github.core.usecases.ReadFileModels;
import aragao.ellian.com.github.core.usecases.ports.ReaderFilePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class ReadFileModelsImpl implements ReadFileModels {

	private final ReaderFilePort readerFile;

	private final ProducerLineModelString producerLineModelsString;

	@Override
	public boolean read(String fileName) {
		if (Objects.isNull(fileName) || fileName.isBlank()) {
			log.error("File name is null or empty");
			throw new FileNameIsNullOrEmptyException(fileName);
		}
		final var stringList = readerFile.readFile(fileName);
		if (stringList.isEmpty()) {
			log.error("File is empty");
			return false;
		}
		stringList.forEach(producerLineModelsString::produceLineStringModel);
		return true;
	}
}
