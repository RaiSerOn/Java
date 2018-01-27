package ru.sfedu.shopproject.model.entities.CSVEntities;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;
import org.simpleframework.xml.Element;
import ru.sfedu.shopproject.model.entities.TypeOfEntities;

/**
 * Class Purchase
 *
 *
 * @author Максим
 */
public class Purchase extends WithId{

//    @CsvBindByPosition(position = 0)
//    private Long id;
    
//    @CsvBindByPosition(position = 1)
    @CsvBindByName
    private String title;
    
//    @CsvBindByPosition(position = 2)
    @CsvBindByName
    private Long userId;
    
//    @CsvBindByPosition(position = 3)
    @CsvBindByName
    private Long modelId;
    
//    @CsvBindByPosition(position = 4)
    @CsvBindByName
    private int status;

    //
    // Constructors
    //
    
    public Purchase(){
        super(TypeOfEntities.PURCHASE);
    }
    
    public Purchase(Long id, String title, Long userId, Long modelId, int status){
        super(id, TypeOfEntities.PURCHASE);
        this.title = title;
        this.userId = userId;
        this.modelId = modelId;
        this.status = status;
    }

    public Purchase(String title, Long userId, Long modelId, int status){
        super(TypeOfEntities.PURCHASE);
        this.title = title;
        this.userId = userId;
        this.modelId = modelId;
        this.status = status;
    }

    //
    // Methods
    //

    //
    // Access methods
    //
    
    /**
     * Set value of title
     * @param title - new value of title
     */
    @Element
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set value of userId
     * @param userId - new value of userId
     */
    @Element
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Set value of modelId
     * @param modelId - new value of modelId
     */
    @Element
    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    /**
     * Set value of status
     * @param status - new value of status
     */
    @Element
    public void setStatus(int status){
        this.status = status;
    }


    /**
     * Get value of title
     * @return value of title
     */
    @Element
    public String getTitle() {
        return title;
    }

    /**
     * Get value of userId
     * @return value of userId
     */
    @Element
    public Long getUserId() {
        return userId;
    }

    /**
     * Get value of modelId
     * @return value of modelId
     */
    @Element
    public Long getModelId() {
        return modelId;
    }

    /**
     * Get value of status
     * @return value of status
     */
    @Element
    public int getStatus(){
        return status;
    }

    //
    // Other methods
    //
    
    @Override
    public String toString(){
        return  super.getId() + ",'" + title + "', " + userId + ", " + modelId + ", " + status;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEquals = false;
        if(obj != null && obj instanceof Purchase){
            isEquals = Objects.equals(this.toString(), obj.toString());
        }
        return isEquals;

    }
    
}
