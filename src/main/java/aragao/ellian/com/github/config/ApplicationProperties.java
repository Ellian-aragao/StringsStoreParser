package aragao.ellian.com.github.config;

import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Pattern;

public class ApplicationProperties {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(ApplicationProperties.class);
	private static ApplicationProperties application;
	private final String pathInput;
	private final String pathOutput;
	private final String extentionFiles;
	private final String processFileEndsName;
	private final Integer threadsToIO;
	private final Integer threadsToScheduledProcess;

	private ApplicationProperties(String pathInput, String pathOutput, String extentionFiles, String processFileEndsName, Integer threadsToIO, Integer threadsToScheduledProcess) {
		this.pathInput = Objects.requireNonNull(pathInput);
		this.pathOutput = Objects.requireNonNull(pathOutput);
		this.extentionFiles = Objects.requireNonNull(extentionFiles);
		this.processFileEndsName = Objects.requireNonNull(processFileEndsName);
		this.threadsToIO = Objects.requireNonNull(threadsToIO);
		this.threadsToScheduledProcess = Objects.requireNonNull(threadsToScheduledProcess);
	}

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
		log.debug("Reading application properties");
		try (var resourceAsStream = new FileReader("src/main/java/resource/application.properties")) {
			final var properties = new Properties();
			properties.load(resourceAsStream);
			log.debug("Reading application properties successfully: {}", properties);
			final var hasEnvPattern = Pattern.compile("(.*)(\\$\\{(.*)})(.*)");
			final var splitEnvPattern = Pattern.compile("([${}])");
			final var processFileEndsName = properties.getProperty("process.file.ends", ".done.dat");
			final var pathRoot = properties.getProperty("process.path.root", System.getProperty("HOMEPATH"));
			final var extentionFiles = properties.getProperty("process.file.extention", ".dat");

			final var threadsToIO = Integer.parseInt(properties.getProperty("process.threads.io", String.valueOf(Runtime.getRuntime().availableProcessors())));
			final var threadsToScheduledTasks = Integer.parseInt(properties.getProperty("process.threads.scheduled-tasks", "2"));

			final var parseIn = parseStringToGetEnv(hasEnvPattern, splitEnvPattern, pathRoot + "/" + properties.getProperty("process.path.in", "data/in"));
			final var pathOut = parseStringToGetEnv(hasEnvPattern, splitEnvPattern, pathRoot + "/" + properties.getProperty("process.path.out", "data/out"));

			return ApplicationProperties.builder()
					.pathInput(parseIn)
					.pathOutput(pathOut)
					.extentionFiles(extentionFiles)
					.processFileEndsName(processFileEndsName)
					.threadsToIO(threadsToIO)
					.threadsToScheduledProcess(threadsToScheduledTasks)
					.build();
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

	static ApplicationPropertiesBuilder builder() {
		return new ApplicationPropertiesBuilder();
	}

	public String pathInput() {
		return pathInput;
	}

	public String pathOutput() {
		return pathOutput;
	}

	public String extentionFiles() {
		return extentionFiles;
	}

	public String processFileEndsName() {
		return processFileEndsName;
	}

	public Integer threadsToIO() {
		return threadsToIO;
	}

	public Integer threadsToScheduledProcess() {
		return threadsToScheduledProcess;
	}

	private static class ApplicationPropertiesBuilder {
		private String pathInput;
		private String pathOutput;
		private String extentionFiles;
		private String processFileEndsName;
		private Integer threadsToIO;
		private Integer threadsToScheduledProcess;

		ApplicationPropertiesBuilder pathInput(String pathInput) {
			this.pathInput = pathInput;
			return this;
		}

		ApplicationPropertiesBuilder pathOutput(String pathOutput) {
			this.pathOutput = pathOutput;
			return this;
		}

		ApplicationPropertiesBuilder extentionFiles(String extentionFiles) {
			this.extentionFiles = extentionFiles;
			return this;
		}

		ApplicationPropertiesBuilder processFileEndsName(String processFileEndsName) {
			this.processFileEndsName = processFileEndsName;
			return this;
		}

		ApplicationPropertiesBuilder threadsToIO(Integer threadsToIO) {
			this.threadsToIO = threadsToIO;
			return this;
		}

		ApplicationPropertiesBuilder threadsToScheduledProcess(Integer threadsToScheduledProcess) {
			this.threadsToScheduledProcess = threadsToScheduledProcess;
			return this;
		}

		ApplicationProperties build() {
			return new ApplicationProperties(pathInput, pathOutput, extentionFiles, processFileEndsName, threadsToIO, threadsToScheduledProcess);
		}
	}
}
