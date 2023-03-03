package uz.nt.uzumproject.config;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Cell;
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
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@EnableScheduling
@Configuration
@EnableAsync
public class ScheduleJob {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Gson gson;

    @Async
//    @Scheduled(fixedDelay = 1000)
//    public void print() throws InterruptedException {
//        Thread.sleep(3000);
//        System.out.println(new SimpleDateFormat("dd.MM.yyyy HH.mm.ss").format(new Date()) + " -> " + Thread.currentThread().getName());
//    }

    @Transactional
//    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void report() throws IOException {
        List<Product> products = productRepository.findAllByAmountLessThanEqual(10);

        File f = new File(ImageService.filePath("report", "csv"));
        if (!f.exists() && f.createNewFile()) {
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            PrintWriter pw = new PrintWriter(fileOutputStream);

            products.forEach(p -> {
                String product = gson.toJson(p);
                pw.println(product);
            });

            pw.flush();
            pw.close();
            fileOutputStream.close();
        }
    }

    //    @Scheduled(cron = "0 59 23 * * *")
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void excelReport() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        List<Product> products = productRepository.findAllByAmountLessThanEqual(10);
        XSSFSheet spreadsheet = workbook.createSheet();
        XSSFRow row;

        Map<String, Object[]> fewProducts = new TreeMap<String, Object[]>();
        fewProducts.put("1", new Object[]{
                "PROD ID", "PROD NAME", "PROD PRICE", "PROD AMOUNT", "PROD DESCRIPTION"});
        int currentEmpInfo = 2;

//        fewProducts = products.stream()
//                .map(product -> new Object[] { product.getId(),
//                        product.getName(),
//                        product.getPrice(),
//                        product.getAmount(),
//                        product.getDescription() })
//                .collect(Collectors.toMap(arr -> Integer.toString(currentEmpInfo.getAndIncrement()), arr -> arr));


        for (Product product : products) {
            fewProducts.put(String.valueOf(currentEmpInfo++),
                    new Object[]{product.getId(),
                            product.getName(),
                            product.getPrice(),
                            product.getAmount(),
                            product.getDescription()});
        }
        Set<String> keyid = fewProducts.keySet();
        int rowid = 0;

        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = fewProducts.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(String.valueOf(obj));
            }
        }

        FileOutputStream out = new FileOutputStream(new File(ImageService.filePath("report1", ".xlsx")));
        workbook.write(out);
        out.close();
    }
}
