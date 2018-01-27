package ru.sfedu.shopproject;

import java.util.Random;

import ru.sfedu.shopproject.model.entities.CSVEntities.Model;
import ru.sfedu.shopproject.model.entities.CSVEntities.Purchase;
import ru.sfedu.shopproject.model.entities.CSVEntities.User;

/**
 *
 * @author Максим
 */
public class DataGenerator {
    
    public static final Random random = new Random();
    
    public static User createUser(){
        User user = new User();
        Long myLong = random.nextLong();
        if(myLong < 0){
            myLong *= -1;
        }
        user.setId(myLong);
        user.setName("Name_" + random.nextInt());
        user.setSureName("SureName_" + random.nextInt());
        user.setThirdName("ThirdName_" + random.nextInt());
        user.setNumber("+" + myLong);
        user.setLogin("Login_" + myLong);
        return user;
    }

    public static Model createModel(){
        Model model = new Model();
        Long myLong = random.nextLong();
        if(myLong < 0){
            myLong *= -1;
        }
        model.setId(myLong);
        model.setTitle("Title_" + random.nextInt());
        model.setMark("Mark_" + random.nextInt());
        model.setManufacturer("Manufacturer_" + random.nextInt());
        model.setSize("Size_" + random.nextInt());
        model.setMaterial("Material_" + random.nextInt());
        model.setPrice(myLong);
        return model;
    }

    public static Purchase createPurchase(Long userId, Long modelId){
        Purchase purchase = new Purchase();
        Long myLong = random.nextLong();
        if(myLong < 0){
            myLong *= -1;
        }
        purchase.setId(myLong);
        purchase.setTitle("Title_" + random.nextInt());
        purchase.setStatus(0);
        purchase.setUserId(userId);
        purchase.setModelId(modelId);
        return purchase;
    }
    
}
