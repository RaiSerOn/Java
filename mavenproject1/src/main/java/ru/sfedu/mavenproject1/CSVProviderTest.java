package ru.sfedu.mavenproject1;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.log4j.Logger;
import ru.sfedu.mavenproject1.model.DataProviderCSV;
import ru.sfedu.mavenproject1.model.entities.CSVEntities.Model;
import ru.sfedu.mavenproject1.model.entities.CSVEntities.User;
import ru.sfedu.mavenproject1.model.entities.TypeOfEntities;

public class CSVProviderTest {
    
    private static Logger log = Logger.getLogger(CSVProviderTest.class);
    
    public CSVProviderTest(){
        log.debug("CSVProviderTest[0]: starting application.........");
    }
    
    public void logBasicSystemInfo() throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        log.info("Launching the application...");
        log.info("Operating System: " + System.getProperty("os.name") + " "+ System.getProperty("os.version"));
        log.info("JRE: " + System.getProperty("java.version"));
        log.info("Java Launched From: " + System.getProperty("java.home"));
        log.info("Class Path: " + System.getProperty("java.class.path"));
        log.info("Library Path: " + System.getProperty("java.library.path"));
        log.info("User Home Directory: " + System.getProperty("user.home"));
        log.info("User Working Directory: " + System.getProperty("user.dir"));
        log.info("Test INFO logging.");
        //USER
        DataProviderCSV dataProviderCSV = new DataProviderCSV();
        TypeOfEntities type = TypeOfEntities.USER;
//        User user = new User();
//        user.setId(11L);
//        user.setName("Hello");
//        user.setNumber("89514888969");
//        user.setSureName("SureName");
//        user.setThirdName("Third");
//        log.info(dataProviderCSV.getRecordById(5L, type));
//        log.info(dataProviderCSV.getRecordById(2L, type));
////        log.info(dataProviderCSV.saveRecord(user, type));
//        log.info(dataProviderCSV.deleteRecord(user, type));
        
        //MODEL
        Model model = new Model(1L, "AVEO", "SHEVROLET", "CHINA", "1:18", "iron", 18000L);
        TypeOfEntities type2 = TypeOfEntities.MODEL;
        log.info(dataProviderCSV.saveRecord(model, type2));
        
        //
    }
    
}
