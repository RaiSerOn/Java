/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.mavenproject1;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.log4j.Logger;
import ru.sfedu.mavenproject1.model.DataProviderXML;
import ru.sfedu.mavenproject1.model.entities.CSVEntities.Model;
import ru.sfedu.mavenproject1.model.entities.CSVEntities.User;
import ru.sfedu.mavenproject1.model.entities.TypeOfEntities;

/**
 *
 * @author Максим
 */
public class XMLProviderTest {
    
    private static Logger log = Logger.getLogger(XMLProviderTest.class);
    
    public XMLProviderTest(){
        log.debug("XMLProviderTest[0]: starting application.........");
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
        TypeOfEntities type = TypeOfEntities.USER; 
//        User example = new User(5L, "Hello", "Again", "AndAgain", "88005553535");
//        User example2 = new User(7L, "Hello", "Again", "AndAgain", "88005553535");
        DataProviderXML dataProviderXML = new DataProviderXML();
        //USER
        //dataProviderXML.saveRecord(example, type);
//        log.info(dataProviderXML.saveRecord(example, type));
//        log.info(dataProviderXML.saveRecord(example2, type));
////        log.info(dataProviderXML.deleteRecord(example, type));
//        log.info(dataProviderXML.getRecordById(1L, type));
//        log.info(dataProviderXML.getRecordById(4L, type));
//        log.info(dataProviderXML.getRecordById(5L, type));
        
        //MODEl
        Model model = new Model(2L, "AVEO", "SHEVROLET", "CHINA", "1:18", "iron", 18000L);
        TypeOfEntities type2 = TypeOfEntities.MODEL; 
        log.info(dataProviderXML.saveRecord(model, type2));
        //log.info(dataProviderXML.deleteRecord(model, type2));
        
        //
    }
    
}
