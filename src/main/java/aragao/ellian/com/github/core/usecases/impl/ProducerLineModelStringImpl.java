package aragao.ellian.com.github.core.usecases.impl;

import aragao.ellian.com.github.core.exceptions.StringDataLineIsNullOrEmptyException;
import aragao.ellian.com.github.core.usecases.ProducerLineModelString;
import aragao.ellian.com.github.core.usecases.ports.ProducerStringLineDataPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class ProducerLineModelStringImpl implements ProducerLineModelString {

	private final ProducerStringLineDataPort producerLineModelString;

	@Override
	public boolean produceLineStringModel(String lineData) {
		if (Objects.isNull(lineData) || lineData.isBlank()) {
			log.error("Line is null or empty");
			throw new StringDataLineIsNullOrEmptyException(lineData);
		}
		producerLineModelString.publish(lineData);
		return false;
	}
}
