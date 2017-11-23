package ru.sfedu.mavenproject1.model.entities.XMLEntities;

import java.util.List;
import org.simpleframework.xml.ElementList;


public class BeanList <T>{
    
    @ElementList
    private List<T> beans;
    
    public BeanList(){}
    
    public BeanList(List<T> beans){
        this.beans = beans;
    } 

    public void setList(List<T> beans) {
        this.beans = beans;
    }

    public List<T> getList() {
        return beans;
    }
    
}
