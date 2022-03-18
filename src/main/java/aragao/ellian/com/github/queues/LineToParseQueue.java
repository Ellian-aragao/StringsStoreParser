package aragao.ellian.com.github.queues;

import java.util.Optional;

public interface LineToParseQueue {
	boolean publish(String lineToParse);
	Optional<String> listen();
}
