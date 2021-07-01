package cz.mariskamartin.mtgi2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorServiceConfig {
    private static final Logger log = LoggerFactory.getLogger(ExecutorServiceConfig.class);

    @Bean("fixedThreadPool")
    public ExecutorService fixedThreadPool() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        log.info("Thread pool availableProcessors = {}", availableProcessors);
        return Executors.newFixedThreadPool(availableProcessors);
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