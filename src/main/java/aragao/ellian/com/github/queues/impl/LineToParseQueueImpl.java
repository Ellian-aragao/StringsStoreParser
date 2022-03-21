package aragao.ellian.com.github.queues.impl;

import aragao.ellian.com.github.queues.LineToParseQueue;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class LineToParseQueueImpl implements LineToParseQueue {
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
