package uz.nt.uzumproject.config;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

    @Transactional
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void report2() throws IOException {
        String[] rowHeading = {"Id", "Name", "Price", "Amount", "Description", "Category"};

        List<Product> products = productRepository.findAllByAmountLessThanEqual(10);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadSheet = workbook.createSheet(" Products ");
        Row headerRow = spreadSheet.createRow(0);

        for(int i = 0; i < rowHeading.length; i++){
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(rowHeading[i]);
        }

        for(int i = 0; i < products.size(); i++){
            Product product = products.get(i);
            Row dataRow = spreadSheet.createRow(i + 1);
            dataRow.createCell(0).setCellValue(product.getId());
            dataRow.createCell(1).setCellValue(product.getName());
            dataRow.createCell(2).setCellValue(product.getPrice());
            dataRow.createCell(3).setCellValue(product.getAmount());
            dataRow.createCell(4).setCellValue(product.getDescription());
            dataRow.createCell(5).setCellValue(product.getCategory().getName());
        }

        File f = new File(ImageService.filePath("reportExel", "csv.xlsx"));
        FileOutputStream out = new FileOutputStream(f);

        workbook.write(out);
        out.close();
    }
}
