package ru.sfedu.shopproject.model.entities.CSVEntities;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Element;

import java.util.Objects;
import ru.sfedu.shopproject.model.entities.TypeOfEntities;

/** 
* Class Model
*
*
* @author Максим
*/ 

public class Model extends WithId{
    
//    @CsvBindByName(column = "id", required = true)
//    @CsvBindByPosition(position = 0)
//    private Long id;
    
    
//    @CsvBindByPosition(position = 1)
    @CsvBindByName
    private String title;
    
    
//    @CsvBindByPosition(position = 2)
    @CsvBindByName
    private String mark;
    
    
//    @CsvBindByPosition(position = 3)
    @CsvBindByName
    private String manufacturer;
    
    
//    @CsvBindByPosition(position = 4)
    @CsvBindByName
    private String size;
    
    
//    @CsvBindByPosition(position = 5)
    @CsvBindByName
    private String material;
    
    
//    @CsvBindByPosition(position = 6)
    @CsvBindByName
    private Long price;
    
    // 
    // Constructors 
    // 
    
    public Model(){
        super(TypeOfEntities.MODEL);
    }
    
    public Model(Long id, String title, String mark, String manufacturer, 
            String size, String material, Long price){
        super(id, TypeOfEntities.MODEL);
        this.title = title;
        this.mark = mark;
        this.manufacturer = manufacturer;
        this.size = size;
        this.material = material;
        this.price = price;
    }

    public Model(String title, String mark, String manufacturer,
                 String size, String material, Long price){
        super(TypeOfEntities.MODEL);
        this.title = title;
        this.mark = mark;
        this.manufacturer = manufacturer;
        this.size = size;
        this.material = material;
        this.price = price;
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
    * Get value of title 
    * @return value of title 
    */
    @Element 
    public String getTitle() {
        return title;
    }
    
    /** 
    * Set value of mark 
    * @param mark - new value of mark
    */
    @Element 
    public void setMark(String mark) {
        this.mark = mark;
    }

    /** 
    * Get value of mark 
    * @return value of mark 
    */
    @Element 
    public String getMark() {
        return mark;
    }
    
    /** 
    * Set value of manufacturer 
    * @param manufacturer - new value of manufacturer
    */
    @Element 
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /** 
    * Get value of manufacturer 
    * @return value of manufacturer 
    */
    @Element 
    public String getManufacturer() {
        return manufacturer;
    }
    
    /** 
    * Set value of size 
    * @param size - new value of size
    */
    @Element 
    public void setSize(String size) {
        this.size = size;
    }

    /** 
    * Get value of size 
    * @return value of size 
    */
    @Element 
    public String getSize() {
        return size;
    }
            
    /** 
    * Set value of material 
    * @param material - new value of material
    */
    @Element 
    public void setMaterial(String material) {
        this.material = material;
    }

    /** 
    * Get value of material 
    * @return value of material 
    */
    @Element 
    public String getMaterial() {
        return material;
    }
    
    /** 
    * Set value of price 
    * @param price - new value of price
    */
    @Element 
    public void setPrice(Long price) {
        this.price = price;
    }

    /** 
    * Get value of price 
    * @return value of price 
    */
    @Element 
    public Long getPrice() {
        return price;
    }
    
    // 
    // Other methods 
    // 
    
    @Override
    public String toString(){
        return super.getId() + ",'" + title + "', '" + mark + "', '" + manufacturer + "', '" + 
                size + "', '" + material + "', " + price;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEquals = false;
        if(obj != null && obj instanceof Model){
            isEquals = Objects.equals(this.toString(), obj.toString());
        }
        return isEquals;

    }
     
}
