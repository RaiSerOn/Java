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
public class TestModel {
    
    private static Logger logger = Logger.getLogger(TestUser.class);
    private static DataProviderMySQL dataProvider;
    private static Model model;
    
    //    save model test
    @Test
    public void testA() {
        Model fakeModel = DataGenerator.createModel();
        fakeModel.setId(model.getId());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(model).getStatus());
        Assert.assertEquals(StatusType.ID_ALLREADY_EXIST, dataProvider.saveRecord(model).getStatus());
        Assert.assertEquals(StatusType.ID_ALLREADY_EXIST, dataProvider.saveRecord(fakeModel).getStatus());
        DataProviderResult trueResult = dataProvider.getRecordById(model);
        Assert.assertEquals(trueResult.getStatus(), StatusType.SUCCESS);
        Assert.assertEquals(trueResult.getBean(), model);
    }

    //    get model by title test
    @Test
    public void testB() {
        Model fakeModel = DataGenerator.createModel();
        DataProviderResult trueResult = dataProvider.getEntityByTitle(model.getTitle(), TypeOfEntities.MODEL);
        DataProviderResult fakeResult = dataProvider.getEntityByTitle(fakeModel.getTitle(), TypeOfEntities.MODEL);
        Assert.assertEquals(StatusType.SUCCESS, trueResult.getStatus());
        Assert.assertEquals(trueResult.getBeans().get(0), model);
        Assert.assertEquals(StatusType.TITLE_NOT_EXIST, fakeResult.getStatus());
    }
    
    //    update model test
    @Test
    public void testC(){
        Model updateModel = model;
        updateModel.setManufacturer("updated_manufacturer");
        updateModel.setMark("updated_mark");
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.updateRecord(updateModel).getStatus());
        DataProviderResult trueResult = dataProvider.getRecordById(updateModel);
        Assert.assertEquals(StatusType.SUCCESS, trueResult.getStatus());
        Assert.assertEquals(updateModel.getManufacturer(), ((Model)trueResult.getBean()).getManufacturer());
    }
    
    //    delete model test
    @Test
    public void testD(){
        User userTest = DataGenerator.createUser();
        Purchase purchaseTestFirst = DataGenerator.createPurchase(userTest.getId(), model.getId());
        Purchase purchaseTestSecond = DataGenerator.createPurchase(userTest.getId(), model.getId());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(userTest).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(purchaseTestFirst).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.saveRecord(purchaseTestSecond).getStatus());
        Assert.assertEquals(dataProvider.deleteRecord(model).getStatus(), StatusType.DEPENDENCIES_EXISTED);
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.buyModel(purchaseTestFirst).getStatus());
        Assert.assertEquals(dataProvider.deleteRecord(model).getStatus(), StatusType.DEPENDENCIES_EXISTED);
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.buyModel(purchaseTestSecond).getStatus());
        Assert.assertEquals(dataProvider.deleteRecord(model).getStatus(), StatusType.DEPENDENCIES_EXISTED);
        Assert.assertEquals(model.getId(), ((Purchase)dataProvider.getRecordById(purchaseTestFirst).getBean()).getModelId());
        Assert.assertEquals(model.getId(), ((Purchase)dataProvider.getRecordById(purchaseTestSecond).getBean()).getModelId());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.deleteRecord(purchaseTestFirst).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.deleteRecord(purchaseTestSecond).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.deleteRecord(userTest).getStatus());
        Assert.assertEquals(StatusType.SUCCESS, dataProvider.deleteRecord(model).getStatus());
    }
    
    //    get all models test
    @Test
    public void testE(){
        ArrayList<Model> models = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            model = DataGenerator.createModel();
            models.add(model);
            Assert.assertEquals(dataProvider.saveRecord(model).getStatus(), StatusType.SUCCESS);
        }
        DataProviderResult result = dataProvider.selectAllRecords(TypeOfEntities.MODEL);
        Assert.assertEquals(result.getStatus(), StatusType.SUCCESS);
        List allModels = result.getBeans();
        Assert.assertTrue(models.stream().allMatch(allModels::contains));
        models.forEach(deletedRecord -> dataProvider.deleteRecord(deletedRecord));
    }
    
    @BeforeClass
    public static void Start() throws Exception {
        dataProvider = new DataProviderMySQL();
        dataProvider.initDataSource();
        model = DataGenerator.createModel();
    }

    @AfterClass
    public static void Finish() throws Exception {
        dataProvider.closeProvider();
    }
    
}
