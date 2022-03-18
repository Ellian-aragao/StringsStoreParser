package aragao.ellian.com.github.services.impl;

import aragao.ellian.com.github.queues.LineToParseQueue;
import aragao.ellian.com.github.services.ReadFileAndProduceService;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class ReadFilesWithBlockingIOServiceImpl implements ReadFileAndProduceService {

	private final LineToParseQueue lineToParseQueue;

	@Override
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

	@Override
	public boolean readFileAndPublish(String fileName) {
		try {
			final var file = new File(fileName);
			try (final var scanner = new Scanner(file)) {
				while (scanner.hasNextLine()) {
					final var line = scanner.nextLine();
					lineToParseQueue.publish(line);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
