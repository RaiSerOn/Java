/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.mavenproject1.model.entities.CSVEntities;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;
import ru.sfedu.mavenproject1.model.entities.WithId;

/** 
* Class User 
*/ 


public class User extends WithId{
    
    @CsvBindByPosition(position = 0)
    private Long id;
    
    @CsvBindByPosition(position = 1)
    private String name;
    
    @CsvBindByPosition(position = 2)
    private String sureName;
    
    @CsvBindByPosition(position = 3)
    private String thirdName;
    
    @CsvBindByPosition(position = 4)
    private String number;
    
    // 
    // Constructors 
    // 
    
    public User(){}
    
    public User(Long id, String name, String sureName, String thirdName, String number){
        this.id = id;
        this.name = name;
        this.sureName = sureName;
        this.thirdName = thirdName;
        this.number = number;
    }
    
    // 
    // Methods 
    // 

    // 
    // Access methods 
    // 
    
    /** 
    * Set value of id 
    * @param id - new value of id 
    */ 
    @Element 
    public void setId(Long id) {
        this.id = id;
    }
    
    /** 
    * Get value of id 
    * @return value of id 
    */ 
    @Element 
    public Long getId() {
        return id;
    }

    /** 
    * Set value of name 
    * @param name - new value of name
    */
    @Element 
    public void setName(String name) {
        this.name = name;
    }
    
    /** 
    * Get value of name 
    * @return value of name 
    */
    @Element 
    public String getName() {
        return name;
    }
    
    /** 
    * Set value of sureName 
    * @param sureName - new value of sureName
    */
    @Element 
    public void setSureName(String sureName) {
        this.sureName = sureName;
    }

    /** 
    * Get value of sureName 
    * @return value of sureName 
    */
    @Element 
    public String getSureName() {
        return sureName;
    }
    
    /** 
    * Set value of thirdName 
    * @param thirdName - new value of thirdName
    */
    @Element 
    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    /** 
    * Get value of thirdName 
    * @return value of thirdName 
    */
    @Element 
    public String getThirdName() {
        return thirdName;
    }
    
    
    /** 
    * Set value of number 
    * @param number - new value of number
    */
    @Element 
    public void setNumber(String number) {
        this.number = number;
    }

    /** 
    * Get value of number 
    * @return value of number 
    */
    @Element 
    public String getNumber() {
        return number;
    }
    
    // 
    // Other methods 
    // 
    
    @Override
    public String toString(){
        return "id=" + id + ",name=" + name + ",sureName=" + sureName + ",thirdName=" + thirdName + ",number=" + number;
    }
     
}
