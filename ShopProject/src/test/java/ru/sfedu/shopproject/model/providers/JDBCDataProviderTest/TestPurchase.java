package ru.sfedu.shopproject.model.providers.JDBCDataProviderTest;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.sfedu.shopproject.DataGenerator;
import ru.sfedu.shopproject.model.entities.CSVEntities.Model;
import ru.sfedu.shopproject.model.entities.CSVEntities.Purchase;
import ru.sfedu.shopproject.model.entities.CSVEntities.User;
import ru.sfedu.shopproject.model.entities.Result.DataProviderResult;
import ru.sfedu.shopproject.model.entities.StatusType;
import ru.sfedu.shopproject.model.entities.TypeOfEntities;
import ru.sfedu.shopproject.model.providers.DataProviderMySQL;

/**
 *
 * @author Максим
 */
public class TestPurchase {
    
    private static Logger logger = Logger.getLogger(ru.sfedu.shopproject.model.providers.CSVDataProviderTest.TestPurchase.class);
    private static DataProviderMySQL dataProvider;
    private static Purchase purchase;
    private static User user;
    private static Model model;
    
    //    save purchase test
    @Test
    public void testA() throws InterruptedException {
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(user).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(model).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(purchase).getStatus());
        Assert.assertEquals(StatusType.ID_ALLREADY_EXIST, dataProvider.saveRecord(purchase).getStatus());
        DataProviderResult trueResult = dataProvider.getRecordById(purchase);
        Assert.assertEquals(trueResult.getStatus(), StatusType.SUCCESS);
        Assert.assertEquals(trueResult.getBean(), purchase);
    }
    
    //    get purchase by title test
    @Test
    public void testB() {
        Purchase fakePurchase = DataGenerator.createPurchase(user.getId(), model.getId());
        DataProviderResult trueResult = dataProvider.getEntityByTitle(purchase.getTitle(), TypeOfEntities.PURCHASE);
        DataProviderResult fakeResult = dataProvider.getEntityByTitle(fakePurchase.getTitle(), TypeOfEntities.PURCHASE);
        Assert.assertEquals(StatusType.SUCCESS, trueResult.getStatus());
        Assert.assertEquals(trueResult.getBeans().get(0), purchase);
        Assert.assertEquals(StatusType.TITLE_NOT_EXIST, fakeResult.getStatus());
    }
    
    //    update purchase test
    @Test
    public void testC(){
        Purchase updatePurchase = purchase;
        updatePurchase.setTitle("updated_title");
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.updateRecord(updatePurchase).getStatus());
        DataProviderResult trueResult = dataProvider.getRecordById(updatePurchase);
        Assert.assertEquals(StatusType.SUCCESS, trueResult.getStatus());
        Assert.assertEquals(updatePurchase.getTitle(), ((Purchase)trueResult.getBean()).getTitle());
    }
    
    //    delete purchase test
    @Test
    public void testD(){
        Assert.assertEquals(dataProvider.deleteRecord(purchase).getStatus(), StatusType.STATUS_NOT_SOLD);
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.buyModel(purchase).getStatus());
        Assert.assertEquals(dataProvider.deleteRecord(purchase).getStatus(), StatusType.SUCCESS);
        Assert.assertEquals(dataProvider.getRecordById(purchase).getStatus(), StatusType.ID_NOT_EXIST);
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.deleteRecord(user).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.deleteRecord(model).getStatus());
    }
    
    //    buy purchase test
    @Test
    public void testE(){
        Purchase testPurchase = DataGenerator.createPurchase(user.getId(), model.getId());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(user).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(model).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(testPurchase).getStatus());
        Assert.assertEquals(dataProvider.deleteRecord(user).getStatus(), StatusType.DEPENDENCIES_EXISTED);
        Assert.assertEquals(0, ((Purchase)dataProvider.getRecordById(testPurchase)
                .getBean()).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.buyModel(testPurchase).getStatus());
        Assert.assertEquals(1, ((Purchase)dataProvider.getRecordById(testPurchase).getBean()).getStatus());
        Assert.assertEquals(dataProvider.deleteRecord(user).getStatus(), StatusType.SUCCESS);
        Assert.assertEquals(dataProvider.deleteRecord(model).getStatus(), StatusType.SUCCESS);
        Assert.assertEquals(dataProvider.deleteRecord(testPurchase).getStatus(), StatusType.ID_NOT_EXIST);
        Assert.assertEquals(dataProvider.deleteRecord(model).getStatus(), StatusType.ID_NOT_EXIST);
    }
    
    //    get all purchases test
    @Test
    public void testF(){
        ArrayList<Purchase> purchases = new ArrayList<>();
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(user).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(model).getStatus());
        for (int i = 0; i < 10; i++){
            purchase = DataGenerator.createPurchase(user.getId(), model.getId());
            purchases.add(purchase);
            Assert.assertEquals(dataProvider.saveRecord(purchase).getStatus(), StatusType.SUCCESS);
        }
        DataProviderResult result = dataProvider.selectAllRecords(TypeOfEntities.PURCHASE);
        Assert.assertEquals(result.getStatus(), StatusType.SUCCESS);
        List allPurchases = result.getBeans();
        Assert.assertTrue(purchases.stream().allMatch(allPurchases::contains));
        purchases.forEach(deletedRecord -> {
            dataProvider.buyModel(deletedRecord);
            dataProvider.deleteRecord(deletedRecord);
        });
        Assert.assertEquals(dataProvider.deleteRecord(user).getStatus(), StatusType.SUCCESS);
        Assert.assertEquals(dataProvider.deleteRecord(model).getStatus(), StatusType.SUCCESS);
    }
    
    @BeforeClass
    public static void setUp() throws Exception {
        dataProvider = new DataProviderMySQL<>();
        dataProvider.initDataSource();
        user = DataGenerator.createUser();
        model = DataGenerator.createModel();
        purchase = DataGenerator.createPurchase(user.getId(), model.getId());
    }

    @AfterClass
    public static void tearDown() throws Exception {
    }
    
}
