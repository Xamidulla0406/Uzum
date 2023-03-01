package uz.nt.uzumproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@EnableScheduling
@Configuration
@EnableAsync
public class ScheduleJob {
    @Async
    @Scheduled(fixedRate = 1000) //fixedDelay without @Async / fixedRate + @Async
    public void print() throws InterruptedException {
        Thread.sleep(10000);
        System.out.println(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()) + "->" + Thread.currentThread().getName());
    }
}