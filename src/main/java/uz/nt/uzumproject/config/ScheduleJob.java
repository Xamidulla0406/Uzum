package uz.nt.uzumproject.config;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

import java.io.*;
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
    @Scheduled(fixedRate = 24,timeUnit = TimeUnit.HOURS)
    public void print() throws InterruptedException {
        System.out.println(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()) + "->"+ Thread.currentThread().getName());
        Thread.sleep(10000);
    }

    @Transactional
    @Scheduled(fixedDelay = 24, timeUnit = TimeUnit.HOURS)
    public void report() throws IOException {
        List<Product> products = productRepository.findAllByAmountLessThanEqual(50);
        File f = new File(ImageService.filePath("report", ".csv"));
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
    @Scheduled(fixedDelay = 24, timeUnit = TimeUnit.HOURS)
    public void toExel() throws IOException {
        List<Product> products = productRepository.findAllByAmountLessThanEqual(50);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Java");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Name");
        row.createCell(2).setCellValue("Amount");
        row.createCell(3).setCellValue("Description");
        row.createCell(4).setCellValue("Is-Available");
        row.createCell(5).setCellValue("price");
        row.createCell(6).setCellValue("category_id");


//        System
        int index = 0;
        for (Product product : products) {
            HSSFRow row1 = sheet.createRow(index);
            row1.createCell(0).setCellValue(product.getId());
            row1.createCell(1).setCellValue(product.getName());
            row1.createCell(2).setCellValue(product.getAmount());
            row1.createCell(3).setCellValue(product.getDescription());
            row1.createCell(4).setCellValue(product.getIsAvailable());
            row1.createCell(5).setCellValue(product.getPrice());
            row1.createCell(6).setCellValue(product.getCategory().getId());
            index++;
        }

        File f = new File(ImageService.filePath("Product", ".xls"));
        FileOutputStream fos = new FileOutputStream(f);
        workbook.write(fos);
        workbook.close();
        fos.close();


    }
}
