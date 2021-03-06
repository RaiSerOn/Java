package ru.sfedu.shopproject.model.providers.XMLDataProviderTest;

import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.runners.MethodSorters;
import ru.sfedu.shopproject.DataGenerator;
import ru.sfedu.shopproject.model.entities.CSVEntities.Model;
import ru.sfedu.shopproject.model.entities.CSVEntities.Purchase;
import ru.sfedu.shopproject.model.entities.CSVEntities.User;
import ru.sfedu.shopproject.model.entities.Result.DataProviderResult;
import ru.sfedu.shopproject.model.entities.StatusType;
import ru.sfedu.shopproject.model.entities.TypeOfEntities;
import ru.sfedu.shopproject.model.providers.DataProviderCSV;
import ru.sfedu.shopproject.model.providers.DataProviderXML;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Максим
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUser {

    private static Logger logger = Logger.getLogger(TestUser.class);
    private static DataProviderXML dataProvider;
    private static User user;

    //    save user test
    @Test
    public void testA() throws InterruptedException {
        User fakeUser = DataGenerator.createUser();
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(user).getStatus());
        fakeUser.setLogin(user.getLogin());
        Assert.assertEquals(StatusType.LOGIN_EXIST, dataProvider.saveRecord(fakeUser).getStatus());
        fakeUser.setId(user.getId());
        fakeUser.setLogin(user.getThirdName());
        Assert.assertEquals(StatusType.ID_ALLREADY_EXIST, dataProvider.saveRecord(fakeUser).getStatus());
        DataProviderResult trueResult = dataProvider.getRecordById(user);
        Assert.assertEquals(trueResult.getStatus(), StatusType.SUCCESS);
        Assert.assertEquals(trueResult.getBean(), user);
    }

    //    get user by login test
    @Test
    public void testB() {
        User fakeUser = DataGenerator.createUser();
        DataProviderResult trueResult = dataProvider.getUserByLogin(user.getLogin(), TypeOfEntities.USER);
        DataProviderResult fakeResult = dataProvider.getUserByLogin(fakeUser.getLogin(), TypeOfEntities.USER);
        Assert.assertEquals(StatusType.SUCCESS, trueResult.getStatus());
        Assert.assertEquals(trueResult.getBean(), user);
        Assert.assertEquals(StatusType.LOGIN_NOT_EXIST, fakeResult.getStatus());
    }

    //    update user test
    @Test
    public void testC(){
        User updateUser = user;
        updateUser.setLogin("updated_login");
        updateUser.setNumber(user.getNumber() + "_updated");
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.updateRecord(updateUser).getStatus());
        DataProviderResult trueResult = dataProvider.getRecordById(updateUser);
        Assert.assertEquals(StatusType.SUCCESS, trueResult.getStatus());
        Assert.assertEquals(updateUser.getLogin(), ((User)trueResult.getBean()).getLogin());
    }

    //    delete user test
    @Test
    public void testD(){
        Model modelTest = DataGenerator.createModel();
        Purchase purchaseTest = DataGenerator.createPurchase(user.getId(), modelTest.getId());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(modelTest).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(purchaseTest).getStatus());

        Assert.assertEquals(dataProvider.deleteRecord(user).getStatus(), StatusType.DEPENDENCIES_EXISTED);
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.buyModel(purchaseTest).getStatus());
        Assert.assertEquals(dataProvider.deleteRecord(user).getStatus(), StatusType.SUCCESS);
        Assert.assertEquals(dataProvider.getRecordById(user).getStatus(), StatusType.ID_NOT_EXIST);

        Assert.assertEquals(user.getId(), ((Purchase)dataProvider.getRecordById(purchaseTest).getBean()).getUserId());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.deleteRecord(purchaseTest).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.deleteRecord(modelTest).getStatus());
    }

    //    get all users test
    @Test
    public void testE(){
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            user = DataGenerator.createUser();
            users.add(user);
            Assert.assertEquals(dataProvider.saveRecord(user).getStatus(), StatusType.SUCCESS);
        }
        DataProviderResult result = dataProvider.selectAllRecords(TypeOfEntities.USER);
        Assert.assertEquals(result.getStatus(), StatusType.SUCCESS);
        List allUsers = result.getBeans();
        Assert.assertTrue(users.stream().allMatch(allUsers::contains));
        users.forEach(deletedRecord -> dataProvider.deleteRecord(deletedRecord));
    }

    //    delete recursive user test
    @Test
    public void testF(){
        Model testModel = DataGenerator.createModel();
        Purchase testPurchase = DataGenerator.createPurchase(user.getId(), testModel.getId());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(user).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(testModel).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(testPurchase).getStatus());
        Assert.assertEquals(dataProvider.deleteRecord(user).getStatus(), StatusType.DEPENDENCIES_EXISTED);
        Assert.assertEquals(dataProvider.deleteRecord(testModel).getStatus(), StatusType.DEPENDENCIES_EXISTED);
        Assert.assertEquals(dataProvider.deleteUserRecursive(user).getStatus(), StatusType.SUCCESS);
        Assert.assertEquals(dataProvider.deleteRecord(testModel).getStatus(), StatusType.SUCCESS);
    }

    //    select all user's purchases test
    @Test
    public void testG(){
        Model testModel = DataGenerator.createModel();
        Purchase testPurchaseFirst = DataGenerator.createPurchase(user.getId(), testModel.getId());
        Purchase testPurchaseSecond = DataGenerator.createPurchase(user.getId(), testModel.getId());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(user).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(testModel).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(testPurchaseFirst).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(testPurchaseSecond).getStatus());
        DataProviderResult result = dataProvider.selectUsersPurchases(user);
        Assert.assertEquals(result.getStatus(), StatusType.SUCCESS);
        List<Purchase> purchases = new ArrayList();
        purchases.add(testPurchaseFirst);
        purchases.add(testPurchaseSecond);
        List allPurchasesOfUser = result.getBeans();
        Assert.assertTrue(purchases.stream().allMatch(allPurchasesOfUser::contains));
        purchases.forEach(deletedRecord -> {
            dataProvider.buyModel(deletedRecord);
            dataProvider.deleteRecord(deletedRecord);
        });
        Assert.assertEquals(dataProvider.deleteRecord(user).getStatus(), StatusType.SUCCESS);
        Assert.assertEquals(dataProvider.deleteRecord(testModel).getStatus(), StatusType.SUCCESS);
    }

    @BeforeClass
    public static void setUp() throws Exception {
        dataProvider = new DataProviderXML<>();
        dataProvider.initDataSource();
        user = DataGenerator.createUser();
    }

    @AfterClass
    public static void tearDown() throws Exception {
    }

}
