package aragao.ellian.com.github.infra.entrypoint;

import aragao.ellian.com.github.config.ApplicationProperties;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class ListFilesAvailableEntrypoint {
	private final ApplicationProperties applicationProperties;
	private final File file;

	public ListFilesAvailableEntrypoint(ApplicationProperties applicationProperties) {
		this.applicationProperties = Objects.requireNonNull(applicationProperties);
		file = new File(applicationProperties.pathInput());
	}

	public Stream<String> findFilesToParse() {
		return Arrays.stream(file.listFiles(this::filterWithExtention))
				.map(File::getName);
	}

	private boolean filterWithExtention(File fileToFilter) {
		return fileToFilter.getName()
				.endsWith(applicationProperties.extentionFiles());
	}
}
