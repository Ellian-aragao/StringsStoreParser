package aragao.ellian.com.github.services;

import aragao.ellian.com.github.core.usecases.GenerateReport;
import aragao.ellian.com.github.core.usecases.WriteFileReport;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class GenerateReportFromDatabase implements Runnable {

	private final ScheduledExecutorService executorService;
	private final GenerateReport generateReport;
	private final WriteFileReport writeFileReport;

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss_SSS");

	public GenerateReportFromDatabase(ScheduledExecutorService executorService, GenerateReport generateReport, WriteFileReport writeFileReport) {
		this.executorService = Objects.requireNonNull(executorService);
		this.generateReport = Objects.requireNonNull(generateReport);
		this.writeFileReport = Objects.requireNonNull(writeFileReport);
	}

	@Override
	public void run() {
		executorService.scheduleAtFixedRate(this::generateReportFromDatabase, 5, 5, SECONDS);
	}

	private void generateReportFromDatabase() {
		final var report = generateReport.generateReport();
		writeFileReport.write(formatter.format(LocalDateTime.now()), report);
	}
}
