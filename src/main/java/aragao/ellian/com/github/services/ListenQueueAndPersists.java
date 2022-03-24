package aragao.ellian.com.github.services;

import aragao.ellian.com.github.core.usecases.ListenerLineModelString;
import aragao.ellian.com.github.infra.entrypoint.ListenerStringLineDataEntrypoint;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ListenQueueAndPersists implements Runnable {
	private final ScheduledExecutorService scheduledExecutorService;
	private final ListenerStringLineDataEntrypoint listenerStringLineDataEntrypoint;
	private final ListenerLineModelString listenerLineModelString;

	public ListenQueueAndPersists(ScheduledExecutorService executorService, ListenerStringLineDataEntrypoint listenerStringLineDataEntrypoint, ListenerLineModelString listenerLineModelString) {
		this.scheduledExecutorService = Objects.requireNonNull(executorService);
		this.listenerStringLineDataEntrypoint = Objects.requireNonNull(listenerStringLineDataEntrypoint);
		this.listenerLineModelString = Objects.requireNonNull(listenerLineModelString);
	}

	@Override
	public void run() {
		scheduledExecutorService.scheduleWithFixedDelay(this::listenQueueAndPersists, 3, 3, SECONDS);
	}

	private void listenQueueAndPersists() {
		for (Optional<String> listen; (listen = listenerStringLineDataEntrypoint.listen()).isPresent(); ) {
			listen.ifPresent(listenerLineModelString::onLineModelString);
		}
	}
}
