package aragao.ellian.com.github.services;

import aragao.ellian.com.github.core.usecases.GenerateReport;
import aragao.ellian.com.github.core.usecases.WriteFileReport;

import java.util.Objects;

public class GenerateReportFromDatabase {
	private final GenerateReport generateReport;
	private final WriteFileReport writeFileReport;

	public GenerateReportFromDatabase(GenerateReport generateReport, WriteFileReport writeFileReport) {
		this.generateReport = Objects.requireNonNull(generateReport);
		this.writeFileReport = Objects.requireNonNull(writeFileReport);
	}

	public void generateReportFromDatabase() {
		final var report = generateReport.generateReport();
		writeFileReport.write("flat_out_file", report);
	}
}
