package aragao.ellian.com.github.infra.entrypoint;

import aragao.ellian.com.github.config.ApplicationProperties;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ListFilesAvailableEntrypoint {
	private final ApplicationProperties applicationProperties;
	private final File file;

	public ListFilesAvailableEntrypoint(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
		file = new File(applicationProperties.pathInput());
	}

	public List<String> findFilesToParse() {
		return Arrays.stream(file.listFiles(this::filterWithExtention))
				.map(File::getName)
				.toList();
	}

	private boolean filterWithExtention(File fileToFilter) {
		return fileToFilter.getName()
				.endsWith(applicationProperties.extentionFiles());
	}
}
