package ru.sfedu.shopproject;

import org.apache.log4j.Logger;
import ru.sfedu.shopproject.controllers.CLIController;
import ru.sfedu.shopproject.model.entities.Result.DataProviderResult;
import ru.sfedu.shopproject.model.entities.TypeOfEntities;
import ru.sfedu.shopproject.model.providers.DataProviderMySQL;
import ru.sfedu.shopproject.model.providers.IDataProvider;
import ru.sfedu.shopproject.utils.ConfigurationUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class Main
 *
 *
 * @author Максим
 */
public class Main {

    private static Logger logger = Logger.getLogger(Main.class);
    private static IDataProvider dataProvider;
    private static CLIController cliController = new CLIController();

    public static void main(String [] args){
        String str = System.getProperty("PATH");
        if(str != null){
            ConfigurationUtil.setConfigPath(System.getProperty("PATH"));
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] commands;
        dataProvider = null;
        System.out.println("Select Data Provider(XML, CSV, JDBC)");
        System.out.println("Use command -> use 'smth' (smth = CSV, XML, JDBC)");
        try {
            while(true){
                System.out.println("Main menu:");
                try {
                    commands = reader.readLine().split(" ");
                    if (commands[0].equals("quit")) {
                        if(dataProvider instanceof DataProviderMySQL){
                            ((DataProviderMySQL) dataProvider).closeProvider();
                        }
                        System.out.println("Bye");
                        break;
                    }
                    switch (commands[0]) {
                        case "use":
                            dataProvider = cliController.initDataProvider(dataProvider, commands[1]);
                            break;

                        case "select":
                            if (dataProvider == null) {
                                System.out.println("Select Data Provider!");
                                break;
                            }
                            switch (commands[1]) {
                                case "users":
                                    cliController.selectAllRecords(dataProvider, TypeOfEntities.USER);
                                    break;

                                case "models":
                                    cliController.selectAllRecords(dataProvider, TypeOfEntities.MODEL);
                                    break;

                                case "purchases":
                                    cliController.selectAllRecords(dataProvider, TypeOfEntities.PURCHASE);
                                    break;

                                case "user":
                                    cliController.selectUser(dataProvider);
                                    break;

                                case "model":
                                    cliController.selectModel(dataProvider);
                                    break;

                                case "purchase":
                                    cliController.selectPurchase(dataProvider);
                                    break;

                                default:
                                    System.out.println("This Entity not provided!");
                                    break;

                            }
                            break;

                        case "save":
                            if (dataProvider == null) {
                                System.out.println("Select Data Provider!");
                                break;
                            }
                            switch(commands[1]){
                                case "user":
                                    cliController.savingUser(dataProvider);
                                    break;

                                case "model":
                                    cliController.savingModel(dataProvider);
                                    break;

                                case "purchase":
                                    cliController.savingPurchase(dataProvider);
                                    break;

                                default:
                                    System.out.println("This Entity not provided!");
                                    break;

                            }
                            break;

                        case "delete":
                            if (dataProvider == null) {
                                System.out.println("Select Data Provider!");
                                break;
                            }
                            switch(commands[1]){
                                case "user":
                                    cliController.deleteUser(dataProvider);
                                    break;

                                case "model":
                                    cliController.deleteModel(dataProvider);
                                    break;

                                case "purchase":
                                    cliController.deletePurchase(dataProvider);
                                    break;

                                case "cascade":
                                    cliController.deleteUserRecursive(dataProvider);
                                    break;
                                    
                                default:
                                    System.out.println("This Entity not provided!");
                                    break;

                            }
                            break;

                        case "update":
                            if (dataProvider == null) {
                                System.out.println("Select Data Provider!");
                                break;
                            }
                            switch(commands[1]){
                                case "user":
                                    cliController.updateUser(dataProvider);
                                    break;

                                case "model":
                                    cliController.updateModel(dataProvider);
                                    break;

                                case "purchase":
                                    cliController.updatePurchase(dataProvider);
                                    break;

                                default:
                                    System.out.println("This Entity not provided!");
                                    break;

                            }
                            break;

                        case "pay":
                            if (dataProvider == null) {
                                System.out.println("Select Data Provider!");
                                break;
                            }
                            if(commands[1].equals("off")){
                                cliController.buyPurchase(dataProvider);
                                break;
                            } else {
                                System.out.println("Wrong second argument");
                                break;
                            }

                        case "user":
                            if (dataProvider == null) {
                                System.out.println("Select Data Provider!");
                                break;
                            }
                            if(commands[1].equals("purchases")){
                                cliController.findPurchase(dataProvider);
                                break;
                            } else {
                                System.out.println("Wrong second argument");
                                break;
                            }

                        case "-help":
                            cliController.help();
                            break;

                        default:
                            System.out.println("Your command isn't right!");
                            System.out.println("You can use '-help' for list of commands");
                            break;
                    }
                } catch(IndexOutOfBoundsException ex){
                    logger.error(ex);
                }

            }
        }catch(IOException ex){
            logger.info(ex);
        }
    }

}
