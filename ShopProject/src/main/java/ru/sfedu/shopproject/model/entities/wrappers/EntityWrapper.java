package ru.sfedu.shopproject.model.entities.wrappers;

import ru.sfedu.shopproject.model.entities.CSVEntities.WithId;

/**
 * Class EntityWrapper
 *
 *
 * @author Максим
 */
public class EntityWrapper <T> extends WithId{
    
    private int index;
    private T entity;
    
    public EntityWrapper(int index, T entity){
        this.index = index;
        this.entity = entity;
    }

    public int getIndex() {
        return index;
    }

    public T getEntity() {
        return entity;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    @Override
    public Long getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setId(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
      
}
