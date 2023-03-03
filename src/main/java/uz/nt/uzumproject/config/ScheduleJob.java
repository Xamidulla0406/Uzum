package uz.nt.uzumproject.config;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xslf.usermodel.XSLFSheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.repository.ProductRepository;
import uz.nt.uzumproject.service.ImageService;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableScheduling
@Configuration
@EnableAsync
public class ScheduleJob {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    Gson gson;
//    @Async
//    @Scheduled(fixedRate = 1000)
//    public void print() throws InterruptedException {
//        System.out.println("start");
//        Thread.sleep(2000);
//        System.out.println(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())+":"+Thread.currentThread().getName());
//        System.out.println("end ");
//    }
    //.txt filega yozish
//    @Transactional
//    @Scheduled(fixedDelay = 1 , timeUnit = TimeUnit.MINUTES)
//    public void report() throws IOException {
//        List<Product> products = productRepository.findAllByAmountLessThanEqual(100);
//        File file = new File(ImageService.filePath("report", ".txt"));
//        if(!file.exists() && file.createNewFile()){
//            PrintWriter printWriter = new PrintWriter(new FileOutputStream(file));
//            products.forEach(p->{
//                String pr = gson.toJson(p);
//                printWriter.println(pr);
//            });
//            printWriter.flush();
//            printWriter.close();
//        }
//    }
    // Exelga yozish
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void reportExel() throws IOException {
        List<Product> products = productRepository.findAllByAmountLessThanEqual(100);
        File file = new File(ImageService.filePath("report", ".xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("product");
        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("Id");
        row.createCell(1).setCellValue("Name");
        row.createCell(2).setCellValue("Price");
        row.createCell(3).setCellValue("Amount");
        row.createCell(4).setCellValue("Description");
        row.createCell(5).setCellValue("CategoryId");


            for (int i=0; i<products.size();i++) {
                row = sheet.createRow(i+1);
                row.createCell(0).setCellValue(products.get(i).getId());
                row.createCell(1).setCellValue(products.get(i).getName());
                row.createCell(2).setCellValue(products.get(i).getPrice());
                row.createCell(3).setCellValue(products.get(i).getAmount());
                row.createCell(4).setCellValue(products.get(i).getDescription());
                row.createCell(5).setCellValue(products.get(i).getCategory().getName());
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.close();
       }

}
