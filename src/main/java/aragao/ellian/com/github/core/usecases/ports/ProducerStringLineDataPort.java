package aragao.ellian.com.github.core.usecases.ports;

public interface ProducerStringLineDataPort {
	boolean publish(String lineToParse);
}
