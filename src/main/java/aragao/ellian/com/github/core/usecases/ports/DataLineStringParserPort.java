package aragao.ellian.com.github.core.usecases.ports;

import java.util.Optional;

public interface DataLineStringParserPort {
	Optional<ParserPort<?>> whichParserFromDataLineModel(String dataLine);
}
