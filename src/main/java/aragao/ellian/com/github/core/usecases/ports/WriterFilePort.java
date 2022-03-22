package aragao.ellian.com.github.core.usecases.ports;

import java.io.Writer;
import java.util.function.Consumer;

public interface WriterFilePort {
	boolean writeFileReport(String fileOutput, Consumer<Writer> writerConsumer);
}
