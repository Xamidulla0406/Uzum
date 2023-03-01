package uz.nt.uzumproject.config;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.repository.ProductRepository;
import uz.nt.uzumproject.service.ImageService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableScheduling
@Component
@EnableAsync
public class ScheduleJob {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Gson gson;

    @Async
    @Scheduled(fixedRate = 1000)
    public void print() throws InterruptedException {
        System.out.println(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()) + "->"+ Thread.currentThread().getName());
        Thread.sleep(10000);
    }

    @Transactional
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void report() throws IOException {
        List<Product> products = productRepository.findAllByAmountLessThanEqual(50);
        File f = new File(ImageService.filePath("report", "csv"));
        if (f.createNewFile()){
            FileOutputStream fos = new FileOutputStream(f);
            PrintWriter pw = new PrintWriter(fos);

            products.forEach(p -> {
                String product = gson.toJson(p);
                pw.println(product);
            });

            pw.flush();
            pw.close();
            fos.close();
        }
    }
}
