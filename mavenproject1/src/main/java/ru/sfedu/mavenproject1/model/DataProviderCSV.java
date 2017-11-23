package ru.sfedu.mavenproject1.model;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import ru.sfedu.mavenproject1.CSVProviderTest;
import ru.sfedu.mavenproject1.Constants;
import ru.sfedu.mavenproject1.model.entities.TypeOfEntities;
import ru.sfedu.mavenproject1.model.entities.CSVEntities.User;
import ru.sfedu.mavenproject1.model.entities.Result.DataProviderResult;
import ru.sfedu.mavenproject1.model.entities.StatusType;
import ru.sfedu.mavenproject1.model.entities.WithId;
import ru.sfedu.mavenproject1.utils.ConfigurationUtil;


public class  DataProviderCSV <T extends WithId> implements IDataProvider<T>{
    
    private static Logger log = Logger.getLogger(CSVProviderTest.class);
    CsvToBeanBuilder<T> builder;
    StatefulBeanToCsv beanToCsv;
    FileWriter writer;
    CSVReader reader;
    
    public DataProviderCSV(){}

    @Override
    public DataProviderResult deleteRecord(T bean, TypeOfEntities type) {
        try{
            initBuilder(type);
            List<T> beanList = builder.build().parse();
            T candidate = beanList.stream()
                .filter(article -> Objects.equals(article.getId(), bean.getId()))
                .findFirst()
                .orElse(null);
            if(candidate != null){
                initBeanToCsv(type, Boolean.FALSE);
                beanList.remove(candidate);
                beanToCsv.write(beanList);
                writer.close();
                return new DataProviderResult(StatusType.SUCCESS, bean, 
                        "Bean was successfully deleted");
            } else{
                return new DataProviderResult(StatusType.FAIL, bean, 
                        "Bean wasn't deleted cause wasn't fined");
            }
        } catch(CsvDataTypeMismatchException | 
                CsvRequiredFieldEmptyException | IOException | IllegalStateException ex){
            log.info(ex.getStackTrace());
        }
        return new DataProviderResult(StatusType.ERROR, bean, 
                "Some error was existed in " + this.getClass().getName());
    }

    @Override
    public DataProviderResult getRecordById(long id, TypeOfEntities type) {
        try{
            initBuilder(type);
            List<T> beanList = builder.build().parse();
            T bean = beanList.stream()
                    .filter(candidate -> Objects.equals(id, candidate.getId()))
                    .findFirst()
                    .orElse(null);
            return bean != null ?  new DataProviderResult(StatusType.SUCCESS, bean, 
                    "Bean was successfully finded") 
                : new DataProviderResult(StatusType.ID_NOT_EXIST,
                    "Bean was't finded");
        } catch(IllegalStateException ex){
            log.info(ex.getStackTrace());
        }
        return new DataProviderResult(StatusType.ERROR, 
                "Some error was existed in " + this.getClass().getName());
    }
    
    @Override
    public DataProviderResult saveRecord(T bean, TypeOfEntities type) 
            throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try{
            if(Objects.equals(
                    getRecordById(bean.getId(), type).getStatus()
                    ,StatusType.ID_NOT_EXIST)){
                initBeanToCsv(type, Boolean.TRUE);
                beanToCsv.write(bean);
                writer.close();
                return new DataProviderResult(StatusType.SUCCESS, bean, 
                        "Bean was successfully added!");
            } else {
                return new DataProviderResult(StatusType.FAIL, bean, 
                        "This bean is already writed");
            }
        } catch(CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException ex){
            log.info(ex.getStackTrace());
        }
        return new DataProviderResult(StatusType.ERROR, bean, 
                "Some error was existed in " + this.getClass().getName()); 
    }
    
    @Override
    public void initDataSource(Class<T> tClass) {
        
    }
    
    public void initBeanToCsv(TypeOfEntities type, boolean flag) { 
        switch(type) { 
            case USER: 
                try { 
                    writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(
                            Constants.CSV_PATH_USERS), flag); 
                    beanToCsv = new StatefulBeanToCsvBuilder(writer).build(); 
                } catch (IOException ex) { 
                    log.info(ex.getStackTrace()); 
                } 
                break; 
            case BOOT: 
                try { 
                    writer = new FileWriter(ConfigurationUtil.getConfigurationEntry( 
                        Constants.CSV_PATH_BOOT), flag); 
                    beanToCsv = new StatefulBeanToCsvBuilder(writer).build(); 
                } catch (IOException ex) { 
                    log.info(ex.getStackTrace()); 
                } 
                break; 
            case MODEL: 
                try { 
                    writer = new FileWriter(ConfigurationUtil.getConfigurationEntry( 
                        Constants.CSV_PATH_MODEL), flag); 
                    beanToCsv = new StatefulBeanToCsvBuilder(writer).build(); 
                } catch (IOException ex) { 
                    log.info(ex.getStackTrace()); 
                } 
                break; 
        } 
    }
    
    public void initBuilder(TypeOfEntities type) { 
        switch(type) { 
            case USER: 
                try { 
                    builder = new CsvToBeanBuilder(new FileReader( 
                        ConfigurationUtil.getConfigurationEntry( 
                        Constants.CSV_PATH_USERS))) 
                        .withType(User.class); 
                } catch (IOException ex) { 
                    log.debug(Priority.DEBUG, ex); 
                }
                break;  
            case BOOT: 
                try { 
                    builder = new CsvToBeanBuilder(new FileReader( 
                        ConfigurationUtil.getConfigurationEntry( 
                        Constants.CSV_PATH_BOOT))) 
                        .withType(User.class); 
                } catch (IOException ex) { 
                    log.debug(Priority.DEBUG, ex); 
                }
                break;  
            case MODEL: 
                try { 
                    builder = new CsvToBeanBuilder(new FileReader( 
                        ConfigurationUtil.getConfigurationEntry( 
                        Constants.CSV_PATH_MODEL))) 
                        .withType(User.class); 
                } catch (IOException ex) { 
                    log.debug(Priority.DEBUG, ex); 
                }
                break;  
        } 
    }
    
}