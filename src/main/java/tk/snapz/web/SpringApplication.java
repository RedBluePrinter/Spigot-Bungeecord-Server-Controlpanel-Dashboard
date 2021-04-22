package tk.snapz.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"tk.snapz.server.rest","tk.snapz.web"})
@SpringBootApplication
public class SpringApplication {

	public static void start(String[] args) {
		org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
	}

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
	}

}
