package aragao.ellian.com.github.services;

import aragao.ellian.com.github.models.Report;

public interface WriteFileReportService {
	boolean writeFileReport(String fileInput, Report report);
}
