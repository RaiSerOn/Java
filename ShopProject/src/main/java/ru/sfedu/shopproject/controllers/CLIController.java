package ru.sfedu.shopproject.controllers;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.log4j.Logger;
import ru.sfedu.shopproject.Main;
import ru.sfedu.shopproject.model.entities.CSVEntities.Model;
import ru.sfedu.shopproject.model.entities.CSVEntities.Purchase;
import ru.sfedu.shopproject.model.entities.CSVEntities.User;
import ru.sfedu.shopproject.model.entities.CSVEntities.WithId;
import ru.sfedu.shopproject.model.entities.Result.DataProviderResult;
import ru.sfedu.shopproject.model.entities.StatusType;
import ru.sfedu.shopproject.model.entities.TypeOfEntities;
import ru.sfedu.shopproject.model.providers.DataProviderCSV;
import ru.sfedu.shopproject.model.providers.DataProviderMySQL;
import ru.sfedu.shopproject.model.providers.DataProviderXML;
import ru.sfedu.shopproject.model.providers.IDataProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import ru.sfedu.shopproject.model.entities.wrappers.EntityWrapper;

@SuppressWarnings("all")
public class CLIController <T extends WithId>{

    private static final Logger log = Logger.getLogger(Main.class);

    public IDataProvider initDataProvider(IDataProvider iDataProvider, String provider){
        if (provider.equals("CSV")) {
            if(iDataProvider instanceof DataProviderMySQL){
                ((DataProviderMySQL) iDataProvider).closeProvider();
            }
            iDataProvider = new DataProviderCSV();
            iDataProvider.initDataSource();
            ((DataProviderCSV)iDataProvider).clearPurchases();
            System.out.println("CSV has been chosen");
            return iDataProvider;
        } else if (provider.equals("XML")) {
            if(iDataProvider instanceof DataProviderMySQL){
                ((DataProviderMySQL) iDataProvider).closeProvider();
            }
            iDataProvider = new DataProviderXML();
            iDataProvider.initDataSource();
            ((DataProviderXML)iDataProvider).clearPurchases();
            System.out.println("XML has been chosen");
            return iDataProvider;
        } else if (provider.equals("JDBC")) {
            if(iDataProvider instanceof DataProviderMySQL){
                ((DataProviderMySQL) iDataProvider).closeProvider();
            }
            iDataProvider = new DataProviderMySQL();
            iDataProvider.initDataSource();
            System.out.println("JDBC has been chosen");
            return iDataProvider;
        } else {
            System.out.println("Wrong Data Provider!");
            System.out.println("Use 'CSV', 'XML', 'JDBC'!");
            return null;
        }
    }

