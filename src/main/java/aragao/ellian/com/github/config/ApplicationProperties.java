package aragao.ellian.com.github.config;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Pattern;

@Slf4j
public record ApplicationProperties(String pathInput, String pathOutput, String extentionFiles, String processFileEndsName) {
	private static ApplicationProperties application;

	public static void initApplicationProperties() {
		if (Objects.isNull(application)) {
			log.trace("Initializing application properties");
			application = readApplicationPropertiesToApplication();
			return;
		}
		log.warn("ApplicationProperties is already initialized");
	}

	public static ApplicationProperties getInstance() {
		if (Objects.isNull(application)) {
			throw new RuntimeException("ApplicationProperties is not initialized");
		}
		return application;
	}

	private static ApplicationProperties readApplicationPropertiesToApplication() {
		try (var resourceAsStream = new FileReader("src/main/java/resource/application.properties")) {
			final var properties = new Properties();
			properties.load(resourceAsStream);
			final var hasEnvPattern = Pattern.compile("(.*)(\\$\\{(.*)})(.*)");
			final var splitEnvPattern = Pattern.compile("([${}])");
			final var processFileEndsName = properties.getProperty("process.file.ends", ".done.dat");
			final var pathRoot = properties.getProperty("process.path.root", System.getProperty("HOMEPATH"));
			final var extentionFiles = properties.getProperty("process.file.extention", ".dat");
			final var parseIn = parseStringToGetEnv(hasEnvPattern, splitEnvPattern, pathRoot + "/" + properties.getProperty("process.path.in", "data/in"));
			final var pathOut = parseStringToGetEnv(hasEnvPattern, splitEnvPattern, pathRoot + "/" + properties.getProperty("process.path.out", "data/out"));
			return new ApplicationProperties(parseIn, pathOut, extentionFiles, processFileEndsName);
		} catch (FileNotFoundException e) {
			log.error("Did not find src/main/java/resource/application.properties");
			throw new RuntimeException(e);
		} catch (IOException e) {
			log.error("Generic IO error", e);
			throw new RuntimeException(e);
		}
	}

	private static String parseStringToGetEnv(Pattern hasEnvPattern,
	                                          Pattern splitEnvPattern,
	                                          String pathIn) {
		Objects.requireNonNull(splitEnvPattern);
		Objects.requireNonNull(hasEnvPattern);
		if (!hasEnvPattern.matcher(pathIn).matches()) {
			return pathIn;
		}
		final var stringBuilder = new StringBuilder();
		final var replaceAll = splitEnvPattern.split(pathIn);
		return stringBuilder
				.append(System.getenv(replaceAll[2]))
				.append(replaceAll[3])
				.toString();
	}
}
