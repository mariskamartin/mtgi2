package cz.mariskamartin.mtgi2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {

    @Bean
    public Gson getGson() {
        return new GsonBuilder().create();
    }
}
