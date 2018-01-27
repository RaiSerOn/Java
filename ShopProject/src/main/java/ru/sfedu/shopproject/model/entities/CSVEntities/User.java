package ru.sfedu.shopproject.model.entities.CSVEntities;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;

import java.util.Objects;
import java.util.Random;
import org.simpleframework.xml.Attribute;
import ru.sfedu.shopproject.model.entities.TypeOfEntities;

/**
 * Class User
 *
 *
 * @author Максим
 */
public class User extends WithId{
    
//    @CsvBindByPosition(position = 1)
    @CsvBindByName
    private String name;
    
//    @CsvBindByPosition(position = 2)
    @CsvBindByName
    private String sureName;
    
//    @CsvBindByPosition(position = 3)
    @CsvBindByName
    private String thirdName;
    
//    @CsvBindByPosition(position = 4)
    @CsvBindByName
    private String number;
    
//    @CsvBindByPosition(position = 5)
    @CsvBindByName
    private String login;
    
    // 
    // Constructors 
    // 
    
    public User(){
        super(TypeOfEntities.USER);
    }
    
    public User(Long id, String name, String sureName, String thirdName, String number, String login){
        super(id, TypeOfEntities.USER);
        this.name = name;
        this.sureName = sureName;
        this.thirdName = thirdName;
        this.number = number;
        this.login = login;
    }

    public User(String name, String sureName, String thirdName, String number, String login){
        super(TypeOfEntities.USER);
        this.name = name;
        this.sureName = sureName;
        this.thirdName = thirdName;
        this.number = number;
        this.login = login;
    }

    // 
    // Methods 
    // 

    // 
    // Access methods 
    // 

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
    
    /** 
    * Set value of login 
    * @param login - new value of login 
    */ 
    @Element 
    public void setLogin(String login) {
        this.login = login;
    }
    
    /** 
    * Get value of login 
    * @return value of login 
    */ 
    @Element 
    public String getLogin() {
        return login;
    }

    
    // 
    // Other methods 
    // 
    
    
    @Override
    public String toString(){
        return super.getId() + ",'" + name + "', '" + sureName + "', '" + 
                thirdName + "', '" + number + "', '" + login + "'" ;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEquals = false;
        if(obj != null && obj instanceof User){
            isEquals = Objects.equals(this.toString(), obj.toString());
        }
        return isEquals;

    }
     
}
