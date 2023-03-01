package uz.nt.uzumproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import uz.nt.uzumproject.service.ImageService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@EnableScheduling
@Configuration
@EnableAsync
public class ScheduleJob {
    @Autowired
    private ExcelWriter excelWriter;

    @Async
    @Scheduled(fixedRate = 1000)
    public void print() throws InterruptedException {
        Thread.sleep(10000);
//        System.out.println(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + " : " + Thread.currentThread().getName());
    }

    @Scheduled(cron = "0 57 20 28 2 2")
    public void print2() {
        System.out.println("Hello");
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void report() throws IOException {
        FileOutputStream outputStream = new FileOutputStream(ImageService.filePath("report", ".xlsx"));

        excelWriter.writeHeaderLine();
        excelWriter.writeDataLines();
        excelWriter.workbook.write(outputStream);
        excelWriter.workbook.close();
    }

}
