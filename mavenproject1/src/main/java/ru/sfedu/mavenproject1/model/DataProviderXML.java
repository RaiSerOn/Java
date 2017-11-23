package ru.sfedu.mavenproject1.model;

import java.io.File;
import java.util.List;
import java.util.Objects;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.mavenproject1.Constants;
import ru.sfedu.mavenproject1.model.entities.TypeOfEntities;
import ru.sfedu.mavenproject1.model.entities.WithId;
import ru.sfedu.mavenproject1.model.entities.XMLEntities.BeanList;
import ru.sfedu.mavenproject1.model.entities.Result.DataProviderResult;
import ru.sfedu.mavenproject1.model.entities.StatusType;
import ru.sfedu.mavenproject1.utils.ConfigurationUtil;

public class DataProviderXML <T extends WithId> implements IDataProvider<T>{
    
    private static Logger log = org.apache.log4j.Logger.getLogger(DataProviderXML.class);
    File filePath;
    
    public DataProviderXML() {
    }

    @Override
    public DataProviderResult deleteRecord(T bean, TypeOfEntities type) {
        Serializer serializer = new Persister();
        try{ 
            initBuilderXml(type);
            List<T> contact = serializer.read(BeanList.class, filePath).getList();
            T candidate = contact.stream()
                    .filter(iterator -> Objects.equals(iterator.getId(), bean.getId()))
                    .findFirst()
                    .orElse(null);
            if(candidate != null){
                contact.remove(candidate);
                serializer.write(new BeanList(contact), filePath);
                return new DataProviderResult(StatusType.SUCCESS, bean, 
                        "Bean was successfully deleted");
            } else {
                serializer.write(new BeanList(contact), filePath);
                return new DataProviderResult(StatusType.FAIL, bean, 
                        "Bean wasn't deleted cause wasn't fined");
            }
        }catch(Exception ex){
            log.info(ex);
        }
        return new DataProviderResult(StatusType.ERROR, bean, 
                "Some error was existed in " + this.getClass().getName());
    }

    @Override
    public DataProviderResult getRecordById(long id, TypeOfEntities type) {
        Serializer serializer = new Persister();
        try {
            initBuilderXml(type); 
            List<T> contact = serializer.read(BeanList.class, filePath).getList();
            T bean = contact.stream()
                    .filter(candidate -> Objects.equals(candidate.getId(), id))
                    .findFirst()
                    .orElse(null);
            return bean != null ?  new DataProviderResult(StatusType.SUCCESS,
                    bean, "Bean was successfully finded") 
                : new DataProviderResult(StatusType.ID_NOT_EXIST, bean,
                    "Bean was't finded");
        } catch (Exception ex) {
            log.info(ex);
        }
        return new DataProviderResult(StatusType.ERROR, 
                "Some error was existed in " + this.getClass().getName());
    }

    @Override
    public DataProviderResult saveRecord(T bean, TypeOfEntities type) {
        Serializer serializer = new Persister();
        try {
            initBuilderXml(type);
            List<T> contact = serializer.read(BeanList.class, filePath).getList();
            if(Objects.equals(
                    getRecordById(bean.getId(), type).getStatus()
                    ,StatusType.ID_NOT_EXIST)){
                contact.add(bean);
                serializer.write(new BeanList(contact), filePath);
                return new DataProviderResult(StatusType.SUCCESS, bean, 
                        "Bean was successfully added!");
            } else{
                serializer.write(new BeanList(contact), filePath);
                return new DataProviderResult(StatusType.FAIL, bean, 
                        "This bean is already writed");
            }
            
        } catch (Exception ex) {
            log.info(ex);
        }
        return new DataProviderResult(StatusType.ERROR, bean, 
                "Some error was existed in " + this.getClass().getName()); 
    }

    @Override
    public void initDataSource(Class<T> tClass) {
        
    }
    
    public void initBuilderXml(TypeOfEntities type) {
        switch(type) { 
            case USER: 
                try { 
                    filePath = new File(ConfigurationUtil.getConfigurationEntry
                        (Constants.XML_PATH_USERS));
                } catch (Exception ex) { 
                    log.debug(Priority.DEBUG, ex); 
                } 
                break; 
            case BOOT: 
                try { 
                    filePath = new File(ConfigurationUtil.getConfigurationEntry
                        (Constants.XML_PATH_BOOT)); 
                } catch (Exception ex) { 
                    log.debug(Priority.DEBUG, ex); 
                } 
                break; 
            case MODEL: 
                try { 
                    filePath = new File(ConfigurationUtil.getConfigurationEntry
                        (Constants.XML_PATH_MODEL)); 
                } catch (Exception ex) { 
                    log.debug(Priority.DEBUG, ex); 
                } 
                break; 
        } 
    }
    
}
