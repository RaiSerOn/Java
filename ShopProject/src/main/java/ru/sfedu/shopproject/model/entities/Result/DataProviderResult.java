package ru.sfedu.shopproject.model.entities.Result;

import java.util.List;
import ru.sfedu.shopproject.model.entities.StatusType;

/**
 * Class DataProviderResult
 *
 *
 * @author Максим
 */
public class DataProviderResult <T>{
    
    private Enum status;
    private T bean;
    private String message;
    private List<T> beans = null;
    
    public DataProviderResult(StatusType status, T bean, String message){
        this.status = status;
        this.bean = bean;
        this.message = message;
    }
    
    public DataProviderResult(StatusType status, List<T> beans, String message){
        this.status = status;
        this.beans = beans;
        this.message = message;
    }
    
    public DataProviderResult(StatusType status, String message){
        this.status = status;
        this.bean = null;
        this.message = message;
    }

    public Enum getStatus() {
        return status;
    }

    public T getBean() {
        return bean;
    }
    
    public List<T> getBeans() {
        return beans;
    }
    
    public void setBeans(List<T> beans) {
        this.beans = beans;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String toString(){
        return "Status: " + status + "\nMessage: " + message;
    }
    
}
