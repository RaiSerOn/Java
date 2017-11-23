package ru.sfedu.mavenproject1.model.entities.Result;

import ru.sfedu.mavenproject1.model.entities.StatusType;

/**
 *
 * @author Максим
 */

public class DataProviderResult <T>{
    
    private Enum status;
    private T bean;
    private String message;
    
    public DataProviderResult(StatusType status, T bean, String message){
        this.status = status;
        this.bean = bean;
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
        return "Status: " + status + "\nMessage: " + message + "\nBean: " + bean.getClass();
    }
    
}
