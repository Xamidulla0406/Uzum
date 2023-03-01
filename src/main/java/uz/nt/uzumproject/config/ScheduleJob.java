package uz.nt.uzumproject.config;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.repository.ProductRepository;
import uz.nt.uzumproject.service.ImageService;


import java.io.*;
import java.sql.*;
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

    //    @Async
//    @Scheduled(fixedRate = 1000)
//    public void print() throws InterruptedException {
//        System.out.println(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()) + "->"+ Thread.currentThread().getName());
//        Thread.sleep(10000);
//    }

//    @Transactional
//    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
//    public void report() throws IOException {
//        List<Product> products = productRepository.findAllByAmountLessThanEqual(20);
//        File file = new File(ImageService.filePath("report", "csv"));
//        if (!file.exists() && file.createNewFile()) {
//            FileOutputStream fos = new FileOutputStream(file);
//            PrintWriter pw = new PrintWriter(fos);
//            products.forEach(p -> {
//                String product = gson.toJson(p);
//                pw.println(product);
//            });
//
//            pw.flush();
//            pw.close();
//            fos.close();
//        }
//    }


        @Transactional
    @Scheduled(fixedRate = 1000, timeUnit = TimeUnit.MINUTES)
    public void writeExcel() {
        List<Product> productList = productRepository.findAllByAmountIsBetween(25, 55);

            HSSFWorkbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Reviews");

            Row headerRow = sheet.createRow(0);

            Cell headerCell = headerRow.createCell(0);
            headerCell.setCellValue("Product Name");

            headerCell = headerRow.createCell(1);
            headerCell.setCellValue("Amount");

            headerCell = headerRow.createCell(2);
            headerCell.setCellValue("Description");

            headerCell = headerRow.createCell(3);
            headerCell.setCellValue("Price");
            int row = 1;

            for (Product product:productList) {
                headerRow=sheet.createRow(row);
                row++;

                headerRow.createCell(0).setCellValue(product.getName());

               headerRow.createCell(1).setCellValue(product.getAmount());

               headerRow.createCell(2).setCellValue(product.getDescription());

               headerRow.createCell(3).setCellValue(product.getPrice());
            }
            try {
                PrintStream printStream = new PrintStream("C:\\Users\\NuriddinBro\\Downloads\\excel.xls");
                workbook.write(printStream);
                workbook.close();
                printStream.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}
