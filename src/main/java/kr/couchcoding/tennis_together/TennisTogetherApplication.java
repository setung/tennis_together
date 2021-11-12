package kr.couchcoding.tennis_together;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TennisTogetherApplication {

	public static void main(String[] args) {
		SpringApplication.run(TennisTogetherApplication.class, args);
	}

}