    public void savingModel(IDataProvider iDataProvider){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String[] arguments;
            Model model;
            while(true){
                try{
                System.out.println("Enter model's parameters");
                System.out.println("title mark manufacturer size material price");
                arguments = reader.readLine().split(" ");
                for(String str : arguments) System.out.println(str);
                model = new Model(
                    arguments[0],
                    arguments[1],
                    arguments[2],
                    arguments[3],
                    arguments[4],
                    Long.parseLong(arguments[5])
                );
                break;
                } catch(NumberFormatException ex){
                    System.out.println("'Price' format not valid, try again");
                    log.error(ex);
                }
            }
            DataProviderResult result = iDataProvider.saveRecord(model);
            System.out.println(result.getStatus() + " : " + result.getMessage());
        } catch(IOException ex){
            log.error(ex);
        } catch (ArrayIndexOutOfBoundsException ex){
            log.error(ex);
            System.out.println("Invalid number of parametrs");
        }
    }

    public void savingUser(IDataProvider iDataProvider){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String[] arguments;
            System.out.println("Enter user's parameters");
            System.out.println("name surename thirdname number login");
            arguments = reader.readLine().split(" ");
            User user = new User(
                    arguments[0],
                    arguments[1],
                    arguments[2],
                    arguments[3],
                    arguments[4]
            );
            DataProviderResult result = iDataProvider.saveRecord(user);
            System.out.println(result.getStatus() + " : " + result.getMessage());
        } catch(IOException ex){
            log.error(ex);
        } catch (ArrayIndexOutOfBoundsException ex){
            log.error(ex);
        }
    }

    public void savingPurchase(IDataProvider iDataProvider){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            List<User> users = iDataProvider.selectAllRecords(TypeOfEntities.USER).getBeans();
            List<EntityWrapper> resultSearchUser   = new ArrayList();
            List<Model> models = iDataProvider.selectAllRecords(TypeOfEntities.MODEL).getBeans();
            List<EntityWrapper> resultSearchModel = new ArrayList();
            int index = 0;
            String title, buffer;
            Long userId = 0L, modelId = 0L;
            System.out.println("Enter purchase's parameters");
            System.out.println("title");
            title = reader.readLine();
            for (User user : users) {
                    resultSearchUser.add(new EntityWrapper(index, user));
                    System.out.println(resultSearchUser.get(index).getIndex() + 1 + ") " + 
                            resultSearchUser.get(index).getEntity().toString());
                    index++;
                }
            System.out.println("\nSelect index of user or enter 'quit' to quit!");
            buffer = reader.readLine();
            if (buffer.equals("quit")) {
                return;
            }
            index = Integer.parseInt(buffer);
            for (EntityWrapper wrapper : resultSearchUser) {
                if (wrapper.getIndex() == index - 1) {
                    userId = ((User)wrapper.getEntity()).getId();
                }
            }
            if(index > 0 && index <= users.size()) {
                index = 0;
                for (Model model : models) {
                    resultSearchModel.add(new EntityWrapper(index, model));
                    System.out.println(resultSearchModel.get(index).getIndex() + 1 + ") " + 
                            resultSearchModel.get(index).getEntity().toString());
                    index++;
                }
                System.out.println("\nSelect index of model or enter 'quit' to quit!");
                buffer = reader.readLine();
                if (buffer.equals("quit")) {
                    return;
                }
                index = Integer.parseInt(buffer);
                for (EntityWrapper wrapper : resultSearchModel) {
                    if (wrapper.getIndex() == index - 1) {
                        modelId = ((Model)wrapper.getEntity()).getId();
                    }
                }
                if(index <= models.size() && index > 0){
                    Purchase purchase = new Purchase(
                            title,
                            userId,
                            modelId,
                            0
                    );
                    DataProviderResult result = iDataProvider.saveRecord(purchase);
                    System.out.println(result.getStatus() + " : " + result.getMessage());
                } else {
                    System.out.println("You enter wrong index or list of Models is empty");
                }
            } else {
                System.out.println("You enter wrong index or list of Users is empty");
            }

        } catch(IOException ex){
            log.error(ex);
        } catch (ArrayIndexOutOfBoundsException ex){
            log.error(ex);
        }
    }

    public void deleteUser(IDataProvider iDataProvider){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            List<User> users = iDataProvider.selectAllRecords(TypeOfEntities.USER).getBeans();
            List<EntityWrapper> resultSearchUser = new ArrayList();
            String buffer;
            int index = 0;
            for (User user : users) {
                resultSearchUser.add(new EntityWrapper(index, user));
                System.out.println(resultSearchUser.get(index).getIndex() + 1 + ") " + resultSearchUser
                        .get(index).getEntity().toString());
                index++;
            }
            while (true) {
                System.out.println("\nSelect index of user, which you want delete or enter 'quit' to quit!");
                buffer = reader.readLine();
                if (buffer.equals("quit")) {
                    return;
                }
                index = Integer.parseInt(buffer);
                for (EntityWrapper wrapper : resultSearchUser) {
                    if (wrapper.getIndex() == index - 1) {
                        DataProviderResult result = iDataProvider.deleteRecord(resultSearchUser
                                .get(index - 1).getEntity());
                        System.out.println(result.getStatus() + " : " + result.getMessage());
                        break;
                    }
                    
                }
                if(index <= 0 || index > resultSearchUser.size()){
                    System.out.println("You enter wrong index or list of Users is empty");
                }else {
                    resultSearchUser.remove(index - 1);
                }
                System.in.read();
                resultSearchUser.forEach((user) -> {
                    System.out.println(user.getIndex() + 1 + ") " + user.getEntity().toString());
                });
            }
        } catch(IOException ex){
            log.info(ex);
        } catch (ArrayIndexOutOfBoundsException ex){
            log.info(ex);
        } catch (NumberFormatException ex){
            log.info(ex);
        }
    }
       
    public void deleteUserRecursive(IDataProvider iDataProvider){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            List<User> users = iDataProvider.selectAllRecords(TypeOfEntities.USER).getBeans();
            List<EntityWrapper> resultSearchUser = new ArrayList();
            String buffer;
            int index = 0;
            for (User user : users) {
                resultSearchUser.add(new EntityWrapper(index, user));
                System.out.println(resultSearchUser.get(index).getIndex() + 1 + ") " + resultSearchUser
                        .get(index).getEntity().toString());
                index++;
            }
            while (true) {
                System.out.println("\nSelect index of user, which you want delete recursive or enter 'quit' to quit!");
                buffer = reader.readLine();
                if (buffer.equals("quit")) {
                    return;
                }
                index = Integer.parseInt(buffer);
                for (EntityWrapper wrapper : resultSearchUser) {
                    if (wrapper.getIndex() == index - 1) {
                        DataProviderResult result = iDataProvider
                                .deleteUserRecursive(resultSearchUser.get(index - 1).getEntity());
                        System.out.println(result.getStatus() + " : " + result.getMessage());
                        break;
                    }
                    
                }
                if(index <= 0 || index > resultSearchUser.size()){
                    System.out.println("You enter wrong index or list of Users is empty");
                } else {
                    resultSearchUser.remove(index - 1);
                }
                System.in.read();
                resultSearchUser.forEach((user) -> {
                    System.out.println(user.getIndex() + 1 + ") " + user.getEntity().toString());
                });
            }
        } catch(IOException ex){
            log.info(ex);
        } catch (ArrayIndexOutOfBoundsException ex){
            log.info(ex);
        } catch (NumberFormatException ex){
            log.info(ex);
        }
    }

    public void deleteModel(IDataProvider iDataProvider){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            List<Model> models = iDataProvider.selectAllRecords(TypeOfEntities.MODEL).getBeans();
            List<EntityWrapper> resultSearchModel = new ArrayList();
            String buffer;
            int index = 0;
            for (Model model : models) {
                resultSearchModel.add(new EntityWrapper(index, model));
                System.out.println(resultSearchModel.get(index).getIndex() + 1 + ") "
                        + resultSearchModel.get(index).getEntity().toString());
                index++;
            }
            while (true) {
                System.out.println("\nSelect index of model, which you want delete or enter 'quit' to quit!");
                buffer = reader.readLine();
                if (buffer.equals("quit")) {
                    return;
                }
                index = Integer.parseInt(buffer);
                for (EntityWrapper wrapper : resultSearchModel) {
                    if (wrapper.getIndex() == index - 1) {
                        DataProviderResult result = iDataProvider.deleteRecord(resultSearchModel
                                .get(index - 1).getEntity());
                        System.out.println(result.getStatus() + " : " + result.getMessage());
                        break;
                    }
                }
                if(index <= 0 || index > resultSearchModel.size()){
                    System.out.println("You enter wrong index or list of Models is empty");
                }else {
                    resultSearchModel.remove(index - 1);
                }
                System.in.read();
                resultSearchModel.forEach((model) -> {
                    System.out.println(model.getIndex() + 1 + ") " + model.getEntity().toString());
                });
            }
        } catch(IOException ex){
            log.info(ex);
        } catch (ArrayIndexOutOfBoundsException ex){
            log.info(ex);
        } catch (NumberFormatException ex){
            log.info(ex);
        }
    }

    public void deletePurchase(IDataProvider iDataProvider){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            List<Purchase> purchases = iDataProvider.selectAllRecords(TypeOfEntities.PURCHASE).getBeans();
            List<EntityWrapper> resultSearchPurchase = new ArrayList();
            int index = 0;
            String buffer;
            for (Purchase purchase : purchases) {
                resultSearchPurchase.add(new EntityWrapper(index, purchase));
                System.out.println(resultSearchPurchase.get(index).getIndex() + 1 + ") "
                        + resultSearchPurchase.get(index).getEntity().toString());
                index++;
            }
            while (true) {
                System.out.println("\nSelect index of model, which you want delete or enter 'quit' to quit!");
                buffer = reader.readLine();
                if (buffer.equals("quit")) {
                    return;
                }
                index = Integer.parseInt(buffer);
                for (EntityWrapper wrapper : resultSearchPurchase) {
                    if (wrapper.getIndex() == index - 1) {
                        DataProviderResult result = iDataProvider.deleteRecord(resultSearchPurchase
                                .get(index - 1).getEntity());
                        System.out.println(result.getStatus() + " : " + result.getMessage());
                        break;
                    }
                }
                if(index <= 0 || index > resultSearchPurchase.size()){
                    System.out.println("You enter wrong index or list of Purchases is empty");
                }else {
                    resultSearchPurchase.remove(index - 1);
                }
                System.in.read();
                resultSearchPurchase.forEach((purchase) -> {
                    System.out.println(purchase.getIndex() + 1 + ") " + purchase.getEntity().toString());
                });
            }
        } catch(IOException ex){
            log.info(ex);
        } catch (ArrayIndexOutOfBoundsException ex){
            log.info(ex);
        } catch (NumberFormatException ex){
            log.info(ex);
        }
    }

    public void updateUser(IDataProvider iDataProvider){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            DataProviderResult result;
            List<User> users = iDataProvider.selectAllRecords(TypeOfEntities.USER).getBeans();
            List<EntityWrapper> resultSearchUser = new ArrayList();
            User userChosen = null;
            User candidate;
            int chose = 0;
            String valueUpdate, object, buffer;
            for (User user : users) {
                resultSearchUser.add(new EntityWrapper(chose, user));
                System.out.println(resultSearchUser.get(chose).getIndex() + 1 + ") "
                        + resultSearchUser.get(chose).getEntity().toString());
                chose++;
            }
            while (true) {
                System.out.println("\nSelect user's index, which you want update or enter 'quit' to quit!");
                buffer = reader.readLine();
                if (buffer.equals("quit")) {
                    return;
                }
                chose = Integer.parseInt(buffer);
                for (EntityWrapper wrapper : resultSearchUser) {
                    if (wrapper.getIndex() == chose - 1) {
                        userChosen = ((User)wrapper.getEntity());
                        break;
                    }
                }
                if(userChosen != null) {
                    System.out.println();
                    System.out.println("What we will be change?");
                    System.out.println("Name");
                    System.out.println("SureName");
                    System.out.println("ThirdName");
                    System.out.println("Number");
                    System.out.println("Login");
                    object = reader.readLine();
                    switch (object) {
                        case "Name":
                            System.out.println("Enter your value for update");
                            valueUpdate = reader.readLine();
                            candidate = new User(
                                    userChosen.getId(),
                                    valueUpdate,
                                    userChosen.getSureName(),
                                    userChosen.getThirdName(),
                                    userChosen.getNumber(),
                                    userChosen.getLogin()
                            );
                            result = iDataProvider.updateRecord(candidate);
                            System.out.println(result.getStatus() + " : " + result.getMessage());
                            break;

                        case "SureName":
                            System.out.println("Enter your value for update");
                            valueUpdate = reader.readLine();
                            candidate = new User(
                                    userChosen.getId(),
                                    userChosen.getName(),
                                    valueUpdate,
                                    userChosen.getThirdName(),
                                    userChosen.getNumber(),
                                    userChosen.getLogin()
                            );
                            result = iDataProvider.updateRecord(candidate);
                            System.out.println(result.getStatus() + " : " + result.getMessage());
                            break;

                        case "ThirdName":
                            System.out.println("Enter your value for update");
                            valueUpdate = reader.readLine();
                            candidate = new User(
                                    userChosen.getId(),
                                    userChosen.getName(),
                                    userChosen.getSureName(),
                                    valueUpdate,
                                    userChosen.getNumber(),
                                    userChosen.getLogin()
                            );
                            result = iDataProvider.updateRecord(candidate);
                            System.out.println(result.getStatus() + " : " + result.getMessage());
                            break;

                        case "Number":
                            System.out.println("Enter your value for update");
                            valueUpdate = reader.readLine();
                            candidate = new User(
                                    userChosen.getId(),
                                    userChosen.getName(),
                                    userChosen.getSureName(),
                                    userChosen.getThirdName(),
                                    valueUpdate,
                                    userChosen.getLogin()
                            );
                            result = iDataProvider.updateRecord(candidate);
                            System.out.println(result.getStatus() + " : " + result.getMessage());
                            break;

                        case "Login":
                            System.out.println("Enter your value for update");
                            valueUpdate = reader.readLine();
                            candidate = new User(
                                    userChosen.getId(),
                                    userChosen.getName(),
                                    userChosen.getSureName(),
                                    userChosen.getThirdName(),
                                    userChosen.getNumber(),
                                    valueUpdate
                            );
                            result = iDataProvider.updateRecord(candidate);
                            System.out.println(result.getStatus() + " : " + result.getMessage());
                            break;

                        default:
                            System.out.println("Your chose isn't right!");
                    }
                }
//                System.in.read();
                userChosen = null;
            }

        } catch (IOException ex){
            log.error(ex);
        } catch (NumberFormatException ex){
            log.error(ex);
        }
    }

    public void updateModel(IDataProvider iDataProvider){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            DataProviderResult result;
            List<Model> models = iDataProvider.selectAllRecords(TypeOfEntities.MODEL).getBeans();
            List<EntityWrapper> resultSearchModel = new ArrayList();
            Model modelChosen = null;
            Model candidate;
            int chose = 0;
            String valueUpdate, object, buffer;
            for (Model model : models) {
                resultSearchModel.add(new EntityWrapper(chose, model));
                System.out.println(resultSearchModel.get(chose).getIndex() + 1 + ") "
                        + resultSearchModel.get(chose).getEntity().toString());
                chose++;
            }
            while (true) {
                System.out.println("\nSelect model's index, which you want update or enter 'quit' to quit!");
                buffer = reader.readLine();
                if (buffer.equals("quit")) {
                    return;
                }
                chose = Integer.parseInt(buffer);
                for (EntityWrapper wrapper : resultSearchModel) {
                    if (wrapper.getIndex() == chose - 1) {
                        modelChosen = ((Model)wrapper.getEntity());
                        break;
                    }
                }
                if(modelChosen != null) {
                    System.out.println();
                    System.out.println("What we will be change?");
                    System.out.println("Title");
                    System.out.println("Mark");
                    System.out.println("Manufacturer");
                    System.out.println("Size");
                    System.out.println("Material");
                    System.out.println("Price");
                    object = reader.readLine();
                    switch (object) {
                        case "Title":
                            System.out.println("Enter your value for update");
                            valueUpdate = reader.readLine();
                            candidate = new Model(
                                    modelChosen.getId(),
                                    valueUpdate,
                                    modelChosen.getMark(),
                                    modelChosen.getManufacturer(),
                                    modelChosen.getSize(),
                                    modelChosen.getMaterial(),
                                    modelChosen.getPrice()
                            );
                            result = iDataProvider.updateRecord(candidate);
                            System.out.println(result.getStatus() + " : " + result.getMessage());
                            break;

                        case "Mark":
                            System.out.println("Enter your value for update");
                            valueUpdate = reader.readLine();
                            candidate = new Model(
                                    modelChosen.getId(),
                                    modelChosen.getTitle(),
                                    valueUpdate,
                                    modelChosen.getManufacturer(),
                                    modelChosen.getSize(),
                                    modelChosen.getMaterial(),
                                    modelChosen.getPrice()
                            );
                            result = iDataProvider.updateRecord(candidate);
                            System.out.println(result.getStatus() + " : " + result.getMessage());
                            break;

                        case "Manufacturer":
                            System.out.println("Enter your value for update");
                            valueUpdate = reader.readLine();
                            candidate = new Model(
                                    modelChosen.getId(),
                                    modelChosen.getTitle(),
                                    modelChosen.getMark(),
                                    valueUpdate,
                                    modelChosen.getSize(),
                                    modelChosen.getMaterial(),
                                    modelChosen.getPrice()
                            );
                            result = iDataProvider.updateRecord(candidate);
                            System.out.println(result.getStatus() + " : " + result.getMessage());
                            break;

                        case "Size":
                            System.out.println("Enter your value for update");
                            valueUpdate = reader.readLine();
                            candidate = new Model(
                                    modelChosen.getId(),
                                    modelChosen.getTitle(),
                                    modelChosen.getMark(),
                                    modelChosen.getManufacturer(),
                                    valueUpdate,
                                    modelChosen.getMaterial(),
                                    modelChosen.getPrice()
                            );
                            result = iDataProvider.updateRecord(candidate);
                            System.out.println(result.getStatus() + " : " + result.getMessage());
                            break;

                        case "Material":
                            System.out.println("Enter your value for update");
                            valueUpdate = reader.readLine();
                            candidate = new Model(
                                    modelChosen.getId(),
                                    modelChosen.getTitle(),
                                    modelChosen.getMark(),
                                    modelChosen.getManufacturer(),
                                    modelChosen.getSize(),
                                    valueUpdate,
                                    modelChosen.getPrice()
                            );
                            result = iDataProvider.updateRecord(candidate);
                            System.out.println(result.getStatus() + " : " + result.getMessage());
                            break;

                        case "Price":
                            while(true){
                                try{
                                    System.out.println("Enter your value for update");
                                    valueUpdate = reader.readLine();
                                    candidate = new Model(
                                            modelChosen.getId(),
                                            modelChosen.getTitle(),
                                            modelChosen.getMark(),
                                            modelChosen.getManufacturer(),
                                            modelChosen.getSize(),
                                            modelChosen.getMaterial(),
                                            Long.parseLong(valueUpdate)
                                    );
                                    break;
                                } catch(NumberFormatException ex){
                                    System.out.println("'Price' format not valid, try again");
                                }
                            }
                            result = iDataProvider.updateRecord(candidate);
                            System.out.println(result.getStatus() + " : " + result.getMessage());
                            break;

                        default:
                            System.out.println("Your chose isn't right!");
                    }
                }
//                System.in.read();
                modelChosen = null;
            }
        } catch (IOException ex){
            log.error(ex);
        } catch (NumberFormatException ex){
            log.error(ex);
        }
    }

    public void updatePurchase(IDataProvider iDataProvider){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            DataProviderResult result;
            List<Purchase> purchases = iDataProvider.selectAllRecords(TypeOfEntities.PURCHASE).getBeans();
            List<EntityWrapper> resultSearchPurchase = new ArrayList();
            Purchase purchaseChosen = null;
            Purchase candidate;
            int chose = 0;
            String valueUpdate, object, buffer;
            for (Purchase purchase : purchases) {
                resultSearchPurchase.add(new EntityWrapper(chose, purchase));
                System.out.println(resultSearchPurchase.get(chose).getIndex() + 1 + ") "
                        + resultSearchPurchase.get(chose).getEntity().toString());
                chose++;
            }
            while (true) {
                System.out.println("\nSelect purchase's index, which you want update or enter 'quit' to quit!");
                buffer = reader.readLine();
                if (buffer.equals("quit")) {
                    return;
                }
                chose = Integer.parseInt(buffer);
                for (EntityWrapper wrapper : resultSearchPurchase) {
                    if (wrapper.getIndex() == chose - 1) {
                        purchaseChosen = ((Purchase)wrapper.getEntity());
                        break;
                    }
                }
                if(purchaseChosen != null) {
                    System.out.println();
                    System.out.println("What we will be change?");
                    System.out.println("Title");
                    System.out.println("UserId");
                    System.out.println("ModelId");
                    object = reader.readLine();
                    switch (object) {
                        case "Title":
                            System.out.println("Enter your value for update");
                            valueUpdate = reader.readLine();
                            candidate = new Purchase(
                                    purchaseChosen.getId(),
                                    valueUpdate,
                                    purchaseChosen.getUserId(),
                                    purchaseChosen.getModelId(),
                                    purchaseChosen.getStatus()
                            );
                            result = iDataProvider.updateRecord(candidate);
                            System.out.println(result.getStatus() + " : " + result.getMessage());
                            break;

                        case "UserId":
                            while (true){
                                try{
                                    System.out.println("Enter your value for update");
                                    valueUpdate = reader.readLine();
                                    candidate = new Purchase(
                                            purchaseChosen.getId(),
                                            purchaseChosen.getTitle(),
                                            Long.parseLong(valueUpdate),
                                            purchaseChosen.getModelId(),
                                            purchaseChosen.getStatus()
                                    );
                                    break;
                                } catch(NumberFormatException ex){
                                    System.out.println("'UserId' format not valid, try again");
                                }
                            }
                            result = iDataProvider.updateRecord(candidate);
                            System.out.println(result.getStatus() + " : " + result.getMessage());
                            break;

                        case "ModelId":
                            while (true){
                                try{
                                    System.out.println("Enter your value for update");
                                    valueUpdate = reader.readLine();
                                    candidate = new Purchase(
                                            purchaseChosen.getId(),
                                            purchaseChosen.getTitle(),
                                            purchaseChosen.getUserId(),
                                            Long.parseLong(valueUpdate),
                                            purchaseChosen.getStatus()
                                    );
                                    break;
                                } catch(NumberFormatException ex){
                                    System.out.println("'ModelId' format not valid, try again");
                                }
                            }
                            result = iDataProvider.updateRecord(candidate);
                            System.out.println(result.getStatus() + " : " + result.getMessage());
                            break;

                        default:
                            System.out.println("Your chose isn't right!");
                            break;
                    }
                }
//                System.in.read();
                purchaseChosen = null;
            }

        } catch (IOException ex){
            log.error(ex);
        } catch (NumberFormatException ex){
            log.error(ex);
        }
    }

    public void findPurchase(IDataProvider iDataProvider){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            DataProviderResult result;
            List<User> users = iDataProvider.selectAllRecords(TypeOfEntities.USER).getBeans();
            List<EntityWrapper> resultSearchUser = new ArrayList();
            int chose = 0;
            String buffer;
            for (User user : users) {
                resultSearchUser.add(new EntityWrapper(chose, user));
                System.out.println(resultSearchUser.get(chose).getIndex() + 1 + ") "
                        + resultSearchUser.get(chose).getEntity().toString());
                chose++;
            }
            while (true) {
                System.out.println("\nSelect user's index, which history you want open or enter 'quit' to quit!");
                buffer = reader.readLine();
                if (buffer.equals("quit")) {
                    return;
                }
                chose = Integer.parseInt(buffer);
                for (EntityWrapper wrapper : resultSearchUser) {
                    if (wrapper.getIndex() == chose - 1) {
                        result = iDataProvider.selectUsersPurchases(wrapper.getEntity());
                        if (!result.getStatus().equals(StatusType.PURCHASES_NOT_FOUND)){
                            result.getBeans().forEach(iterator -> System.out.println(((Purchase) iterator).toString()));
                        } else {
                            System.out.println("User don't have any purchase");
                        }
                        break;
                    }
                }
            }
        } catch (IOException ex){
            log.error(ex);
        }
    }

    public void selectAllRecords(IDataProvider iDataProvider, TypeOfEntities type){
        List<T> entityes = iDataProvider.selectAllRecords(type).getBeans();
        List<EntityWrapper> resultSearchEntity = new ArrayList();
        int index = 0;
        for (T entity : entityes) {
            resultSearchEntity.add(new EntityWrapper(index, entity));
            System.out.println(resultSearchEntity.get(index).getIndex() + 1 + ") " +
                    resultSearchEntity.get(index).getEntity().toString());
            index++;
        }
    }

    public void selectUser(IDataProvider iDataProvider){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            List<User> users = iDataProvider.selectAllRecords(TypeOfEntities.USER).getBeans();
            List<EntityWrapper> resultSearchUser = new ArrayList();
            int index = 0;
            DataProviderResult result;
            String buffer;
            System.out.println("Enter user's login to find him");
            buffer = reader.readLine();
            result = iDataProvider.getUserByLogin(buffer, TypeOfEntities.USER);
            for (User user : users) {
                resultSearchUser.add(new EntityWrapper(index, user));
                index++;
            }
            if (result.getStatus().equals(StatusType.SUCCESS)) {
                System.out.println(((User) result.getBean()).getId() + " , " + result.getBean().toString() + "\n");
                resultSearchUser.forEach(iterator -> System.out.println(iterator.getIndex()
                        + 1 + ") " + iterator.getEntity().toString()));
            } else {
                System.out.println("Login - " + buffer + " hasn't been found!\n");
            }
        }catch(IOException ex){
            log.error(ex);
        }
    }

    public void selectModel(IDataProvider iDataProvider){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            List<Model> models = iDataProvider.selectAllRecords(TypeOfEntities.MODEL).getBeans();
            List<EntityWrapper> resultSearchModel = new ArrayList();
            int index = 0;
            DataProviderResult result;
            String buffer;
            System.out.println("Enter model's title to find it");
            buffer = reader.readLine();
            result = iDataProvider.getEntityByTitle(buffer, TypeOfEntities.MODEL);
            if (result.getStatus().equals(StatusType.SUCCESS)) {
                for (Model model : ((List<Model>)result.getBeans())) {
                    resultSearchModel.add(new EntityWrapper(index, model));
                    index++;
                }
                resultSearchModel.stream().forEach(iterator -> {
                    System.out.println(iterator.getIndex() + 1 + ") " + ((Model)iterator.getEntity()).toString());
                });
            } else {
                System.out.println("Title - " + buffer + " hasn't been found!\n");
            }
        } catch(IOException ex){
            log.error(ex);
        }
    }

    public void selectPurchase(IDataProvider iDataProvider){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            List<Purchase> purchases = iDataProvider.selectAllRecords(TypeOfEntities.PURCHASE).getBeans();
            List<EntityWrapper> resultSearchPurchase = new ArrayList();
            int index = 0;
            DataProviderResult result;
            String buffer;
            System.out.println("Enter purchase's title to find it");
            buffer = reader.readLine();
            result = iDataProvider.getEntityByTitle(buffer, TypeOfEntities.PURCHASE);
            if (result.getStatus().equals(StatusType.SUCCESS)) {
                for (Purchase purchase : ((List<Purchase>)result.getBeans())) {
                    resultSearchPurchase.add(new EntityWrapper(index, purchase));
                    index++;
                }
                resultSearchPurchase.stream().forEach(iterator -> {
                    System.out.println(iterator.getIndex() + 1 + ") " + ((Purchase)iterator.getEntity()).toString());
                });
            } else {
                System.out.println("Title - " + buffer + " hasn't been found!\n");
            }
        } catch(IOException ex){
            log.error(ex);
        }
    }

    public void buyPurchase(IDataProvider iDataProvider) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            List<Purchase> purchases = iDataProvider.selectAllRecords(TypeOfEntities.PURCHASE).getBeans();
            List<EntityWrapper> resultSearchPurchase = new ArrayList();
            int index = 0;
            String buffer;
            for (Purchase purchase : purchases) {
                resultSearchPurchase.add(new EntityWrapper(index, purchase));
                System.out.println(resultSearchPurchase.get(index).getIndex() + 1 + ") " + 
                        resultSearchPurchase.get(index).getEntity().toString());
                index++;
            }
            while (true) {
                System.out.println("\nSelect index of purchase, which has been purchased or enter 'quit' to quit!");
                buffer = reader.readLine();
                if (buffer.equals("quit")) {
                    return;
                }
                index = Integer.parseInt(buffer);
                for (EntityWrapper wrapper : resultSearchPurchase) {
                    if (wrapper.getIndex() == index - 1) {
                        DataProviderResult result = iDataProvider.buyModel((Purchase)wrapper.getEntity());
                        System.out.println(result.getStatus() + " : " + result.getMessage());
                        break;
                    }
                }
                if(index <= 0 || index > resultSearchPurchase.size()){
                    System.out.println("You enter wrong index or list of Purchases is empty");
                }
                System.in.read();
            }

        } catch(IOException ex){
            log.info(ex);
        } catch (ArrayIndexOutOfBoundsException ex){
            log.info(ex);
        }
    }

    public void help(){
        System.out.println("[use]     [CSV, XML, JDBC]");
        System.out.println("[select]  [user, users, model, models, purchase, purchases]");
        System.out.println("[save]    [user, model, purchase]");
        System.out.println("[delete]  [user, model, purchase]");
        System.out.println("[update]  [user, model, purchase]");
        System.out.println("[delete]  [cascade]");
        System.out.println("[user]    [purchases]");
    }

}
