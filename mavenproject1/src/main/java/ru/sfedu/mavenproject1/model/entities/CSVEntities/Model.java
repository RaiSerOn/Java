package ru.sfedu.mavenproject1.model.entities.CSVEntities;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;
import ru.sfedu.mavenproject1.model.entities.WithId;

/** 
* Class Model 
*/ 

public class Model extends WithId{
    
    @CsvBindByPosition(position = 0)
    private Long id;
    
    @CsvBindByPosition(position = 1)
    private String title;
    
    @CsvBindByPosition(position = 2)
    private String mark;
    
    @CsvBindByPosition(position = 3)
    private String manufacturer;
    
    @CsvBindByPosition(position = 4)
    private String size;
    
    @CsvBindByPosition(position = 5)
    private String material;
    
    @CsvBindByPosition(position = 6)
    private Long price;
    
    // 
    // Constructors 
    // 
    
    public Model(){}
    
    public Model(Long id, String title, String mark, String manufacturer, 
            String size, String material, Long price){
        this.id = id;
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
    * Set value of id 
    * @param id - new value of id 
    */ 
    @Element 
    @Override
    public void setId(Long id) {
        this.id = id;
    }
    
    /** 
    * Get value of id 
    * @return value of id 
    */ 
    @Element 
    @Override
    public Long getId() {
        return id;
    }

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
        return "id=" + id + ",title=" + title + ",mark=" + mark + 
                ",manufacturer=" + manufacturer + ",price=" + price;
    }
     
}
