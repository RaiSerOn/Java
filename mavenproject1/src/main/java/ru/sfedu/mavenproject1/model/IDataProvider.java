package ru.sfedu.mavenproject1.model;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.mavenproject1.model.entities.Result.DataProviderResult;
import ru.sfedu.mavenproject1.model.entities.TypeOfEntities;

public abstract interface IDataProvider <T>{
    
    public DataProviderResult saveRecord(T bean, TypeOfEntities type) 
            throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;
    
    public DataProviderResult deleteRecord(T bean, TypeOfEntities type);
    
    public DataProviderResult getRecordById(long id, TypeOfEntities type);
    
    public void initDataSource(Class<T> tClass);
}
