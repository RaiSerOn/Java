/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.shopproject.model.entities.CSVEntities;

import com.opencsv.bean.CsvBindByName;
import java.io.Serializable;
import java.util.Random;
import org.simpleframework.xml.Attribute;
import ru.sfedu.shopproject.model.entities.TypeOfEntities;

/**
 * Class WithId
 *
 *
 * @author Максим
 */
public class WithId implements Serializable{
    
    Random random = new Random();
    
    @Attribute 
    @CsvBindByName
    private Long id;
    
    private TypeOfEntities type;
    
    public WithId(){} 

    public WithId(Long id, TypeOfEntities type){ 
        this.id = id; 
        this.type = type; 
    }
    
    public WithId(TypeOfEntities type){ 
        Long number = random.nextLong();
        if(number < 0) number *= -1;
        this.id = number; 
        this.type = type; 
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }
    
    public TypeOfEntities getType(){
        return type;
    }
    
    public void setType(TypeOfEntities type){
        this.type = type;
    }
    
}
