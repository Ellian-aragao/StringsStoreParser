package aragao.ellian.com.github;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		final var applicationContext = new ApplicationContext();
		applicationContext.initializeContexts();
		applicationContext.startServices();
		try (var scanner = new Scanner(System.in)) {
			while (!scanner.nextLine().equals("exit")) ;
			applicationContext.stopServices();
		}
	}
}
