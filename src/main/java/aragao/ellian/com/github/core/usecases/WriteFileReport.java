package aragao.ellian.com.github.core.usecases;

import aragao.ellian.com.github.core.models.Report;

public interface WriteFileReport {
	boolean write(String fileName, Report report);
}
