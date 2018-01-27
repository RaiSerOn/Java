package ru.sfedu.shopproject.model.providers;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import ru.sfedu.shopproject.Constants;
import ru.sfedu.shopproject.exception.DependenciesDoesntExist;
import ru.sfedu.shopproject.exception.WrongTypeException;
import ru.sfedu.shopproject.model.entities.CSVEntities.Model;
import ru.sfedu.shopproject.model.entities.CSVEntities.Purchase;
import ru.sfedu.shopproject.model.entities.TypeOfEntities;
import ru.sfedu.shopproject.model.entities.CSVEntities.User;
import ru.sfedu.shopproject.model.entities.Result.DataProviderResult;
import ru.sfedu.shopproject.model.entities.StatusType;
import ru.sfedu.shopproject.model.entities.CSVEntities.WithId;
import static ru.sfedu.shopproject.model.entities.TypeOfEntities.USER;
import ru.sfedu.shopproject.utils.ConfigurationUtil;

/**
 * Class DataProviderCSV
 *
 *
 * @author Максим
 * @param <T>
 */
@SuppressWarnings("all")
public class  DataProviderCSV <T extends WithId> implements IDataProvider<T>{
    
    public static Logger log = Logger.getLogger(DataProviderCSV.class);
    public CsvToBeanBuilder<T> builder;
    private List<T> usersList, modelsList, purchasesForUser, purchasesForModel, purchasesList, purchases, beans;
    private T resultOfSearchUser, resultOfSearchModel, candidate;
    private StatefulBeanToCsv beanToCsv;
    private CsvToBean csvToBean;
    private FileWriter writer;
    private FileReader reader;
    
    public DataProviderCSV(){}
    
