package guru.springframework.sfgjms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

// set up async scheduling
@EnableScheduling
@EnableAsync
@Configuration
public class TaskConfig {

//    @EnableScheduling
//    @EnableAsync
    // setup a bean called taskExecutor to support Async Task on periodic basis
    // help to enable task scheduling
    @Bean
    TaskExecutor taskExecutor(){
        return new SimpleAsyncTaskExecutor();
    }


}
