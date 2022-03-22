package aragao.ellian.com.github.infra.adapters.files;

import aragao.ellian.com.github.config.ApplicationProperties;
import aragao.ellian.com.github.core.usecases.ports.ReaderFilePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class BlockingIOReadFiles implements ReaderFilePort {

	private final ApplicationProperties applicationProperties;

	@Override
	public boolean readFileAndProcess(String fileInput, Consumer<String> consumerLines) {
		log.debug("File to read: {}", fileInput);
//		log.debug("Consumer is null: {}", Objects.isNull(consumerLines));
		Objects.requireNonNull(fileInput, "Can not read null path");
		Objects.requireNonNull(consumerLines, "Can not consume null function");
		try {
			log.trace("Reading file: {}", fileInput);
			final var pathname = applicationProperties.pathInput() + "/" + fileInput;
			final var file = new File(pathname);
			try (var scanner = new Scanner(file)) {
				while (scanner.hasNextLine()) {
					final var line = scanner.nextLine();
					log.debug("Processing line: {}", line);
					consumerLines.accept(line);
					log.trace("Processed line: {}", line);
				}
			}
			log.trace("Fineshed reading file: {}", fileInput);
			return true;
		} catch (IOException e) {
			log.error("Error reading file: {}", fileInput, e);
			throw new RuntimeException(e);
		}
	}
}
