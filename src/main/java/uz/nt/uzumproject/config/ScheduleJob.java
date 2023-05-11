package uz.nt.uzumproject.config;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import uz.nt.uzumproject.repository.ProductRepository;

@EnableScheduling
@Configuration
@EnableAsync
public class ScheduleJob {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Gson gson;


//    @Async
//    @Scheduled(fixedDelay = 1000)
//    public void print() throws InterruptedException {
//        Thread.sleep(10000);
//        System.out.println(new SimpleDateFormat("dd.MM.yyyy HH:dd:ss").format(new Date()) + "->" + Thread.currentThread().getName());
//    }
//    @Transactional
//    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
//    public void report() throws IOException {
//        List<Product> products = productRepository.findAllByAmountLessThanEqual(10);
//        File f = new File(ImageService.filePath("report","xlsx"));
//        if(!f.exists() && f.createNewFile()){
//            FileOutputStream fos = new FileOutputStream(f);
//            PrintWriter pw = new PrintWriter(fos);
//
//            products.forEach(p ->{
//                String product = gson.toJson(p);
//                pw.println(product);
//            });
//
//            pw.flush();
//            pw.close();
//            fos.close();
//        }
//    }
//    @Transactional
//    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
////    @Scheduled(cron = "0 6 * * *")
//    public void write() throws IOException {
//        List<Product> products = productRepository.findAllByAmountLessThanEqual(50);
//        String[] column = {"Id", "Name", "Price", "Amount", "Description", "IsAvailable"};
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet spreadsheet = workbook.createSheet("Data");
//        int cellid = 0;
////        FileOutputStream out = new FileOutputStream(ImageService.filePath("excel","xlsx"));
//        FileOutputStream out = new FileOutputStream("C:/w.xlsx");
//            Row row = spreadsheet.createRow(0);
//        for (String s : column)
//            row.createCell(cellid++).setCellValue(s);
//        for(int i = 0; i < products.size(); i++){
//            Row r = spreadsheet.createRow(i+1);
//            r.createCell(0).setCellValue(products.get(i).getId());
//            r.createCell(1).setCellValue(products.get(i).getName());
//            r.createCell(2).setCellValue(products.get(i).getPrice());
//            r.createCell(3).setCellValue(products.get(i).getAmount());
//            r.createCell(4).setCellValue(products.get(i).getDescription());
////            r.createCell(4).setCellValue(products.get(i).getIsAvailable());
//        }
//        workbook.write(out);
//        out.close();
//    }
}
