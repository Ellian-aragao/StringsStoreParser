package aragao.ellian.com.github.infra.adapters.files;

import aragao.ellian.com.github.config.ApplicationProperties;
import aragao.ellian.com.github.core.usecases.ports.WriterFilePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class BlockingIOWriteReportServiceImpl implements WriterFilePort {

	@Override
	public boolean writeFileReport(String fileOutput, Consumer<Writer> writerConsumer) {
		log.debug("File name output: {}", fileOutput);
		log.debug("Is WriteFileReport null: {}", Objects.isNull(writerConsumer));
		Objects.requireNonNull(fileOutput);
		Objects.requireNonNull(writerConsumer);
		try {
			final var pathname = applicationProperties.pathOutput() + "/" + fileOutput + applicationProperties.processFileEndsName() + applicationProperties.extentionFiles();
			existsFileOutputDir();
			log.debug("Pathname: {}", pathname);
			try (var out = new FileWriter(pathname, true)) {
				try (var printWriter = new PrintWriter(out)) {
					writerConsumer.accept(printWriter);
				}
			}
		} catch (IOException e) {
			log.error("Error creating file to write", e);
			return false;
		}
		return true;
	}

	private final ApplicationProperties applicationProperties;

	private boolean existsFileOutputDir() {
		final var file = new File(applicationProperties.pathOutput());
		if (!file.exists()) {
			return file.mkdir();
		}
		return false;
	}
}
