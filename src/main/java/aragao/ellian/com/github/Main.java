package aragao.ellian.com.github;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

	public static void main(String[] args) {
		final var initializerState = new InitializerState();
		initializerState.getConsumeFilesAndProduceToQueue().consumeFilesAndProduceToQueue();
		initializerState.getListenQueueAndPersists().listenQueueAndPersists();
		initializerState.getGenerateReportFromDatabase().generateReportFromDatabase();
	}

}
