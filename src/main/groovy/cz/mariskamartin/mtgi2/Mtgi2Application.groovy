package cz.mariskamartin.mtgi2


import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class Mtgi2Application {

	static void main(String[] args) {
		SpringApplication.run(Mtgi2Application, args)
	}

}

