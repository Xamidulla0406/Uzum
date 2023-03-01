package uz.nt.uzumproject.config;

import jakarta.transaction.Transactional;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
import java.util.concurrent.TimeUnit;

@EnableScheduling
@Component
@EnableAsync
public class ScheduleJob {

    @Autowired
    private ProductRepository productRepository;

//    @Async
//    @Scheduled(fixedRate = 1000) //fixedDelay without @Async / fixedRate + @Async
//    public void print() throws InterruptedException {
//        System.out.println(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()) + "->" + Thread.currentThread().getName());
//        Thread.sleep(1000*60*60*24);
//    }

    @Transactional
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
    public void report() throws IOException {
        List<Product> products = productRepository.findAllByAmountLessThanEqual(50);
        File file = new File(ImageService.filePath("report", ".xls"));
        String[] column = {"id", "name", "price", "amount", "description", "isAvailable"};

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("Product");
        Row row = sheet.createRow(0);

        for (int i = 0; i < column.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(column[i]);
        }

        for (int i = 0; i < products.size(); i++) {
            Row dataRow = sheet.createRow(i + 1);
            dataRow.createCell(0).setCellValue(products.get(i).getId());
            dataRow.createCell(1).setCellValue(products.get(i).getName());
            dataRow.createCell(2).setCellValue(products.get(i).getPrice());
            dataRow.createCell(3).setCellValue(products.get(i).getAmount());
            dataRow.createCell(4).setCellValue(products.get(i).getDescription());
            dataRow.createCell(5).setCellValue(products.get(i).getIsAvailable().toString());
        }

        FileOutputStream fos = new FileOutputStream(file);

        workbook.write(fos);
        fos.close();
    }
}