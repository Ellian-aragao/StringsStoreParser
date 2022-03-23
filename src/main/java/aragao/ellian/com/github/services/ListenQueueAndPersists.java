package aragao.ellian.com.github.services;

import aragao.ellian.com.github.core.usecases.ListenerLineModelString;
import aragao.ellian.com.github.infra.entrypoint.ListenerStringLineDataEntrypoint;

import java.util.Objects;
import java.util.Optional;

public class ListenQueueAndPersists {
	private final ListenerStringLineDataEntrypoint listenerStringLineDataEntrypoint;
	private final ListenerLineModelString listenerLineModelString;

	public ListenQueueAndPersists(ListenerStringLineDataEntrypoint listenerStringLineDataEntrypoint, ListenerLineModelString listenerLineModelString) {
		this.listenerStringLineDataEntrypoint = Objects.requireNonNull(listenerStringLineDataEntrypoint);
		this.listenerLineModelString = Objects.requireNonNull(listenerLineModelString);
	}


	public void listenQueueAndPersists() {
		for (Optional<String> listen; (listen = listenerStringLineDataEntrypoint.listen()).isPresent(); ) {
			listen.ifPresent(listenerLineModelString::onLineModelString);
		}
	}
}
