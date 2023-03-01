package uz.nt.uzumproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@EnableAsync
@Configuration
@EnableScheduling
public class ScheduleJob {
    @Async
    @Scheduled(fixedRate = 1000)
    public void print() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss ").format(new Date()) + Thread.currentThread().getName());
    }

    @Scheduled(cron = "0 0 9 20-26 * SUN")
    public void choyxona(){
        System.out.println("bugun kechga choyxona esdan chiqmasin!");
    }
}
