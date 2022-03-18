package aragao.ellian.com.github.queues.impl;

import aragao.ellian.com.github.queues.LineToParseQueue;
import lombok.extern.java.Log;

import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Log
public class LineToParseQueueImpl implements LineToParseQueue {
	private static final Queue<String> STRING_QUEUE = new LinkedBlockingQueue<>();

	@Override
	public boolean publish(String lineToParse) {
		if (Objects.isNull(lineToParse)) {
			log.warning("can not publish null values in queue");
			return false;
		}
		return STRING_QUEUE.add(lineToParse);
	}

	@Override
	public Optional<String> listen() {
		return Optional.ofNullable(STRING_QUEUE.poll());
	}
}
