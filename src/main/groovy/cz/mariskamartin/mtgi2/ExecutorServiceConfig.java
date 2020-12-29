package cz.mariskamartin.mtgi2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorServiceConfig {

    @Bean("fixedThreadPool")
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(4);
    }

//    @Bean("singleThreaded")
//    public ExecutorService singleThreadedExecutor() {
//        return Executors.newSingleThreadExecutor();
//    }
//
//    @Bean("cachedThreadPool")
//    public ExecutorService cachedThreadPool() {
//        return Executors.newCachedThreadPool();
//    }
}