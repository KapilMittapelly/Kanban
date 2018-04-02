package kanban;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KanbanApplication {

	public static void main(String[] args) {
		System.getProperty("user.dir");
		SpringApplication.run(KanbanApplication.class, args);
	}
}
