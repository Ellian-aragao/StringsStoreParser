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
public class BlockingIOWriteFile implements WriterFilePort {

	private final ApplicationProperties applicationProperties;

	@Override
	public boolean writeFileReport(String fileOutput, Consumer<Writer> writerConsumer) {
		log.debug("File name output: {}", fileOutput);
		log.debug("Is WriteFileReport null: {}", Objects.isNull(writerConsumer));
		Objects.requireNonNull(fileOutput);
		Objects.requireNonNull(writerConsumer);
		try {
			final var pathname = applicationProperties.pathOutput() + "/" + fileOutput + applicationProperties.processFileEndsName() + applicationProperties.extentionFiles();
			log.debug("Pathname: {}", pathname);
			final var fileToWrite = createIfNotExistsFileOutputDir(pathname);
			try (var out = new FileWriter(fileToWrite, true)) {
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

	private File createIfNotExistsFileOutputDir(String pathName) {
		final var pathOutput = new File(applicationProperties.pathOutput());
		final var fileToWrite = new File(pathName);
		if (!pathOutput.exists()) {
			pathOutput.mkdir();
			try {
				fileToWrite.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException("Error creating file to write", e);
			}
		}
		return fileToWrite;
	}
}
