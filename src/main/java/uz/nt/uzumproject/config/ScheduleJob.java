package uz.nt.uzumproject.config;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@EnableScheduling
@Component
@EnableAsync
public class ScheduleJob {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Gson gson;

//    @Profile("dev")
    @Async
    @Scheduled(fixedRate = 1000)
    public void print() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date())+"->"+Thread.currentThread().getName());
    }

//    @Transactional
//    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
//    public void report() throws IOException {
//        List<Product> products = productRepository.findAllByAmountLessThanEqual(100);
//        File f = new File(ImageService.filePath("report", "csv"));
//        if(!f.exists() && f.createNewFile()){
//            PrintWriter writer = new PrintWriter(new FileOutputStream(f));
//            products.forEach(p -> writer.println(gson.toJson(p)));
//            writer.flush();
//            writer.close();
//        }
//
//    }

    @Transactional
    @Scheduled(cron = "0 1 1 * * *")
    public void report() throws IOException {
        List<Product> products = productRepository.findAllByAmountLessThanEqual(100);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet1");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("name");
        row.createCell(2).setCellValue("description");
        row.createCell(3).setCellValue("price");
        row.createCell(4).setCellValue("amount");

        int rowNum = 1;
        for (Product product : products) {
            HSSFRow row1 = sheet.createRow(rowNum++);
            row1.createCell(0).setCellValue(product.getId());
            row1.createCell(1).setCellValue(product.getName());
            row1.createCell(2).setCellValue(product.getDescription());
            row1.createCell(3).setCellValue(product.getPrice());
            row1.createCell(4).setCellValue(product.getAmount());
        }

        File f = new File(ImageService.filePath("report", ".xls"));
        FileOutputStream fileOutputStream = new FileOutputStream(f);
        workbook.write(fileOutputStream);
        fileOutputStream.close();

    }


}
