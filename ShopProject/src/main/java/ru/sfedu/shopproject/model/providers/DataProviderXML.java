package ru.sfedu.shopproject.model.providers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.shopproject.Constants;
import ru.sfedu.shopproject.exception.WrongTypeException;
import ru.sfedu.shopproject.model.entities.CSVEntities.Model;
import ru.sfedu.shopproject.model.entities.CSVEntities.Purchase;
import ru.sfedu.shopproject.model.entities.CSVEntities.User;
import ru.sfedu.shopproject.model.entities.TypeOfEntities;
import ru.sfedu.shopproject.model.entities.CSVEntities.WithId;
import ru.sfedu.shopproject.model.entities.XMLEntities.BeanList;
import ru.sfedu.shopproject.model.entities.Result.DataProviderResult;
import ru.sfedu.shopproject.model.entities.StatusType;
import ru.sfedu.shopproject.utils.ConfigurationUtil;

/**
 * Class DataProviderXML
 *
 *
 * @author Максим
 */
@SuppressWarnings("all")
public class DataProviderXML <T extends WithId> implements IDataProvider<T>{
    
    private static Logger log = Logger.getLogger(DataProviderXML.class);
    private File filePathUser;
    private File filePathModel;
    private File filePathPurchase;
    private Serializer serializer;
    
    private List<T> users, usersPurchases, models, modelsPurchases, purchases, beans, connectedUsersPurchases;
    private T purchasesUser, purchasesModel;
    
    public DataProviderXML() {}

