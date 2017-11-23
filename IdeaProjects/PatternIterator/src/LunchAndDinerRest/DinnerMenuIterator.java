package LunchAndDinerRest;

import java.util.Iterator;

public class DinnerMenuIterator implements Iterator{
    MenuItem[] items;
    int position = 0;

    public DinnerMenuIterator(MenuItem[] items){
        this.items = items;
    }

    public Object next(){
        MenuItem menuItem = items[position];
        position++;
        return menuItem;
    }

    public boolean hasNext(){
        if(position >= items.length || items[position] == null){
            return false;
        } else return true;
    }

    public void remove(){
        if(position <= 0){
            throw new IllegalStateException("Can't remove this ITEM");
        }
        if(items[position - 1] != null){
            for(int i = position - 1; i <(items.length - 1); i++){
                items[i] = items[i + 1];
            }
            items[items.length - 1] = null;
        }
    }

}
