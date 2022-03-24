package aragao.ellian.com.github;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class Main {

	public static void main(String[] args) {
		final var applicationContext = new ApplicationContext();
		applicationContext.initializeContexts();
		applicationContext.startServices();
		new Scanner(System.in).nextLine();
		applicationContext.stopServices();
	}

}
