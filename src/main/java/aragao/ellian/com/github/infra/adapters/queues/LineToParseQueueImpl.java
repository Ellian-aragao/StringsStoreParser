package aragao.ellian.com.github.infra.adapters.queues;

import aragao.ellian.com.github.core.usecases.ports.ProducerStringLineDataPort;
import aragao.ellian.com.github.infra.entrypoint.ListenerStringLineDataEntrypoint;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class LineToParseQueueImpl implements ProducerStringLineDataPort, ListenerStringLineDataEntrypoint {
	private final Queue<String> stringLinkedBlockingQueue = new LinkedBlockingQueue<>();

	@Override
	public boolean publish(String lineToParse) {
		if (Objects.isNull(lineToParse)) {
			log.warn("can not publish null values in queue");
			return false;
		}
		return stringLinkedBlockingQueue.add(lineToParse);
	}

	@Override
	public Optional<String> listen() {
		return Optional.ofNullable(stringLinkedBlockingQueue.poll());
	}
}
