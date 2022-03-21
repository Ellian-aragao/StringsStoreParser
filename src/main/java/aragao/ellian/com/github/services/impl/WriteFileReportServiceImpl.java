package aragao.ellian.com.github.services.impl;

import aragao.ellian.com.github.models.Report;
import aragao.ellian.com.github.services.WriteFileReportService;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class WriteFileReportServiceImpl implements WriteFileReportService {

	@Override
	public boolean writeFileReport(String fileInput, Report report) {
		try {
			try (final var out = new FileWriter(fileInput + ".done.dat")) {
				try (final var bufferedWriter = new PrintWriter(out)) {
					bufferedWriter.write(report.toString());
				}
			}
		} catch (IOException e) {
			log.error("Error writing file", e);
			return false;
		}
		return true;
	}

	public List<String> readFile(String fileName) {
		try {
			final var lines = new LinkedList<String>();
			final var file = new File(fileName);
			try (final var scanner = new Scanner(file)) {
				while (scanner.hasNextLine()) {
					final var line = scanner.nextLine();
					lines.add(line);
				}
			}
			return lines;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