    @Override
    public DataProviderResult deleteRecord(T bean) {
        try{ 
            switch(bean.getType()){
                case USER:
                    users = serializer.read(BeanList.class, filePathUser).getList();
                    usersPurchases = serializer.read(BeanList.class, filePathPurchase).getList();
                    T user = users.stream()
                            .filter(iterator -> Objects.equals(iterator.getId(), bean.getId()))
                            .findFirst()
                            .orElse(null);
                    Object connectedUsersPurchase = usersPurchases.stream()
                            .filter(iterator -> Objects.equals(((Purchase)iterator).getUserId(), bean.getId()))
                            .filter(iterator -> Objects.equals(((Purchase)iterator).getStatus(), 0))
                            .findFirst()
                            .orElse(null);
                    if(user != null){
                        if(Objects.equals(((User)user).getLogin(), ((User)bean).getLogin())){
                            if(connectedUsersPurchase == null){
                                users.remove(user);
                                serializer.write(new BeanList(users), filePathUser);
                                serializer.write(new BeanList(usersPurchases), filePathPurchase);
//                                clearPurchases();
                                return new DataProviderResult(StatusType.SUCCESS, bean, 
                                        "User has been deleted successfully");
                            } else {
                                serializer.write(new BeanList(users), filePathUser);
                                serializer.write(new BeanList(usersPurchases), filePathPurchase);
                                return new DataProviderResult(StatusType.DEPENDENCIES_EXISTED, bean, 
                                        "This user is connected with purchases");
                            }
                        } else {
                            serializer.write(new BeanList(users), filePathUser);
                            serializer.write(new BeanList(usersPurchases), filePathPurchase);
                            return new DataProviderResult(StatusType.LOGIN_NOT_EXIST, bean, 
                                    "User hasn't been deleted cause it hasn't been found");
                        }
                    } else {
                        serializer.write(new BeanList(users), filePathUser);
                        serializer.write(new BeanList(usersPurchases), filePathPurchase);
                        return new DataProviderResult(StatusType.ID_NOT_EXIST, bean, 
                                "User hasn't been deleted cause it hasn't been found");
                    }
                    
                case MODEL:
                    models = serializer.read(BeanList.class, filePathModel).getList();
                    modelsPurchases = serializer.read(BeanList.class, filePathPurchase).getList();
                    Object connectedModelsPurchases = modelsPurchases.stream()
                            .filter(iterator -> Objects.equals(((Purchase)iterator).getModelId(), bean.getId()))
                            .filter(iterator -> Objects.equals(((Purchase)iterator).getStatus(), 0))
                            .findFirst()
                            .orElse(null);
                    T model = models.stream()
                            .filter(iterator -> Objects.equals(iterator.getId(), bean.getId()))
                            .findFirst()
                            .orElse(null);
                    if(model != null){
                        if(connectedModelsPurchases == null){
                            models.remove(model);
                            serializer.write(new BeanList(models), filePathModel);
                            serializer.write(new BeanList(modelsPurchases), filePathPurchase);
                            return new DataProviderResult(StatusType.SUCCESS, bean, 
                                    "Model has been deleted successfully");
                        } else {
                            serializer.write(new BeanList(models), filePathModel);
                            serializer.write(new BeanList(modelsPurchases), filePathPurchase);
                            return new DataProviderResult(StatusType.DEPENDENCIES_EXISTED, bean, 
                                    "This model is connected with purchases");
                        }
                    } else {
                        serializer.write(new BeanList(models), filePathModel);
                        serializer.write(new BeanList(modelsPurchases), filePathPurchase);
                        return new DataProviderResult(StatusType.FAIL, bean, 
                                "Model hasn't deleted cause it hasn't been founded");
                    }
                    
                case PURCHASE:
                    purchases = serializer.read(BeanList.class, filePathPurchase).getList();
                    T purchase = purchases.stream()
                            .filter(iterator -> Objects.equals(iterator.getId(), bean.getId()))
                            .findFirst()
                            .orElse(null);
                    if(purchase == null){
                        serializer.write(new BeanList(purchases), filePathPurchase);
                        return new DataProviderResult(StatusType.ID_NOT_EXIST, bean,
                                "Purchase hasn't deleted cause it hasn't been found");
                    }
                    if(purchase != null && ((Purchase)purchase).getStatus() == 1){
                            purchases.remove(purchase);
                            serializer.write(new BeanList(purchases), filePathPurchase);
                            return new DataProviderResult(StatusType.SUCCESS, bean, 
                                "Purchase has been deleted successfully");
                    } else {
                        serializer.write(new BeanList(purchases), filePathPurchase);
                        return new DataProviderResult(StatusType.STATUS_NOT_SOLD, bean,
                                "Purchase hasn't deleted cause it has status 'not sold'");
                    }
                    
                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                        "Wrong Data Type in " + this.getClass().getName());
                    
            }
        }catch(Exception ex){
            log.info(ex);
            return new DataProviderResult(StatusType.ERROR, ex, 
                "There was some error in " + this.getClass().getName());
        } 
    }
    
    @Override
    public DataProviderResult deleteUserRecursive(T bean){
        try{
            switch(bean.getType()){
                case USER:
                    users = serializer.read(BeanList.class, filePathUser).getList();
                    usersPurchases = serializer.read(BeanList.class, filePathPurchase).getList();
                    T user = users.stream()
                            .filter(iterator -> Objects.equals(iterator.getId(), bean.getId()))
                            .findFirst()
                            .orElse(null);
                    connectedUsersPurchases = usersPurchases.stream()
                        .filter(iterator -> Objects.equals(((Purchase)iterator).getUserId(), bean.getId()))
                        .collect(Collectors.toList());
                    if(user != null){
                        if(Objects.equals(((User)user).getLogin(), ((User)bean).getLogin())){
                            if(connectedUsersPurchases.size() > 0){
                                connectedUsersPurchases.forEach(iterator -> usersPurchases.remove(iterator));
                            }
                            users.remove(user);
                            serializer.write(new BeanList(users), filePathUser);
                            serializer.write(new BeanList(usersPurchases), filePathPurchase);
                            return new DataProviderResult(StatusType.SUCCESS, bean, 
                                "User has been deleted successfully");
                        } else {
                            serializer.write(new BeanList(users), filePathUser);
                            serializer.write(new BeanList(usersPurchases), filePathPurchase);
                            return new DataProviderResult(StatusType.LOGIN_NOT_EXIST, bean, 
                                    "User hasn't been deleted cause it hasn't been found");
                        }
                    } else {
                        serializer.write(new BeanList(users), filePathUser);
                        serializer.write(new BeanList(usersPurchases), filePathPurchase);
                        return new DataProviderResult(StatusType.ID_NOT_EXIST, bean, 
                                "User hasn't been deleted cause it hasn't been found");
                    }
                    
                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                        "Wrong Data Type in " + this.getClass().getName());
            }
        } catch(Exception ex){
            log.info(ex);
            return new DataProviderResult(StatusType.FAIL, ex, 
                "Some error has been existed in " + this.getClass().getName());
        }
    }

    @Override
    public DataProviderResult getRecordById(T bean) {
        try {
            switch(bean.getType()){
                case USER:
                    users = serializer.read(BeanList.class, filePathUser).getList();
                    T user = users.stream()
                            .filter(candidate -> Objects.equals(candidate.getId(), bean.getId()))
                            .findFirst()
                            .orElse(null);
                    return user != null ?  new DataProviderResult(StatusType.SUCCESS, user,
                            "User has been found successfully")
                        : new DataProviderResult(StatusType.ID_NOT_EXIST, user,
                            "User hasn't been found");

                case MODEL:
                    models = serializer.read(BeanList.class, filePathModel).getList();
                    T model = models.stream()
                            .filter(candidate -> Objects.equals(candidate.getId(), bean.getId()))
                            .findFirst()
                            .orElse(null);
                    return model != null ?  new DataProviderResult(StatusType.SUCCESS,
                            model, "Model has been found successfully") 
                        : new DataProviderResult(StatusType.ID_NOT_EXIST, model,
                            "Model hasn't been found");
                    
                case PURCHASE:
                    purchases = serializer.read(BeanList.class, filePathPurchase).getList();
                    T purchase = purchases.stream()
                            .filter(candidate -> Objects.equals(candidate.getId(), bean.getId()))
                            .findFirst()
                            .orElse(null);
                    return purchase != null ?  new DataProviderResult(StatusType.SUCCESS,
                            purchase, "Purchase has been found successfully") 
                        : new DataProviderResult(StatusType.ID_NOT_EXIST, purchase,
                            "Purchase hasn't been found");
                    
                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                        "Wrong Data Type in " + this.getClass().getName());
                    
            }
        } catch (Exception ex) {
            log.info(ex);
            return new DataProviderResult(StatusType.ERROR, ex, 
                "There was some error in " + this.getClass().getName());
        }
    }

    @Override
    public DataProviderResult getEntityByTitle(String title, TypeOfEntities type){
        try {
            switch(type){
                case MODEL:
                    models =  serializer.read(BeanList.class, filePathModel).getList();
                    beans = new ArrayList();
                    models.stream()
                            .forEach(iterator -> {
                                if(((Model)iterator).getTitle().contains(title))beans.add(iterator);
                            });
                    return beans.size() > 0 ? new DataProviderResult(StatusType.SUCCESS, beans,
                            "Model has been found successfully")
                            : new DataProviderResult(StatusType.TITLE_NOT_EXIST,
                            "Title hasn't been found");

                case PURCHASE:
                    purchases =  serializer.read(BeanList.class, filePathPurchase).getList();
                    beans = new ArrayList();
                    purchases.stream()
                            .forEach(iterator -> {
                                if(((Purchase)iterator).getTitle().contains(title))beans.add(iterator);
                            });
                    return beans.size() > 0 ? new DataProviderResult(StatusType.SUCCESS, beans,
                            "Purchase has been found successfully")
                            : new DataProviderResult(StatusType.TITLE_NOT_EXIST,
                            "Title hasn't been found");

                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                            "Wrong Data Type in " + this.getClass().getName());

            }

        } catch (WrongTypeException ex) {
            return new DataProviderResult(StatusType.ERROR, ex,
                    "WrongTypeException has been existed in " + this.getClass().getName());
        } catch(Exception ex){
            return new DataProviderResult(StatusType.ERROR, ex,
                    "There was some error in " + this.getClass().getName());
        }

    }

    @Override
    public DataProviderResult getUserByLogin(String login, TypeOfEntities type){
        try {
            users =  serializer.read(BeanList.class, filePathUser).getList();
            T result = users.stream()
                    .filter(iterator -> Objects.equals(login, ((User)iterator).getLogin()))
                    .findFirst()
                    .orElse(null);
            return result != null ?  new DataProviderResult(StatusType.SUCCESS, result,
                    "User has been found successfully")
                    : new DataProviderResult(StatusType.LOGIN_NOT_EXIST,
                    "Login hasn't been found");
        } catch(WrongTypeException ex){
            return new DataProviderResult(StatusType.WRONG_TYPE, ex,
                    "WrongTypeException has been existed in " + this.getClass().getName());
        } catch (Exception ex) {
            log.info(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                    "There was some error in " + this.getClass().getName());
        }
    }

    @Override
    public DataProviderResult saveRecord(T bean) {
        try {
            switch(bean.getType()){
                case USER:
                    users = serializer.read(BeanList.class, filePathUser).getList();
                    Object duplicate = users.stream()
                            .filter(iterator -> Objects.equals(((User)iterator).getLogin(), ((User)bean).getLogin()))
                            .findFirst()
                            .orElse(null);
                    if(Objects.equals(
                            getRecordById(bean).getStatus()
                            ,StatusType.ID_NOT_EXIST)){
                        if(duplicate == null){
                            users.add(bean);
                            serializer.write(new BeanList(users), filePathUser);
                            return new DataProviderResult(StatusType.SUCCESS, bean, 
                                    "User has been added successfully");
                        } else {
                            serializer.write(new BeanList(users), filePathUser);
                        return new DataProviderResult(StatusType.LOGIN_EXIST, bean, 
                                "This user has the same login with another one");
                        }
                    } else{
                        serializer.write(new BeanList(users), filePathUser);
                        return new DataProviderResult(StatusType.ID_ALLREADY_EXIST, bean, 
                                "This user has been already added");
                    }
                    
                case MODEL:
                    models = serializer.read(BeanList.class, filePathModel).getList();
                    if(Objects.equals(
                            getRecordById(bean).getStatus()
                            ,StatusType.ID_NOT_EXIST)){
                        models.add(bean);
                        serializer.write(new BeanList(models), filePathModel);
                        return new DataProviderResult(StatusType.SUCCESS, bean, 
                                "Model has been added successfully");
                    } else{
                        serializer.write(new BeanList(models), filePathModel);
                        return new DataProviderResult(StatusType.ID_ALLREADY_EXIST, bean, 
                                "Model has been already added");
                    }
                    
                case PURCHASE:
                    clearPurchases();
                    purchases = serializer.read(BeanList.class, filePathPurchase).getList();
                    List<T> usersOfPurchases = serializer.read(BeanList.class, filePathUser).getList();
                    List<T> modelsOfPurchases = serializer.read(BeanList.class, filePathModel).getList();
                    Object userOfPurchase, modelOfPurchase;
                    if(Objects.equals(
                            getRecordById(bean).getStatus()
                            ,StatusType.ID_NOT_EXIST)){
                        userOfPurchase = usersOfPurchases.stream()
                                .filter(iterator -> Objects.equals(iterator.getId(), ((Purchase)bean).getUserId()))
                                .findFirst()
                                .orElse(null);
                        modelOfPurchase = modelsOfPurchases.stream()
                                .filter(iterator -> Objects.equals(iterator.getId(), ((Purchase)bean).getModelId()))
                                .findFirst()
                                .orElse(null);
                        if(userOfPurchase != null && modelOfPurchase != null){
                            purchases.add(bean);
                            serializer.write(new BeanList(purchases), filePathPurchase);
                            serializer.write(new BeanList(usersOfPurchases), filePathUser);
                            serializer.write(new BeanList(modelsOfPurchases), filePathModel);
                            return new DataProviderResult(StatusType.SUCCESS, bean, 
                                    "Purchase has been added successfully");
                        } else {
                            serializer.write(new BeanList(purchases), filePathPurchase);
                            serializer.write(new BeanList(usersOfPurchases), filePathUser);
                            serializer.write(new BeanList(modelsOfPurchases), filePathModel);
                            return new DataProviderResult(StatusType.ID_ALLREADY_EXIST, bean, 
                                "Depended entities haven't been found for this purchase");
                        }
                    } else{
                        serializer.write(new BeanList(purchases), filePathPurchase);
                        serializer.write(new BeanList(usersOfPurchases), filePathUser);
                        serializer.write(new BeanList(modelsOfPurchases), filePathModel);
                        return new DataProviderResult(StatusType.ID_ALLREADY_EXIST, bean, 
                                "This purchase has been already added");
                    }
                    
                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                        "Wrong Data Type in " + this.getClass().getName());
                    
            }
        } catch (Exception ex) {
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex, 
                "There was some error in " + this.getClass().getName()); 
        }
    }
    
    @Override
    public DataProviderResult selectAllRecords(TypeOfEntities type){
        try{
            switch(type){
                
                case USER:
                    users = serializer.read(BeanList.class, filePathUser).getList();
                    return new DataProviderResult(StatusType.SUCCESS, users, 
                        "All users have been returned successfully");
                    
                case MODEL:
                    models = serializer.read(BeanList.class, filePathModel).getList();
                    return new DataProviderResult(StatusType.SUCCESS, models, 
                        "All models have been returned successfully");
                    
                case PURCHASE:
                    purchases = serializer.read(BeanList.class, filePathPurchase).getList();
                    return new DataProviderResult(StatusType.SUCCESS, purchases, 
                        "All purchases have been returned successfully");
                    
                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                        "Wrong Data Type in " + this.getClass().getName());
                    
            }
        } catch(Exception ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                "Some error was existed in " + this.getClass().getName()); 
        }
    }
    
    @Override
    public DataProviderResult selectUsersPurchases(T bean){
        try{
            switch(bean.getType()){
                case USER:
                    purchases = serializer.read(BeanList.class, filePathPurchase).getList();
                    usersPurchases = purchases.stream()
                        .filter(iterator -> Objects.equals(bean.getId(), ((Purchase)iterator).getUserId()))
                        .collect(Collectors.toList());
                    if(usersPurchases.size() > 0){
                        return new DataProviderResult(StatusType.SUCCESS, usersPurchases, 
                            "Purchases have been selected successfully from CSV");
                    } else {
                        return new DataProviderResult(StatusType.PURCHASES_NOT_FOUND, 
                            "User hasn't purchases");
                    }
                    
                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                        "Wrong Data Type in " + this.getClass().getName());
                    
            }
        } catch(Exception ex){
            return new DataProviderResult(StatusType.ERROR, ex,
                "Some error was existed in " + this.getClass().getName());
        }  
    }
    
    @Override
    public DataProviderResult updateRecord(T bean){
        try{
            switch(bean.getType()){

                case USER:
                    users = serializer.read(BeanList.class, filePathUser).getList();
                    T user = users.stream().filter(iterator -> Objects.equals(iterator.getId(), bean.getId()))
                            .findFirst()
                            .orElse(null);
                    if(user != null){
                        users.remove(user);
                        users.add(bean);
                        serializer.write(new BeanList(users), filePathUser);
                        return new DataProviderResult(StatusType.SUCCESS, bean, 
                            "User has been updated successfully");
                    } else {
                        return new DataProviderResult(StatusType.ID_NOT_EXIST, bean, 
                            "User hasn't been updated successfully");
                    }
                    
                case MODEL:
                    models = serializer.read(BeanList.class, filePathModel).getList();
                    T model = models.stream().filter(iterator -> Objects.equals(iterator.getId(), bean.getId()))
                            .findFirst()
                            .orElse(null);
                    if(model != null){
                        models.remove(model);
                        models.add(bean);
                        serializer.write(new BeanList(models), filePathModel);
                        return new DataProviderResult(StatusType.SUCCESS, bean, 
                            "Model has been updated successfully");
                    } else {
                        return new DataProviderResult(StatusType.ID_NOT_EXIST, bean, 
                            "Model hasn't been updated successfully");
                    }
                    
                case PURCHASE:
                    purchases = serializer.read(BeanList.class, filePathPurchase).getList();
                    users = serializer.read(BeanList.class, filePathUser).getList();
                    models = serializer.read(BeanList.class, filePathModel).getList();
                    T purchase = purchases.stream()
                            .filter(iterator -> Objects.equals(iterator.getId(), bean.getId()))
                            .findFirst()
                            .orElse(null);
                    if(purchase != null){
                        purchasesUser = users.stream()
                            .filter(iterator -> Objects.equals(iterator.getId(), ((Purchase)bean).getUserId()))
                            .findFirst()
                            .orElse(null);
                        purchasesModel = models.stream()
                            .filter(iterator -> Objects.equals(iterator.getId(), ((Purchase)bean).getModelId()))
                            .findFirst()
                            .orElse(null);
                        if((purchasesUser != null && purchasesModel != null) ||
                                (purchasesUser == null && purchasesModel != null)){
                            purchases.remove(purchase);
                            purchases.add(bean);
                            serializer.write(new BeanList(purchases), filePathPurchase);
                            return new DataProviderResult(StatusType.SUCCESS, bean, 
                                "Purchase has been updated successfully");
                        } else {
                            return new DataProviderResult(StatusType.DEPENDENCIES_NOT_EXIST,
                                "Purchase has wrong dependecies");
                        }
                    } else {
                        return new DataProviderResult(StatusType.ID_NOT_EXIST, bean, 
                            "Purchase hasn't been updated successfully");
                    }
                    
                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                        "Wrong Data Type in " + this.getClass().getName());
                    
            }
        } catch(Exception ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                "Some error was existed in " + this.getClass().getName());
        }
    }

    @Override
    public DataProviderResult buyModel(T bean){
        try {
            purchases = serializer.read(BeanList.class, filePathPurchase).getList();
            T purchase = purchases.stream()
                    .filter(iterator -> Objects.equals(iterator.getId(), bean.getId()))
                    .findFirst()
                    .orElse(null);
            if(purchase != null){
                ((Purchase)purchase).setStatus(1);
                DataProviderResult result = updateRecord(purchase);
                if(result.getStatus() == StatusType.SUCCESS){
                    return new DataProviderResult(StatusType.SUCCESS, purchase,
                            "Purchase's status has been updated successfully");
                }else {
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
        } catch (Exception ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                    "IllegalStateException has been existed in " + this.getClass().getName());
        }
    }

    public void clearPurchases(){
        try {
            purchases = serializer.read(BeanList.class, filePathPurchase).getList();
            List<T> deletionPurchases;
            deletionPurchases = purchases;
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
        } catch (Exception ex){
            log.error(ex);
        }
    }
    
    public DataProviderResult getRecordByIdForCleaning(Long id, TypeOfEntities type) {
        try{
            List<T> beanList = serializer.read(BeanList.class, filePathUser).getList();
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
        } catch (Exception ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                "WrongTypeException has been existed in " + this.getClass().getName());
        }
    }

    @Override
    public void initDataSource() {
        initBuilderXml();
    }
    
    public DataProviderResult initBuilderXml() {
        try{
            serializer = new Persister();
            filePathUser = new File(ConfigurationUtil.getConfigurationEntry(Constants.START_PATH)
                    + ConfigurationUtil.getConfigurationEntry(Constants.XML_PATH_USERS));
            filePathModel = new File(ConfigurationUtil.getConfigurationEntry(Constants.START_PATH)
                    + ConfigurationUtil.getConfigurationEntry(Constants.XML_PATH_MODEL));
            filePathPurchase = new File(ConfigurationUtil.getConfigurationEntry(Constants.START_PATH)
                    + ConfigurationUtil.getConfigurationEntry(Constants.XML_PATH_PURCHASE));
            return new DataProviderResult(StatusType.SUCCESS, 
                                "InitBuilderXML initiate all paths");
        } catch(IOException ex){
            log.error(ex);
        }
        return new DataProviderResult(StatusType.ERROR,
                "There was some error in " + this.getClass().getName()); 
    }
    
}