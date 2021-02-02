package com.fyself.post;

import com.fyself.post.configuration.initializers.CriteriaExecutorInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages= "com.fyself.post.configuration")
public class Main {
	public static String VERSION = Main.class.getPackage().getImplementationVersion();
	public static String TITLE = Main.class.getPackage().getImplementationTitle();

	public static void main(String[] args) {
		System.out.println("Search Microservice v3");
		SpringApplication application = new SpringApplication(Main.class);
		setInitializers(application);
		application.run(args);
	}

	private static void setInitializers(SpringApplication application) {
		application.addListeners(new CriteriaExecutorInitializer());
	}
}
