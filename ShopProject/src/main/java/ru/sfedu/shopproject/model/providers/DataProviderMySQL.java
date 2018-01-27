package ru.sfedu.shopproject.model.providers;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import org.apache.log4j.Logger;
import ru.sfedu.shopproject.Constants;
import ru.sfedu.shopproject.exception.WrongTypeException;
import ru.sfedu.shopproject.model.entities.CSVEntities.Model;
import ru.sfedu.shopproject.model.entities.CSVEntities.Purchase;
import ru.sfedu.shopproject.model.entities.CSVEntities.User;
import ru.sfedu.shopproject.model.entities.CSVEntities.WithId;
import ru.sfedu.shopproject.model.entities.Result.DataProviderResult;
import ru.sfedu.shopproject.model.entities.StatusType;
import ru.sfedu.shopproject.model.entities.TypeOfEntities;
import ru.sfedu.shopproject.utils.ConfigurationUtil;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class DataProviderMySQL
 *
 *
 * @author Максим
 */
@SuppressWarnings("all")
public class DataProviderMySQL <T extends WithId> implements IDataProvider<T>{
    public static Logger log = Logger.getLogger(DataProviderMySQL.class);
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet = null;
    private String query;

    @Override
    public DataProviderResult saveRecord(T bean){
        try {
            switch(bean.getType()){
                case USER:
                    query = "SELECT * FROM users WHERE user_id=" + bean.getId() + ";";
                    resultSet = statement.executeQuery(query);
                    if(resultSet.next()) return new DataProviderResult(StatusType.ID_ALLREADY_EXIST,
                            "This user with this id has been found");
                    query = "SELECT * FROM users WHERE user_login='" + ((User)bean).getLogin() + "';";
                    resultSet = statement.executeQuery(query);
                    if(resultSet.next()) return new DataProviderResult(StatusType.LOGIN_EXIST,
                            "This login has been found");
                    query = "INSERT INTO users(user_id, user_name, user_sureName, user_thirdName, user_number, user_login)"
                            + "VALUES(" + bean + ");";
                    statement.executeUpdate(query);
                    return new DataProviderResult(StatusType.SUCCESS, bean,
                        "User has been added successfully");

                case MODEL:
                    query = "SELECT * FROM models WHERE model_id=" + bean.getId() + ";";
                    resultSet = statement.executeQuery(query);
                    if(resultSet.next()) return new DataProviderResult(StatusType.ID_ALLREADY_EXIST,
                            "This id has been found");
                    query = "INSERT INTO models(model_id, model_title, model_mark, "
                            + "model_manufacturer, model_size, model_material, model_price)"
                            + "VALUES(" + bean + ");";
                    statement.executeUpdate(query);
                    return new DataProviderResult(StatusType.SUCCESS, bean,
                        "Model has been added successfully");

                case PURCHASE:
                     query = "SELECT * FROM purchases WHERE purchase_id=" + bean.getId() + ";";
                    resultSet = statement.executeQuery(query);
                    if(resultSet.next()){
                        return new DataProviderResult(StatusType.ID_ALLREADY_EXIST,
                            "This purchase hasn't been found");
                    }
                    query = "INSERT INTO purchases(purchase_id, purchase_title, purchase_user_id, purchase_model_id, purchase_status)"
                            + "VALUES(" + bean + ");";
                    statement.executeUpdate(query);
                    return new DataProviderResult(StatusType.SUCCESS, bean,
                        "Purchase has been added successfully");

                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                        "Wrong Data Type in " + this.getClass().getName());

            }

        }catch (SQLIntegrityConstraintViolationException ex) {
            log.error(ex);
            return new DataProviderResult(StatusType.SQL_INTEGRITY_CONSTRAIN_EXCEPTION, ex,
                    "SQL INTEGRITY CONSTRAIN EXCEPTION");
        } catch (CommunicationsException ex){
            log.error(ex);
            initDataSource();
            return new DataProviderResult(StatusType.COMMUNICATIONS_EXCEPTION, ex,
                    "COMMUNICATIONS EXCEPTION");
        }  catch (SQLException ex) {
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                            "Some error has been existed in " + this.getClass().getName());
        }
    }

    @Override
    public DataProviderResult deleteRecord(T bean) {
        try {
            switch(bean.getType()){
                case USER:
                    query = "SELECT * FROM users WHERE user_id=" + bean.getId() + ";";
                    resultSet = statement.executeQuery(query);
                    if(!resultSet.next()) return new DataProviderResult(StatusType.ID_NOT_EXIST,
                            "This user hasn't been found");
                    query = "SELECT * FROM users WHERE user_login='" + ((User)bean).getLogin() + "';";
                    resultSet = statement.executeQuery(query);
                    if(!resultSet.next()) return new DataProviderResult(StatusType.LOGIN_NOT_EXIST,
                            "This user hasn't been found");
                    query = "SELECT * FROM purchases WHERE purchase_user_id=" + bean.getId()+ ";";
                    resultSet = statement.executeQuery(query);
                    if(!resultSet.next()) {
                        query = "DELETE FROM users WHERE user_login ='" + ((User)bean).getLogin() + "' "
                                + "AND user_id=" + bean.getId() +";";
                        statement.executeUpdate(query);
                        return new DataProviderResult(StatusType.SUCCESS, bean,
                                "User has been deleted successfully");
                    } else {
                        if(!resultSet.next()){
                            resultSet.previous();
                            if(resultSet.getInt(5) == 1){
                                query = "DELETE FROM users WHERE user_login ='" + ((User)bean).getLogin() + "' "
                                        + "AND user_id=" + bean.getId() +";";
                                statement.executeUpdate(query);
                                return new DataProviderResult(StatusType.SUCCESS, bean,
                                        "User has been deleted successfully");
                            }else {
                                return new DataProviderResult(StatusType.DEPENDENCIES_EXISTED, bean,
                                        "User has active purchases");
                            }
                        } else {
                            resultSet.previous();
                            resultSet.previous();
                            while (resultSet.next()){
                                if(resultSet.getInt(5) == 0){
                                    return new DataProviderResult(StatusType.DEPENDENCIES_EXISTED, bean,
                                        "User has active purchases");
                                }
                            }
                            query = "DELETE FROM users WHERE user_login ='" + ((User)bean).getLogin() + "' "
                                    + "AND user_id=" + bean.getId() +";";
                            statement.executeUpdate(query);
                            return new DataProviderResult(StatusType.SUCCESS, bean,
                                    "User has been deleted successfully");
                        }
                    }

                case MODEL:
                    query = "SELECT * FROM models WHERE model_id=" + bean.getId() + ";";
                    resultSet = statement.executeQuery(query);
                    if(!resultSet.next()) return new DataProviderResult(StatusType.ID_NOT_EXIST,
                            "This model hasn't been found");
                    query = "SELECT * FROM purchases WHERE purchase_model_id=" + bean.getId()+ ";";
                    resultSet = statement.executeQuery(query);
                    if(!resultSet.next()) {
                        query = "DELETE FROM models WHERE model_id =" + bean.getId() + ";";
                        statement.executeUpdate(query);
                        return new DataProviderResult(StatusType.SUCCESS, bean,
                            "Model has been deleted successfully");
                    } else {
                        if(!resultSet.next()){
                            resultSet.previous();
                            if(resultSet.getInt(5) == 1){
                                return new DataProviderResult(StatusType.DEPENDENCIES_EXISTED, bean,
                                        "Model has purchases");
                            }else {
                                return new DataProviderResult(StatusType.DEPENDENCIES_EXISTED, bean,
                                        "Model has active purchases");
                            }
                        } else {
                            resultSet.previous();
                            resultSet.previous();
                            while (resultSet.next()){
                                if(resultSet.getInt(5) == 0){
                                    return new DataProviderResult(StatusType.DEPENDENCIES_EXISTED, bean,
                                        "Model has active purchases");
                                }
                            }
                            return new DataProviderResult(StatusType.DEPENDENCIES_EXISTED, bean,
                                        "Model has purchases");
                        }
                    }

                case PURCHASE:
                    query = "SELECT * FROM purchases WHERE purchase_id=" + bean.getId() + ";";
                    resultSet = statement.executeQuery(query);
                    if(!resultSet.next()){
                        return new DataProviderResult(StatusType.ID_NOT_EXIST,
                            "This purchase hasn't been found");
                    }
                    if(resultSet.getInt(5) == 0){
                        return new DataProviderResult(StatusType.STATUS_NOT_SOLD,
                            "This purchase hasn't been solded");
                    }
                    query = "DELETE FROM purchases WHERE purchase_id='" + bean.getId() + "' AND "
                            + "purchase_status = 1;";
                    statement.executeUpdate(query);
                    return new DataProviderResult(StatusType.SUCCESS, bean,
                        "Purchase has been deleted successfully");

                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                        "Wrong Data Type");

            }

        }catch (CommunicationsException ex){
            log.error(ex);
            initDataSource();
            return new DataProviderResult(StatusType.COMMUNICATIONS_EXCEPTION, ex,
                    "COMMUNICATIONS EXCEPTION");
        }catch (SQLException ex) {
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
            "Some error has been existed in " + this.getClass().getName());
        }
    }

    @Override
    public DataProviderResult deleteUserRecursive(T bean){
        try {
            switch(bean.getType()){
                case USER:
                    query = "DELETE FROM users WHERE user_id=" + bean.getId();
                    statement.executeUpdate(query);
                    return new DataProviderResult(StatusType.SUCCESS, bean,
                        "User has been deleted successfully");

                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                        "Wrong Data Type");

            }

        }catch (CommunicationsException ex){
            log.error(ex);
            initDataSource();
            return new DataProviderResult(StatusType.COMMUNICATIONS_EXCEPTION, ex,
                    "COMMUNICATIONS EXCEPTION");
        } catch (SQLException ex) {
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                            "Some error has been existed in " + this.getClass().getName());
        }
    }

    @Override
    public DataProviderResult getUserByLogin(String login, TypeOfEntities type){
        try{
            User selectedUser;
            query = "SELECT * FROM users WHERE user_login ='" + login + "';";
            resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                selectedUser = new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
                );
                return new DataProviderResult(StatusType.SUCCESS, selectedUser,
                        "User has been selected successfully");
            } else {
                return new DataProviderResult(StatusType.LOGIN_NOT_EXIST,
                        "User hasn't been found");
            }
        }catch (CommunicationsException ex){
            log.error(ex);
            initDataSource();
            return new DataProviderResult(StatusType.COMMUNICATIONS_EXCEPTION, ex,
                    "COMMUNICATIONS EXCEPTION");
        }catch(SQLException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                    "Some error has been existed in " + this.getClass().getName());
        }
    }

    @Override
    public DataProviderResult getRecordById(T bean) {
        try {
            switch(bean.getType()){
                case USER:
                    User user;
                    query = "SELECT * FROM users WHERE user_id =" + bean.getId() + ";";
                    resultSet = statement.executeQuery(query);
                    if(resultSet.next()) {
                        user = new User(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6)
                        );
                        return new DataProviderResult(StatusType.SUCCESS, user,
                                "User has been selected successfully");
                    }else {
                        return new DataProviderResult(StatusType.ID_NOT_EXIST, bean,
                                "User hasn't been found");
                    }

                case MODEL:
                    Model model;
                    query = "SELECT * FROM models WHERE model_id =" + bean.getId() + ";";
                    resultSet = statement.executeQuery(query);
                    if(resultSet.next()) {
                        model = new Model(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getLong(7)
                        );
                        return new DataProviderResult(StatusType.SUCCESS, model,
                                "Model have been selected successfully");
                    } else {
                        return new DataProviderResult(StatusType.ID_NOT_EXIST, bean,
                                "Model hasn't been found");
                    }

                case PURCHASE:
                    Purchase purchase;
                    query = "SELECT * FROM purchases WHERE purchase_id =" + bean.getId() + ";";
                    resultSet = statement.executeQuery(query);
                    if(resultSet.next()){
                        purchase = new Purchase(
                            resultSet.getLong(1),
                            resultSet.getString(2),
                            resultSet.getLong(3),
                            resultSet.getLong(4),
                            resultSet.getInt(5)
                        );
                        return new DataProviderResult(StatusType.SUCCESS, purchase,
                            "Purchase have been selected successfully");
                    } else {
                        return new DataProviderResult(StatusType.ID_NOT_EXIST, bean,
                            "Purchase haven't been found");
                    }

                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                        "Wrong Data Type");

            }

        }catch (CommunicationsException ex){
            log.error(ex);
            initDataSource();
            return new DataProviderResult(StatusType.COMMUNICATIONS_EXCEPTION, ex,
                    "COMMUNICATIONS EXCEPTION");
        } catch (SQLException ex) {
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                "Some error has been existed in "  + this.getClass().getName());
        }
    }

    @Override
    public DataProviderResult getEntityByTitle(String title, TypeOfEntities type){
        try{
            switch (type) {

                case MODEL:
                    List<Model> selectedModels = new ArrayList();
                    query = "SELECT * FROM models WHERE model_title LIKE '%" + title + "%';";
                    resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        resultSet.previous();
                        while (resultSet.next()) {
                            selectedModels.add(new Model(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getLong(7)
                            ));
                        }
                        return new DataProviderResult(StatusType.SUCCESS, selectedModels,
                                "Model has been selected successfully");
                    } else {
                        return new DataProviderResult(StatusType.TITLE_NOT_EXIST,
                                "Title hasn't been found");
                    }

                case PURCHASE:
                    List<Purchase> selectedPurchase = new ArrayList();
                    query = "SELECT * FROM purchases WHERE purchase_title LIKE '%" + title + "%';";
                    resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        resultSet.previous();
                        while (resultSet.next()) {
                            selectedPurchase.add(new Purchase(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getLong(3),
                                resultSet.getLong(4),
                                resultSet.getInt(5)
                            ));
                        }
                        return new DataProviderResult(StatusType.SUCCESS, selectedPurchase,
                                "Purchase has been selected successfully");
                    } else {
                        return new DataProviderResult(StatusType.TITLE_NOT_EXIST,
                                "Title hasn't been found");
                    }

                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                            "Wrong Data Type");

            }
        }catch (CommunicationsException ex){
            log.error(ex);
            initDataSource();
            return new DataProviderResult(StatusType.COMMUNICATIONS_EXCEPTION, ex,
                    "COMMUNICATIONS EXCEPTION");
        }catch(SQLException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR, ex,
                    "Some error has been existed in " + this.getClass().getName());
        }
    }

    @Override
    public DataProviderResult selectAllRecords(TypeOfEntities type){
        try{
            switch(type){

                case USER:
                    List<User> usersList = new ArrayList();
                    query = "SELECT * FROM users";
                    resultSet = statement.executeQuery(query);
                    while(resultSet.next()){
                        usersList.add(new User(
                            resultSet.getLong(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6)
                        ));
                    }
                    return new DataProviderResult(StatusType.SUCCESS, usersList,
                        "User have been selected successfully from JDBC");

                case MODEL:
                    List<Model> modelsList = new ArrayList();
                    query = "SELECT * FROM models";
                    resultSet = statement.executeQuery(query);
                    while(resultSet.next()){
                        modelsList.add(new Model(
                            resultSet.getLong(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getLong(7)
                        ));
                    }
                    return new DataProviderResult(StatusType.SUCCESS, modelsList,
                        "Model have been selected successfully from JDBC");

                case PURCHASE:
                    List<Purchase> purchasesList = new ArrayList();
                    query = "SELECT * FROM purchases";
                    resultSet = statement.executeQuery(query);
                    while(resultSet.next()){
                        purchasesList.add(new Purchase(
                            resultSet.getLong(1),
                            resultSet.getString(2),
                            resultSet.getLong(3),
                            resultSet.getLong(4),
                            resultSet.getInt(5)
                        ));
                    }
                    return new DataProviderResult(StatusType.SUCCESS, purchasesList,
                        "Purchase have been selected successfully from JDBC");

                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                        "Wrong Data Type");

            }

        }catch (CommunicationsException ex){
            log.error(ex);
            initDataSource();
            return new DataProviderResult(StatusType.COMMUNICATIONS_EXCEPTION, ex,
                    "COMMUNICATIONS EXCEPTION");
        } catch(IllegalStateException | SQLException ex){
            log.error(ex);
            return new DataProviderResult(StatusType.ERROR,
                "Some error was existed in " + this.getClass().getName());
        }
    }

    @Override
    public DataProviderResult selectUsersPurchases(T bean){
        try{
            switch(bean.getType()){
                case USER:
                    query = "SELECT * FROM purchases WHERE purchase_user_id=" + bean.getId() + ";";
                    resultSet = statement.executeQuery(query);
                    if(resultSet.next()){
                        List<Purchase> purchasesList = new ArrayList();
                        resultSet.previous();
                        while(resultSet.next()){
                            purchasesList.add(new Purchase(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getLong(3),
                                resultSet.getLong(4),
                                resultSet.getInt(5)
                            ));
                        }
                        return new DataProviderResult(StatusType.SUCCESS, purchasesList,
                            "Purchases have been selected successfully from JDBC");
                    } else {
                        return new DataProviderResult(StatusType.PURCHASES_NOT_FOUND, bean,
                            "User hasn't purchases or user haven't purchases");
                    }

                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                        "Wrong Data Type");

            }
        }catch (CommunicationsException ex){
            log.error(ex);
            initDataSource();
            return new DataProviderResult(StatusType.COMMUNICATIONS_EXCEPTION, ex,
                    "COMMUNICATIONS EXCEPTION");
        } catch(SQLException ex){
            return new DataProviderResult(StatusType.ERROR, ex,
                "Wrong Data Type in " + this.getClass().getName());
        }
    }

    @Override
    public DataProviderResult updateRecord(T bean){
        try{
            switch(bean.getType()){
                case USER:
                    query = "SELECT * FROM users WHERE user_id=" + bean.getId() + ";";
                    resultSet = statement.executeQuery(query);
                    if(!resultSet.next()) return new DataProviderResult(StatusType.ID_NOT_EXIST,
                            "This user hasn't been found");
                    query = "UPDATE users SET " +
                            " user_name = '" + ((User)bean).getName() +
                            "', user_sureName = '" + ((User)bean).getSureName() +
                            "', user_number = '" + ((User)bean).getNumber() +
                            "', user_login = '" + ((User)bean).getLogin() +
                            "' WHERE user_id =" + bean.getId() + ";";
                    statement.executeUpdate(query);
                    return new DataProviderResult(StatusType.SUCCESS, bean,
                        "User has been updated successfully");

                case MODEL:
                    query = "SELECT * FROM models WHERE model_id=" + bean.getId() + ";";
                    resultSet = statement.executeQuery(query);
                    if(!resultSet.next()) return new DataProviderResult(StatusType.ID_NOT_EXIST,
                            "This model hasn't been found");
                    query = "UPDATE models SET " +
                            " model_title = '" + ((Model)bean).getTitle() +
                            "', model_mark = '" + ((Model)bean).getMark()+
                            "', model_manufacturer = '" + ((Model)bean).getManufacturer()+
                            "', model_size = '" + ((Model)bean).getSize()+
                            "', model_material = '" + ((Model)bean).getMaterial()+
                            "', model_price = " + ((Model)bean).getPrice()+
                            " WHERE model_id =" + bean.getId() + ";";
                    statement.executeUpdate(query);
                    return new DataProviderResult(StatusType.SUCCESS, bean,
                        "Model has been updated successfully");

                case PURCHASE:
                    query = "SELECT * FROM purchases WHERE purchase_id=" + bean.getId() + ";";
                    resultSet = statement.executeQuery(query);
                    if(!resultSet.next()){
                        return new DataProviderResult(StatusType.ID_NOT_EXIST,
                                "This purchase hasn't been found");
                    }
                    query = "UPDATE purchases SET" +
                            " purchase_title = '" + ((Purchase)bean).getTitle() +
                            "', purchase_user_id = " + ((Purchase)bean).getUserId() +
                            ", purchase_model_id = " + ((Purchase)bean).getModelId() +
                            ", purchase_status = " + ((Purchase)bean).getStatus()+
                            " WHERE purchase_id =" + bean.getId() + ";";
                    statement.executeUpdate(query);
                    return new DataProviderResult(StatusType.SUCCESS, bean,
                        "Model has been updated successfully");

                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                        "Wrong Data Type");

            }
        }catch (CommunicationsException ex){
            log.error(ex);
            initDataSource();
            return new DataProviderResult(StatusType.COMMUNICATIONS_EXCEPTION, ex,
                    "COMMUNICATIONS EXCEPTION");
        }catch (SQLIntegrityConstraintViolationException ex) {
            log.error(ex);
            return new DataProviderResult(StatusType.SQL_INTEGRITY_CONSTRAIN_EXCEPTION, ex,
                    "SQL INTEGRITY CONSTRAIN EXCEPTION");
        } catch(SQLException ex){
            return new DataProviderResult(StatusType.ERROR, ex,
                "Some error was existed in " + this.getClass().getName());
        }
    }

    @Override
    public DataProviderResult buyModel(T bean){
        try{
            switch(bean.getType()){
                case PURCHASE:
                    query = "SELECT * FROM purchases WHERE purchase_id=" + bean.getId() + ";";
                    resultSet = statement.executeQuery(query);
                    if(resultSet.next()){
                        Purchase purchase = new Purchase(
                            resultSet.getLong(1),
                            resultSet.getString(2),
                            resultSet.getLong(3),
                            resultSet.getLong(4),
                            resultSet.getInt(5)
                        );
                        purchase.setStatus(1);
                        updateRecord(((T)purchase));
                        return new DataProviderResult(StatusType.SUCCESS, purchase,
                                "Purchases have been selected successfully from JDBC");
                    } else {
                        return new DataProviderResult(StatusType.PURCHASES_NOT_FOUND, bean,
                                "User hasn't purchases");
                    }

                default:
                    return new DataProviderResult(StatusType.WRONG_TYPE, new WrongTypeException(),
                            "Wrong Data Type");

            }
        }catch (CommunicationsException ex){
            log.error(ex);
            initDataSource();
            return new DataProviderResult(StatusType.COMMUNICATIONS_EXCEPTION, ex,
                    "COMMUNICATIONS EXCEPTION");
        } catch(SQLException ex){
            return new DataProviderResult(StatusType.ERROR, ex,
                    "Wrong Data Type in " + this.getClass().getName());
        }
    }

    public void clearPurchases(){
        try {
            query = "DELETE FROM purchases WHERE purchase_status = 1";
            statement.executeUpdate(query);
        }catch (CommunicationsException ex){
            log.error(ex);
            initDataSource();
        } catch (SQLException ex){
            log.error(ex);
        }
    }

    @Override
    public void initDataSource() {
        openProvider();
    }

    public DataProviderResult openProvider(){
        try{
            String url = ConfigurationUtil.getConfigurationEntry(Constants.DB_PATH_CONNECT_URL);
            String user = ConfigurationUtil.getConfigurationEntry(Constants.DB_PATH_CONNECT_USER);
            String password = ConfigurationUtil.getConfigurationEntry(Constants.DB_PATH_CONNECT_PASSWORD);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            return new DataProviderResult(StatusType.SUCCESS, "DataProvider has been opened successfully");
        }catch (CommunicationsException ex){
            log.error(ex);
            initDataSource();
            return new DataProviderResult(StatusType.COMMUNICATIONS_EXCEPTION, ex,
                    "COMMUNICATIONS EXCEPTION");
        }catch(SQLException | IOException ex){
            log.info(ex);
            return new DataProviderResult(StatusType.ERROR, ex, "There was some error in "
                + this.getClass().getName());
        }
    }

    public DataProviderResult closeProvider(){
        try {
            connection.close();
            statement.close();
            if(resultSet != null){
                resultSet.close();
            }
            return new DataProviderResult(StatusType.SUCCESS, "DataProvider has been closed successfully");
        }catch (CommunicationsException ex){
            log.error(ex);
            initDataSource();
            return new DataProviderResult(StatusType.COMMUNICATIONS_EXCEPTION, ex,
                    "COMMUNICATIONS EXCEPTION");
        } catch(SQLException ex) {
            log.info(ex);
            return new DataProviderResult(StatusType.ERROR, ex, "There was some error in "
                + this.getClass().getName());
        }
    }

}