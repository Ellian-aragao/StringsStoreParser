package aragao.ellian.com.github.infra.adapters.queues;

import aragao.ellian.com.github.core.usecases.ListenerStringLineData;
import aragao.ellian.com.github.core.usecases.ports.ProducerStringLineDataPort;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class LineToParseQueueImpl implements ProducerStringLineDataPort, ListenerStringLineData {
	private static final Queue<String> STRING_QUEUE = new LinkedBlockingQueue<>();

	@Override
	public boolean publish(String lineToParse) {
		if (Objects.isNull(lineToParse)) {
			log.warn("can not publish null values in queue");
			return false;
		}
		return STRING_QUEUE.add(lineToParse);
	}

	@Override
	public Optional<String> listen() {
		return Optional.ofNullable(STRING_QUEUE.poll());
	}
}
