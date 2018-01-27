package ru.sfedu.shopproject.model.providers;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.shopproject.model.entities.CSVEntities.User;
import ru.sfedu.shopproject.model.entities.Result.DataProviderResult;
import ru.sfedu.shopproject.model.entities.TypeOfEntities;

/**
 * Interface IDataProvider
 *
 *
 * @author Максим
 */
public abstract interface IDataProvider <T>{
    
    public DataProviderResult saveRecord(T bean);
    
    public DataProviderResult deleteRecord(T bean);
    
    public DataProviderResult deleteUserRecursive(T bean);
    
    public DataProviderResult getRecordById(T bean);

    public DataProviderResult getUserByLogin(String login, TypeOfEntities type);

    public DataProviderResult getEntityByTitle(String title, TypeOfEntities type);
    
    public DataProviderResult selectAllRecords(TypeOfEntities type);
    
    public DataProviderResult updateRecord(T bean);
    
    public DataProviderResult selectUsersPurchases(T bean);

    public DataProviderResult buyModel(T bean);
    
    public void initDataSource();
    
}
