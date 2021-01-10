package cz.mariskamartin.mtgi2

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class Mtgi2Application {

	static void main(String[] args) {
		SpringApplication.run(Mtgi2Application, args)
	}

	@Bean
	public Gson getGson() {
		return new GsonBuilder().create()
	}
}

