package aragao.ellian.com.github.config;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Stream;

public class ThreadExecutorsManager {
	private final ScheduledExecutorService scheduledExecutorService;
	private final ExecutorService executorServiceBlockinIo;

	private ThreadExecutorsManager(ScheduledExecutorService scheduledExecutorService, ExecutorService executorServiceBlockinIo) {
		this.scheduledExecutorService = Objects.requireNonNull(scheduledExecutorService);
		this.executorServiceBlockinIo = Objects.requireNonNull(executorServiceBlockinIo);
	}

	public ScheduledExecutorService scheduledExecutorService() {
		return scheduledExecutorService;
	}

	public ExecutorService executorServiceBlockinIo() {
		return executorServiceBlockinIo;
	}

	public static ThreadExecutorsManagerBuilder builder() {
		return new ThreadExecutorsManagerBuilder();
	}

	public void shutdownExecutors() {
		Stream.of(scheduledExecutorService, executorServiceBlockinIo)
				.forEach(ExecutorService::shutdown);
	}

	public static class ThreadExecutorsManagerBuilder {
		private ScheduledExecutorService scheduledExecutorService;
		private ExecutorService executorServiceBlockinIo;

		public ThreadExecutorsManagerBuilder scheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
			this.scheduledExecutorService = scheduledExecutorService;
			return this;
		}

		public ThreadExecutorsManagerBuilder executorServiceBlockinIo(ExecutorService executorServiceBlockinIo) {
			this.executorServiceBlockinIo = executorServiceBlockinIo;
			return this;
		}

		public ThreadExecutorsManager build() {
			return new ThreadExecutorsManager(scheduledExecutorService, executorServiceBlockinIo);
		}
	}
}