    @Override
    public DataProviderResult deleteUserRecursive(T bean){
        try{
            switch(bean.getType()){
                case USER:
                    initBuilder(bean.getType());
                    usersList = builder.build().parse();
                    initBuilder(TypeOfEntities.PURCHASE);
                    purchasesForUser = builder.build().parse();
                    T resultOfSearchLogin = usersList.stream()
                            .filter(iterator -> Objects.equals(((User)(bean)).getLogin(), ((User)(iterator)).getLogin()))
                            .findFirst()
                            .orElse(null);
                    if(resultOfSearchLogin == null){
                        return new DataProviderResult(StatusType.LOGIN_NOT_EXIST, bean, 
                            "This login doesn't exist"); 
                    } else {
                        candidate = usersList.stream()
                            .filter(article -> Objects.equals(article.getId(), bean.getId()))
                            .findFirst()
                            .orElse(null);
                        if(candidate != null){
                            purchases = purchasesForUser.stream()
                                    .filter(iterator -> Objects.equals(bean.getId(), ((Purchase)iterator).getUserId()))
                                    .collect(Collectors.toList());
                            if(purchases.size() > 0){
                                initBeanToCsv(TypeOfEntities.PURCHASE, Boolean.FALSE);
                                initBuilder(TypeOfEntities.PURCHASE);
                                purchases.forEach(iterator -> purchasesForUser.remove(iterator));
                                beanToCsv.write(purchasesForUser);
                                writer.close();
                            }
                            initBeanToCsv(bean.getType(), Boolean.FALSE);
                            usersList.remove(candidate);
                            beanToCsv.write(usersList);
                            writer.close();
                            
                                return new DataProviderResult(StatusType.SUCCESS, bean, 
                                    "User has been deleted successfully");
                        } else{
                            return new DataProviderResult(StatusType.ID_NOT_EXIST, bean, 
                                "User hasn't been deleted cause hasn't been found");
                        }
                    }
                    
                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                        "This type hasn't been provided");
            }
        } catch(IllegalStateException ex){
            log.info(ex);
            return new DataProviderResult(StatusType.ERROR, ex, 
                "IllegalStateException has been existed in " + this.getClass().getName());
        } catch(WrongTypeException ex){
            log.info(ex);
            return new DataProviderResult(StatusType.ERROR, ex, 
                "WrongTypeException has been existed in " + this.getClass().getName());
        } catch(CsvRequiredFieldEmptyException ex){
            log.info(ex);
            return new DataProviderResult(StatusType.ERROR, ex, 
                "CsvRequiredFieldEmptyException has been existed in " + this.getClass().getName());
        } catch(IOException ex){
            log.info(ex);
            return new DataProviderResult(StatusType.ERROR, ex, 
                "IOException has been existed in " + this.getClass().getName());
        } catch(CsvDataTypeMismatchException ex){
            log.info(ex);
            return new DataProviderResult(StatusType.ERROR, ex, 
                "CsvDataTypeMismatchException has been existed in " + this.getClass().getName());
        }
    }

    @Override
    public DataProviderResult deleteRecord(T bean) {
        try{
            switch(bean.getType()){
                case USER:
                    initBuilder(bean.getType());
                    usersList = builder.build().parse();
                    initBuilder(TypeOfEntities.PURCHASE);
                    purchasesForUser = builder.build().parse();
                    T resultOfSearchLogin = usersList.stream()
                            .filter(iterator -> Objects.equals(((User)(bean)).getLogin(), ((User)(iterator)).getLogin()))
                            .findFirst()
                            .orElse(null);
                    if(resultOfSearchLogin == null){
                        return new DataProviderResult(StatusType.LOGIN_NOT_EXIST, bean, 
                            "This login doesn't exist"); 
                    } else {
                        candidate = usersList.stream()
                            .filter(article -> Objects.equals(article.getId(), bean.getId()))
                            .findFirst()
                            .orElse(null);
                        if(candidate != null){
                            resultOfSearchLogin = purchasesForUser.stream()
                                    .filter(iterator -> Objects.equals(((Purchase)iterator).getUserId(), bean.getId()))
                                    .filter(iterator -> Objects.equals(((Purchase)iterator).getStatus(), 0))
                                    .findFirst()
                                    .orElse(null);
                            if(resultOfSearchLogin == null){
                                initBeanToCsv(bean.getType(), Boolean.FALSE);
                                usersList.remove(candidate);
                                beanToCsv.write(usersList);
                                writer.close();
                                return new DataProviderResult(StatusType.SUCCESS, bean, 
                                    "User has been deleted successfully");
                            } else {
                                return new DataProviderResult(StatusType.DEPENDENCIES_EXISTED, bean,
                                    "User has dependencies with purchases");
                            }
                        } else{
                            return new DataProviderResult(StatusType.ID_NOT_EXIST, bean, 
                                "User hasn't been deleted cause hasn't been found");
                        }
                    }
                    
                case MODEL:
                    initBuilder(bean.getType());
                    modelsList = builder.build().parse();
                    initBuilder(TypeOfEntities.PURCHASE);
                    purchasesForModel = builder.build().parse();
                    candidate = modelsList.stream()
                            .filter(iterator -> Objects.equals(bean.getId(), iterator.getId()))
                            .findFirst()
                            .orElse(null);
                    T resultOfSearchDependencies; 
                    if(candidate != null){
                        resultOfSearchDependencies = purchasesForModel.stream()
                                .filter(iterator -> Objects.equals(bean.getId(), ((Purchase)iterator).getModelId()))
                                .filter(iterator -> Objects.equals(((Purchase)iterator).getStatus(), 0))
                                .findFirst()
                                .orElse(null);
                        if(resultOfSearchDependencies == null){
                            initBeanToCsv(bean.getType(), Boolean.FALSE);
                            modelsList.remove(candidate);
                            beanToCsv.write(modelsList);
                            writer.close();
                            return new DataProviderResult(StatusType.SUCCESS, bean, 
                                "Model has been deleted successfully");
                        } else {
                            return new DataProviderResult(StatusType.DEPENDENCIES_EXISTED, bean,
                                "Model has dependencies with purchases");
                        }
                    } else {
                        return new DataProviderResult(StatusType.ID_NOT_EXIST, bean,
                            "Model hasn't been deleted cause hasn't been fined");
                    }
                    
                case PURCHASE:
                    initBuilder(bean.getType());
                    purchasesList = builder.build().parse();
                    candidate = purchasesList.stream()
                            .filter(iterator -> Objects.equals(bean.getId(), iterator.getId()))
                            .findFirst()
                            .orElse(null);
                    if(candidate == null){
                        return new DataProviderResult(StatusType.ID_NOT_EXIST, bean,
                                "Purchase hasn't deleted cause it hasn't been found");
                    }
                    if(candidate != null && ((Purchase)candidate).getStatus() == 1){  
                        initBeanToCsv(bean.getType(), Boolean.FALSE);
                        purchasesList.remove(candidate);
                        beanToCsv.write(purchasesList);
                        writer.close();
                        return new DataProviderResult(StatusType.SUCCESS, bean, 
                            "Purchase has been deleted successfully");
                    } else {
                        return new DataProviderResult(StatusType.STATUS_NOT_SOLD, bean,
                            "Purchase hasn't been deleted cause hasn't been fined or status 'not sold'");
                    }
                    
                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                                "This type hasn't been provided");

            }  
        } catch(IllegalStateException ex){
            log.info(ex);
            return new DataProviderResult(StatusType.ERROR, ex, 
                "IllegalStateException has been existed in " + this.getClass().getName());
        } catch(WrongTypeException ex){
            log.info(ex);
            return new DataProviderResult(StatusType.ERROR, ex, 
                "WrongTypeException has been existed in " + this.getClass().getName());
        } catch(CsvRequiredFieldEmptyException ex){
            log.info(ex);
            return new DataProviderResult(StatusType.ERROR, ex, 
                "CsvRequiredFieldEmptyException has been existed in " + this.getClass().getName());
        } catch(IOException ex){
            log.info(ex);
            return new DataProviderResult(StatusType.ERROR, ex, 
                "IOException has been existed in " + this.getClass().getName());
        } catch(CsvDataTypeMismatchException ex){
            log.info(ex);
            return new DataProviderResult(StatusType.ERROR, ex, 
                "CsvDataTypeMismatchException has been existed in " + this.getClass().getName());
        }
       
    }

    @Override
    public DataProviderResult getRecordById(T bean) {
        try{
            initBuilder(bean.getType());
            List<T> beanList = builder.build().parse();
            T resultBean = beanList.stream()
                    .filter(iterator -> Objects.equals(bean.getId(), iterator.getId()))
                    .findFirst()
                    .orElse(null);
            return resultBean != null ?  new DataProviderResult(StatusType.SUCCESS, resultBean, 
                    "Bean has been found successfully")
                : new DataProviderResult(StatusType.ID_NOT_EXIST,
                    "Bean hasn't been found");
        } catch(IllegalStateException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                "IllegalStateException has been existed in " + this.getClass().getName());
        } catch(WrongTypeException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                "WrongTypeException has been existed in " + this.getClass().getName());
        }
    }
    
    public DataProviderResult getRecordByIdForCleaning(Long id, TypeOfEntities type) {
        try{
            initBuilder(type);
            List<T> beanList = builder.build().parse();
            T resultBean = beanList.stream()
                    .filter(iterator -> Objects.equals(id, iterator.getId()))
                    .findFirst()
                    .orElse(null);
            return resultBean != null ?  new DataProviderResult(StatusType.SUCCESS, resultBean, 
                    "Bean has been found successfully")
                : new DataProviderResult(StatusType.ID_NOT_EXIST,
                    "Bean hasn't been found");
        } catch(IllegalStateException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                "IllegalStateException has been existed in " + this.getClass().getName());
        } catch(WrongTypeException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                "WrongTypeException has been existed in " + this.getClass().getName());
        }
    }

    @Override
    public DataProviderResult getEntityByTitle(String title, TypeOfEntities type){
        try {
            initBuilder(type);
            switch(type){
                case MODEL:
                    modelsList = builder.build().parse();
                    beans = new ArrayList();
                    modelsList.stream()
                            .forEach(iterator -> {
                                if(((Model)iterator).getTitle().contains(title))beans.add(iterator);
                            });
                    return beans.size() > 0 ? new DataProviderResult(StatusType.SUCCESS, beans,
                            "Model has been found successfully")
                            : new DataProviderResult(StatusType.TITLE_NOT_EXIST,
                            "Model hasn't been found");

                case PURCHASE:
                    purchasesList = builder.build().parse();
                    beans = new ArrayList();
                    purchasesList.stream()
                            .forEach(iterator -> {
                                if(((Purchase)iterator).getTitle().contains(title))beans.add(iterator);
                            });
                    return beans.size() > 0 ? new DataProviderResult(StatusType.SUCCESS, beans,
                            "Purchase has been found successfully")
                            : new DataProviderResult(StatusType.TITLE_NOT_EXIST,
                            "Purchase hasn't been found");

                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                            "This type hasn't been provided");

            }

        } catch (WrongTypeException ex) {
            return new DataProviderResult(StatusType.ERROR, ex,
                    "WrongTypeException has been existed in " + this.getClass().getName());
        }
    }

    @Override
    public DataProviderResult getUserByLogin(String login, TypeOfEntities type) {
        try {
            initBuilder(type);
            usersList = builder.build().parse();
            T beanResult = usersList.stream()
                    .filter(iterator -> Objects.equals(login, ((User) iterator).getLogin()))
                    .findFirst()
                    .orElse(null);
            return beanResult != null ? new DataProviderResult(StatusType.SUCCESS, beanResult,
                    "User has been found successfully")
                    : new DataProviderResult(StatusType.LOGIN_NOT_EXIST,
                    "Login hasn't been found");
        } catch (WrongTypeException ex) {
            return new DataProviderResult(StatusType.ERROR, ex,
                    "WrongTypeException has been existed in " + this.getClass().getName());
        }
    }
    
    @Override
    public DataProviderResult saveRecord(T bean){
        
        try{
            switch(bean.getType()){
                case USER:
                    List<User> usersToSave;
                    initBuilder(bean.getType());
                    usersList = builder.build().parse();
                    resultOfSearchUser = usersList.stream()
                            .filter(iterator -> Objects.equals(((User)(bean)).getLogin(), ((User)(iterator)).getLogin()))
                            .findFirst()
                            .orElse(null);
                    if(resultOfSearchUser == null){
                        if(Objects.equals(getRecordById(bean).getStatus()
                        ,StatusType.ID_NOT_EXIST)){
                            usersToSave = selectAllRecords(bean.getType()).getBeans();
                            usersToSave.add((User)bean);
                            initBeanToCsv(bean.getType(), Boolean.FALSE);
                            beanToCsv.write(usersToSave);
                            writer.close();
                            return new DataProviderResult(StatusType.SUCCESS, bean, 
                                    "User has been added successfully");
                        } else {
                            return new DataProviderResult(StatusType.ID_ALLREADY_EXIST, bean,
                                    "User has been just writed");
                        }
                    } else {
                        return new DataProviderResult(StatusType.LOGIN_EXIST, bean,
                                "This login has been already wreeten"); 
                    }
                    
                case MODEL:
                    List<Model> modelsToSave;
                    initBuilder(bean.getType());
                    if(Objects.equals(getRecordById(bean).getStatus()
                        ,StatusType.ID_NOT_EXIST)){
                            modelsToSave = selectAllRecords(bean.getType()).getBeans();
                            modelsToSave.add((Model)bean);
                            initBeanToCsv(bean.getType(), Boolean.FALSE);
                            beanToCsv.write(modelsToSave);
                            writer.close();
                            return new DataProviderResult(StatusType.SUCCESS, bean, 
                                    "Model has been added successfully");
                        } else {
                            return new DataProviderResult(StatusType.ID_ALLREADY_EXIST, bean,
                                    "Model has been just writed");
                        }
                    
                case PURCHASE:
                    clearPurchases();
                    List<Purchase> purchasesToSave;
                    initBuilder(TypeOfEntities.USER);
                    usersList = builder.build().parse();
                    initBuilder(TypeOfEntities.MODEL);
                    modelsList = builder.build().parse();
                    if(Objects.equals(getRecordById(bean).getStatus()
                        ,StatusType.ID_NOT_EXIST)){
                        resultOfSearchModel = modelsList.stream()
                            .filter(iterator -> Objects.equals(((Purchase)bean).getModelId(), iterator.getId()))
                            .findFirst()
                            .orElse(null);
                        resultOfSearchUser = usersList.stream()
                            .filter(iterator -> Objects.equals(((Purchase)bean).getUserId(), iterator.getId()))
                            .findFirst()
                            .orElse(null);
                        if(resultOfSearchModel != null && resultOfSearchUser != null){
                            purchasesToSave = selectAllRecords(bean.getType()).getBeans();
                            purchasesToSave.add((Purchase)bean);
                            initBeanToCsv(bean.getType(), Boolean.FALSE);
                            beanToCsv.write(purchasesToSave);
                            writer.close();
                            return new DataProviderResult(StatusType.SUCCESS, bean, 
                                    "Purchase has been added successfully");
                        }else {
                            return new DataProviderResult(StatusType.DEPENDENCIES_NOT_EXIST, new DependenciesDoesntExist(),
                                    "Depended entities haven't been found for this purchase");
                        }
                    } else {
                        return new DataProviderResult(StatusType.ID_ALLREADY_EXIST, bean,
                                    "Purchase has been already wreten");
                    }
                    
                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                                "This type hasn't been provided");
                    
            }
            
        } catch(CsvDataTypeMismatchException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex, 
                "CsvDataTypeMismatchException has been existed in " + this.getClass().getName()); 
        } catch(WrongTypeException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex, 
                "WrongTypeException has been existed in " + this.getClass().getName()); 
        } catch(CsvRequiredFieldEmptyException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex, 
                "CsvRequiredFieldEmptyException has been existed in " + this.getClass().getName()); 
        } catch(IOException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex, 
                "Some error was existed in " + this.getClass().getName()); 
        }
    }
    
    @Override
    public DataProviderResult selectAllRecords(TypeOfEntities type){
        try{
            initBeanToCsv(type);
            return new DataProviderResult(StatusType.SUCCESS, csvToBean.parse(), 
                "Entiti has been successfully selected from CSV");
        } catch(IllegalStateException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                "IllegalStateException has been existed in " + this.getClass().getName()); 
        } catch(WrongTypeException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                "WrongTypeException has been existed in " + this.getClass().getName()); 
        }
    }
    
    @Override
    public DataProviderResult selectUsersPurchases(T bean){
        try{
            initBuilder(TypeOfEntities.PURCHASE);
            purchasesList = builder.build().parse();
            purchases = purchasesList.stream()
                    .filter(iterator -> Objects.equals(bean.getId(), ((Purchase)iterator).getUserId()))
                    .collect(Collectors.toList());
            if(purchases.size() > 0){
                return new DataProviderResult(StatusType.SUCCESS, purchases, 
                    "Purchases have been successfully selected from CSV for User");
            } else {
                return new DataProviderResult(StatusType.PURCHASES_NOT_FOUND, purchases, 
                    "User hasn't purchases");
            }
        } catch(WrongTypeException ex){
            return new DataProviderResult(StatusType.ERROR, ex,
                "Some error was existed in " + this.getClass().getName());
        }  
    }
    
    @Override
    public DataProviderResult updateRecord(T bean){
        try{
            int index;
            initBuilder(bean.getType());
            switch(bean.getType()){
                case USER:
                    List<T> users = builder.build().parse();
                    T user = users.stream().filter(iterator -> Objects.equals(iterator.getId(), bean.getId()))
                            .findFirst()
                            .orElse(null);
                    if(user != null){
                        index = users.indexOf(user);
                        initBeanToCsv(bean.getType(), Boolean.FALSE);
                        users.remove(user);
                        users.add(index, bean);
                        beanToCsv.write(users);
                        writer.close();
                        return new DataProviderResult(StatusType.SUCCESS, bean,
                            "User has been updated successfully");
                    }else {
                        return new DataProviderResult(StatusType.ID_NOT_EXIST,
                            "User hasn't been finded");
                    }
                    
                case MODEL:
                    List<T> models = builder.build().parse();
                    T model = models.stream().filter(iterator -> Objects.equals(iterator.getId(), bean.getId()))
                            .findFirst()
                            .orElse(null);
                    if(model != null){
                        index = models.indexOf(model);
                        initBeanToCsv(bean.getType(), Boolean.FALSE);
                        models.remove(model);
                        models.add(index, bean);
                        beanToCsv.write(models);
                        writer.close();
                        return new DataProviderResult(StatusType.SUCCESS, bean,
                            "Model has been updated successfully");
                    }else {
                        return new DataProviderResult(StatusType.ID_NOT_EXIST,
                            "Model hasn't been found");
                    }
                    
                case PURCHASE:
                    initBuilder(TypeOfEntities.USER);
                    usersList = builder.build().parse();
                    initBuilder(TypeOfEntities.MODEL);
                    modelsList = builder.build().parse();
                    initBuilder(bean.getType());
                    purchases = builder.build().parse();
                    T purchase = purchases.stream()
                            .filter(iterator -> Objects.equals(iterator.getId(), bean.getId()))
                            .findFirst()
                            .orElse(null);
                    if(purchase != null){
                        resultOfSearchUser = usersList.stream()
                            .filter(iterator -> Objects.equals(iterator.getId(), ((Purchase)bean).getUserId()))
                            .findFirst()
                            .orElse(null);
                        resultOfSearchModel = modelsList.stream()
                            .filter(iterator -> Objects.equals(iterator.getId(), ((Purchase)bean).getModelId()))
                            .findFirst()
                            .orElse(null);
                        if((resultOfSearchUser != null && resultOfSearchModel != null) ||
                                (resultOfSearchUser == null && resultOfSearchModel != null)){
                            index = purchases.indexOf(purchase);
                            initBeanToCsv(bean.getType(), Boolean.FALSE);
                            purchases.remove(purchase);
                            purchases.add(index, bean);
                            beanToCsv.write(purchases);
                            writer.close();
                            return new DataProviderResult(StatusType.SUCCESS, bean,
                                "Purchase has been updated successfully");
                        } else{
                            return new DataProviderResult(StatusType.DEPENDENCIES_NOT_EXIST,
                                "Purchase has wrong dependencies");
                        }
                    }else {
                        return new DataProviderResult(StatusType.ID_NOT_EXIST,
                            "Purchase hasn't been found");
                    }
                    
                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                        "This type hasn't been provided");
                    
            }

        } catch(CsvDataTypeMismatchException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                "CsvDataTypeMismatchException hs been existed in " + this.getClass().getName());
        } catch(WrongTypeException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                "WrongTypeException has been existed in " + this.getClass().getName());
        } catch( CsvRequiredFieldEmptyException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                "CsvRequiredFieldEmptyException has been existed in " + this.getClass().getName());
        } catch ( IOException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                "IOException has been existed in " + this.getClass().getName());
        } catch(IllegalStateException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                "IllegalStateException has been existed in " + this.getClass().getName());
        }
    }

    @Override
    public DataProviderResult buyModel(T bean){
        try {
            initBuilder(bean.getType());
            purchasesList = builder.build().parse();
            T purchase = purchasesList.stream()
                .filter(iterator -> Objects.equals(iterator.getId(), bean.getId()))
                .findFirst()
                .orElse(null);
            if(purchase.getId() != null){
                ((Purchase)purchase).setStatus(1);
                DataProviderResult result = updateRecord(purchase);
                if(result.getStatus() == StatusType.SUCCESS){
                    return new DataProviderResult(StatusType.SUCCESS, purchase,
                            "Purchase's status has been updated successfully");
                } else {
                    return new DataProviderResult(StatusType.FAIL, 
                        result.getMessage());
                }
            } else {
                return new DataProviderResult(StatusType.ID_NOT_EXIST,
                        "Purchase hasn't been found");
            }
        } catch(WrongTypeException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                    "IllegalStateException has been existed in " + this.getClass().getName());
        }

    }

    public void clearPurchases(){
        try {
            initBuilder(TypeOfEntities.PURCHASE);
            purchasesList = builder.build().parse();
            List<T> deletionPurchases;
            initBuilder(TypeOfEntities.PURCHASE);
            deletionPurchases = purchasesList;
            deletionPurchases.forEach(iterator -> {
                DataProviderResult result = getRecordByIdForCleaning(((Purchase)iterator).getUserId()
                        , TypeOfEntities.USER);
                if(result.getStatus() == StatusType.ID_NOT_EXIST){
                    buyModel(iterator);
                    deleteRecord(iterator);
                }
            });
        } catch (WrongTypeException ex){
            log.error(ex);
        }
    }
    
    @Override
    public void initDataSource() { }
    
    public void initBeanToCsv(TypeOfEntities type, boolean flag) throws WrongTypeException{ 
        switch(type) { 
            case USER: 
                try { 
                    writer = new FileWriter(ConfigurationUtil.
                            getConfigurationEntry(Constants.START_PATH) +
                            ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH_USERS), flag);
                    beanToCsv = new StatefulBeanToCsvBuilder(writer).build(); 
                } catch (IOException ex) { 
                    log.info(ex.getStackTrace()); 
                } 
                break; 
            case PURCHASE: 
                try { 
                    writer = new FileWriter(ConfigurationUtil.
                            getConfigurationEntry(Constants.START_PATH) +
                            ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH_PURCHASE), flag);
                    beanToCsv = new StatefulBeanToCsvBuilder(writer).build(); 
                } catch (IOException ex) { 
                    log.info(ex.getStackTrace()); 
                } 
                break; 
            case MODEL: 
                try { 
                    writer = new FileWriter(ConfigurationUtil.
                            getConfigurationEntry(Constants.START_PATH) +
                            ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH_MODEL), flag);
                    beanToCsv = new StatefulBeanToCsvBuilder(writer).build(); 
                } catch (IOException ex) { 
                    log.info(ex.getStackTrace()); 
                } 
                break; 
                
            default:
                throw new WrongTypeException();
                    
        } 
    }
    
    public void initBeanToCsv(TypeOfEntities type) throws WrongTypeException{ 
        switch(type) { 
            case USER: 
                try { 
                    reader = new FileReader(ConfigurationUtil.
                            getConfigurationEntry(Constants.START_PATH) +
                            ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH_USERS)); 
                    csvToBean = new CsvToBeanBuilder(reader).withType(User.class).build();
                } catch (IOException ex) { 
                    log.info(ex.getStackTrace()); 
                } 
                break; 
            case PURCHASE: 
                try { 
                    reader = new FileReader(ConfigurationUtil.
                            getConfigurationEntry(Constants.START_PATH) +
                            ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH_PURCHASE)); 
                    csvToBean = new CsvToBeanBuilder(reader).withType(Purchase.class).build();
                } catch (IOException ex) { 
                    log.info(ex.getStackTrace()); 
                } 
                break; 
            case MODEL: 
                try { 
                    reader = new FileReader(ConfigurationUtil.
                            getConfigurationEntry(Constants.START_PATH) +
                            ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH_MODEL)); 
                    csvToBean = new CsvToBeanBuilder(reader).withType(Model.class).build();
                } catch (IOException ex) { 
                    log.info(ex.getStackTrace()); 
                } 
                break; 
                
            default:
                throw new WrongTypeException();
                    
        } 
    }
    
    public void initBuilder(TypeOfEntities type) throws WrongTypeException{ 
        switch(type) { 
            case USER: 
                try {
                    builder = new CsvToBeanBuilder(new FileReader( ConfigurationUtil.
                            getConfigurationEntry(Constants.START_PATH) + ConfigurationUtil
                            .getConfigurationEntry(Constants.CSV_PATH_USERS)))
                        .withType(User.class);

                } catch (IOException ex) { 
                    log.debug(Priority.DEBUG, ex); 
                }
                break; 
                
            case PURCHASE: 
                try { 
                    builder = new CsvToBeanBuilder(new FileReader( ConfigurationUtil.
                            getConfigurationEntry(Constants.START_PATH) + ConfigurationUtil
                            .getConfigurationEntry(Constants.CSV_PATH_PURCHASE)))
                        .withType(Purchase.class); 
                } catch (IOException ex) { 
                    log.debug(Priority.DEBUG, ex); 
                }  
                break; 
                
            case MODEL: 
                try { 
                    builder = new CsvToBeanBuilder(new FileReader(ConfigurationUtil.
                            getConfigurationEntry(Constants.START_PATH) + ConfigurationUtil
                            .getConfigurationEntry(Constants.CSV_PATH_MODEL)))
                        .withType(Model.class); 
                } catch (IOException ex) { 
                    log.debug(Priority.DEBUG, ex); 
                }
                break;  
            default:
                throw new WrongTypeException();
                
        } 
    }
}