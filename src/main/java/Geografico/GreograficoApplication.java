package Geografico;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.logging.Logger;

@SpringBootApplication
@EnableAsync
@EnableScheduling

public class GreograficoApplication{
	private static final Logger log = Logger.getLogger(GreograficoApplication.class.getName());

	public static void main(String[] args) {
		new SpringApplicationBuilder(GreograficoApplication.class).run(args);
	}
}
