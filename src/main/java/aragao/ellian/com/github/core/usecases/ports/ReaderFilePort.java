package aragao.ellian.com.github.core.usecases.ports;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public interface ReaderFilePort {

	boolean readFileAndProcess(String fileInput, Consumer<String> consumerLines);

	default List<String> readFile(String filePath) {
		final var listStrings = new LinkedList<String>();
		readFileAndProcess(filePath, listStrings::add);
		return listStrings;
	}
}
