package aragao.ellian.com.github.services;

import java.util.List;

public interface ReadFileAndProduceService {
	List<String> readFile(String fileName);
	boolean readFileAndPublish(String fileName);
}
