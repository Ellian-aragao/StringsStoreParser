package aragao.ellian.com.github.core.usecases.impl;

import aragao.ellian.com.github.core.models.Report;
import aragao.ellian.com.github.core.usecases.WriteFileReport;
import aragao.ellian.com.github.core.usecases.ports.WriterFilePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class WriteFileReportImpl implements WriteFileReport {

	private final WriterFilePort writerFilePort;

	@Override
	public boolean write(String fileName, Report report) {
		Objects.requireNonNull(fileName);
		Objects.requireNonNull(report);
		final var reportString = report.toString();
		return writerFilePort.writeFileReport(fileName, writer -> {
			try {
				writer.write(reportString, 0, reportString.length());
			} catch (IOException e) {
				log.error("Error writing file", e);
				throw new RuntimeException(e);
			}
		});
	}
}
